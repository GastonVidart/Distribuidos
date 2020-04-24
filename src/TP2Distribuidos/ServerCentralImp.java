package TP2Distribuidos;

import estructuras.ListString;
import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class ServerCentralImp extends UnicastRemoteObject implements ServerCentral {

    private ServerHoroscopo svrHoroscopo;
    private ServerClima svrClima;
    private Map<String, String[]> cache;
    private Semaphore semaforoCache;
    private ListString protocoloHoroscopo;

    public ServerCentralImp(String ip, int puerto) throws RemoteException {
        Hashtable<String, String[]> mapa = new Hashtable<String, String[]>();
        this.cache = Collections.synchronizedMap(mapa);
        this.semaforoCache = new Semaphore(1);
        String[] horoscopo = {"AR", "TA", "GE", "CC", "LE", "VG", "LB", "ES", "SA", "CP", "AC", "PI"};
        int largoHoroscopo = horoscopo.length;
        this.protocoloHoroscopo = new ListString();
        for (int i = 0; i < largoHoroscopo; i++) {
            //Llenamos la lista con el protocolo de horoscopo
            this.protocoloHoroscopo.insertar(horoscopo[i], i + 1);
        }
        try {
            this.svrHoroscopo = (ServerHoroscopo) Naming.lookup("//" + ip + ":" + puerto + "/ServerHoroscopoImp");
            this.svrClima = (ServerClima) Naming.lookup("//" + ip + ":" + puerto + "/ServerClimaImp");
        } catch (Exception e) {
            System.err.println("Ocurrió un error al intentar obtener los objetos remotos Servidor Horoscopo y Servidor Clima."
                    + "\nRevise que esten publicados en el puerto correcto");
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getPronostico(String horoscopo, String fecha) {
        //modulo que recibe las solicitudes de un cliente y a partir de ella, verifica que cumpla el formato principal, 
        //el cual es prediccion(signo,fecha). Luego verifca si la solicitud ya fue hecha, si es asi responde la respuesta almacenada,
        //caso contrario solicita un prediccion y un pronostico del clima a cada servidor correspondiente.
        //finalemente contesta lo que le hayan respondido los servidores (error o respuesta valida).
        String respuestaHoroscopo, respuestaClima, rtaValidacion,
                claveCache, clave;
        String[] respuestaCache, valor;
        ArrayList<String> respuesta = new ArrayList<String>();
        try {
            System.out.println("Llamada a getPronostico() | Central Imp | Id Remota: " + this.ref);

            //Se verfica el formato de la solicitud recibida
            rtaValidacion = validacion(horoscopo, fecha);
            if (rtaValidacion.equals("valida")) {
                /*indexA = solicitudCliente.indexOf(";");
                solicitudHoroscopo = solicitudCliente.substring(11, indexA);
                solicitudClima = solicitudCliente.substring(indexA + 1, solicitudCliente.length() - 1);*/

                //se trata de obtener la consulta de la cache
                claveCache = horoscopo + fecha;
                System.out.println("Clave Buscada: " + claveCache);
                respuestaCache = this.cache.get(claveCache);
                System.out.println("Solicitud: " + claveCache + " Respuesta Cache: " + respuestaCache + " || Id Remota: " + this.ref);
                if (respuestaCache != null) { // la cache tuvo exito 
                    //System.out.println("entre al if si es diferente de nulo");
                    while (respuestaCache[0].equals("respuestaEnCurso")) {
                        System.out.println("Respuesta en curso para: " + claveCache + " || Id Remota: " + this.ref);
                        this.semaforoCache.acquire();
                        respuestaCache = this.cache.get(claveCache);
                    }
                    this.semaforoCache.release();

                    respuesta.add(respuestaCache[0]);
                    respuesta.add(respuestaCache[1]);
                    System.out.println("Cache Exito: Clave: " + claveCache + " Valor: " + respuestaCache[0] + "|" + respuestaCache[1] + " || Id Remota: " + this.ref);

                } else { // si la cache no tuvo exito se realiza la consulta a los servidores Clima y Horoscopo respectivamente

                    //semaforo para que un cliente con una misma consulta no trate de buscar la respuesta y no la encuentre en la cache
                    this.semaforoCache.acquire();
                    System.out.println("Se adquirio el lock para la consulta " + claveCache + " || Id Remota: " + this.ref);
                    this.cache.put(claveCache, new String[]{"respuestaEnCurso"});   //se crea un arreglo de 1 posicion que guarda el mensaje de respuesta en curso

                    //si en ambas fue valida devuelve la respuesta al cliente y la guarda en la cache,  
                    //caso contrario caso contrario retransmite el error del servidor correspondiente                    
                    respuestaHoroscopo = svrHoroscopo.getHoroscopo(horoscopo);          //Se consulta el horoscopo y se almacena la respuesta
                    respuestaClima = svrClima.getClima(fecha);                          //Se consulta el clima y se almacena la respuesta
                    if (!respuestaHoroscopo.contains("ESH") && !respuestaHoroscopo.contains("PH")) {

                        //Si no ocurrio una excepcion del lado del servidor horosocopo o la consulta fue valida
                        //se verifica la respuesta que envió el servidor clima.                                                                                                
                        if (!respuestaClima.contains("FD") && !respuestaClima.contains("FM") && !respuestaClima.contains("PC") && !respuestaClima.contains("ESC")) {

                            //la clave se forma concatenando las dos solicitudes horoscopo + fecha
                            //el valor se forma con las respuestas correctas de ambos servidores (rta1,rta2)
                            clave = horoscopo + fecha;
                            valor = new String[]{respuestaHoroscopo, respuestaClima};
                            System.out.println("Agrego Clave: " + clave + " Valor: " + valor[0] + "|" + valor[1] + " || Id Remota: " + this.ref);
                            this.cache.put(clave, valor);

                            //Se le responde al cliente la solicitud valida                            
                            respuesta.add(respuestaHoroscopo);
                            respuesta.add(respuestaClima);
                            this.semaforoCache.release();   //Liberamos el lock para que los que tuvieron la misma consulta puedan obtener la respuesta de la cache
                            System.out.println("Libero el lock para la consulta " + claveCache + " || Id Remota: " + this.ref);
                        } else {
                            respuesta.add(respuestaClima);  //Se le responde al cliente del error en clima
                        }
                    } else {
                        respuesta.add(respuestaHoroscopo);  //Se le responde al cliente del error en horoscopo
                    }
                }
                System.out.println("Cache Completa: " + this.cache.toString());
            } else {
				System.out.println(rtaValidacion);
				respuesta.add(rtaValidacion);
            }
        } catch (InterruptedException ex) {
            System.err.println("Ocurrió un error al esperar por la respuesta\n"
                    + "Ex:" + ex);
            //Si ocurre un error en el servidor entonces se envia un mensaje al cliente notificando del error en el mismo            
            respuesta.add("error al esperar la respuesta");
            return respuesta;
        } catch (RemoteException ex) {
            System.err.println("Ocurrió un error al referenciar al objeto remoto de Servidor Clima y/o Horoscopo\n"
                    + "Ex:" + ex);
            //Si ocurre un error en el servidor entonces se envia un mensaje al cliente notificando del error en el mismo            
            respuesta.add("error al referenciar a un objeto remoto");
            return respuesta;
        }
        return respuesta;
    }
	
    private String validarFecha(String fecha) {
        // modulo que a partir de la fecha recibida verfica si cumple el formato, y luego verifica que sea una fecha valida
        // en caso de no serlo retorna el error que tiene la fecha, caso contrario retorna "valida"
        String respuesta = "";
        int indexA = fecha.indexOf("-"), indexB = fecha.indexOf("-", indexA + 1), dia, mes, anio;

        //se verifica que la fecha recibida cumpla el formato DD-MM-A... (el año puede ser desde 0 en adelante)    
        if (indexA == 2 && indexB == 5 && fecha.length() >= 7) {
            try {
                //luego se obtiene los valores para cada dia, mes y año.
                //si alguno no es un nro entero, entonces se lanza una excepción y se invalida la operación
                dia = Integer.parseInt(fecha.substring(0, indexA));
                mes = Integer.parseInt(fecha.substring(indexA + 1, indexB));
                anio = Integer.parseInt(fecha.substring(indexB + 1));

                // se controla que el dia sea valido
                if (dia >= 1 && dia <= 31) {
                    // si el mes es febrero, se verifica si el año es bisiesto, y que el dia sea menor a 29 dias,
                    // caso contrario 28 dias                    
                    if (mes == 2) {
                        if ((anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0) {
                            if (dia <= 29) {
                                respuesta = "valida";
                            } else {
                                //error en el dia recibido, la fecha tiene mas dias de los que permite febrero bisiesto
                                respuesta = "FD";
                            }
                        } else if (dia <= 28) {
                            respuesta = "valida";
                        } else {
                            //error en el dia recibido, la fecha tiene mas dias de los que permite febrero no bisiesto
                            respuesta = "FD";
                        }
                        //si no es feberero, se verifican para los meses restantes si tiene 30 o 31 dias
                    } else if ((mes == 4 || mes == 6 || mes == 7 || mes == 11) && dia <= 30) {
                        respuesta = "valida";
                    } else if ((mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) && dia <= 31) {
                        respuesta = "valida";
                    } else {
                        //error en el mes recibido, la fecha cuenta con un valor de mes invalido
                        respuesta = "FM";
                    }
                } else {
                    //error en el dia recibido, la fecha cuenta con más de 31 días o 0
                    respuesta = "FD";
                }
            } catch (NumberFormatException ex) {
                return "PC"; //Protocolo Clima
            }
        } else {
            // error en el formato de la fecha recibida, no se respetó el formato
            respuesta = "PC"; //Protocolo Clima
        }
        return respuesta;
    }

    private String validacion(String horoscopo, String fecha) {
        //modulo que valida la solicitud que hizo el cliente
        //retorna true/false en caso de que sea valida o no, segun corresponda
        //tambien devuelve el error que tiene la solicitud, en caso de tenerlo        
        String respuesta;

        //Primero se verifica si es valido para el Protocolo Clima y luego para el de Horoscopo
        if (validarFecha(fecha).equals("valida")) {
            if (horoscopo.length() == 2 && this.protocoloHoroscopo.localizar(horoscopo) != -1) {
                respuesta = "valida";
            } else {
                respuesta = "PH"; //la solicitud fue invalida, no cumple el protocolo (Protocolo Horoscopo)
            }
        } else {
            respuesta = "PC"; //la solicitud fue invalida, no cumple el protocolo (Protocolo Clima)                    
        }

        //aca borre el error CP, porque recibo parametros diferentes ///////////////
        return respuesta;
    }
}
