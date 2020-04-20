package TP2Distribuidos;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class ServerClimaImp extends UnicastRemoteObject implements ServerClima {

    private String[] pronostico;
    private int dia, mes, anio; // no se pueden tener mas porque es el mismo objeto al que se llama siempre
    // hay que ponerlo en el informe

    public ServerClimaImp() throws RemoteException {
        super();
        this.pronostico = new String[]{"Lluvias Aisladas", "Lluvias Intensas", "Despejado", "Tormentas",
            "Nublado", "Viento", "Chaparrones", "Ciclón", "Ráfagas Fuertes", "Relámpagos"};
    }

    @Override
    public String getClima(String fecha) throws RemoteException {
        //modulo que recibe las solicitudes de un cliente y a partir de ella, primero verifca que sea valida
        //y luego si lo es, obtiene un pronostico del clima para la fecha solicitada, contestandosela al cliente
        //caso contrario contesta que recibio una solicitud incorrecta
        String respuesta = "", validacion;
        Random aleatorio = new Random();
        boolean exito;
        int i, dia, mes, anio,
                indexA = fecha.indexOf("-"), indexB = fecha.indexOf("-", indexA + 1);
        try {
            System.out.println("Llamada a getClima() | Clima Imp | Id Remota: " + this.ref);

            validacion = validarFecha(fecha);
            exito = validacion.equals("valida");
            if (exito) {
                System.out.println("La fecha recibida es Valida: " + fecha + " Clima Imp | Id Remota: " + this.ref);
                //se obtiene un pronostico aleatorio haciendo un calculo simple con la fecha recibida
                //se simula su procesamiento (tiempo de espera 1 seg)
                dia = Integer.parseInt(fecha.substring(0, indexA));
                mes = Integer.parseInt(fecha.substring(indexA + 1, indexB));
                anio = Integer.parseInt(fecha.substring(indexB + 1));
                synchronized (this) {
                    this.wait(1000);
                    i = (dia + mes + anio + aleatorio.nextInt(1000)) % this.pronostico.length;
                }
                respuesta = this.pronostico[i];
            } else {
                //respuesta = "La fecha recibida es Invalida " + validacion + "Clima Imp | Id Remota: " + this.ref;
				respuesta = validacion;
            }
        } catch (InterruptedException ex) {
            System.err.println("Ocurrió un error al procesar el pronóstico | Clima Imp | Id Remota: "
                    + this.ref + "\nEx:" + ex);
            //se notifica al cliente del error en el servidor
            return "error al procesar pronóstico";
        }
        return respuesta;
    }

    /*private int[] resolverFecha(String solicitud) {
        //modulo que se encarga de validar que la fecha recibida cumpla el formato de la misma,
        //este es DD-MM-A... (el año puede ser desde 0 en adelante), y que los valores sean numeros.
        //retorna un arreglo que indica en la primer posicion si la fecha es valida o no,
        //y en los restantes los valores de dia, mes y año respectivamente
        int indexA = solicitud.indexOf("-"), indexB = solicitud.indexOf("-", indexA + 1);

        int[] fechaResuelta = new int[4];
        // [0] = (1,0) si la fecha es valida o no
        // [1] = valor de la posicion dia en la solicitud recibida
        // [2] = valor de la posicion mes en la solicitud recibida
        // [3] = valor de la posicion año en la solicitud recibida        

        fechaResuelta[0] = 0; //por defecto es inválida, si cumple el formato y todos los valores son nros se valida 
        if (indexA == 2 && indexB == 5 && solicitud.length() >= 7) {
            try {
                fechaResuelta[1] = Integer.parseInt(solicitud.substring(0, indexA));
                fechaResuelta[2] = Integer.parseInt(solicitud.substring(indexA + 1, indexB));
                fechaResuelta[3] = Integer.parseInt(solicitud.substring(indexB + 1));
                fechaResuelta[0] = 1;
            } catch (NumberFormatException ex) {
                fechaResuelta[0] = 0;
            }
        }
        return fechaResuelta;
    }*/
    private String validarFecha(String fecha) {
        // modulo que a partir de la fecha recibida verfica si cumple el formato, y luego verifica que sea una fecha valida
        // en caso de no serlo retorna el error que tiene la fecha, caso contrario retorna "valida"
        String respuesta = "";
        int indexA = fecha.indexOf("-"), indexB = fecha.indexOf("-", indexA + 1),
                dia, mes, anio;

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
                                respuesta = "error(FD)";
                            }
                        } else if (dia <= 28) {
                            respuesta = "valida";
                        } else {
                            //error en el dia recibido, la fecha tiene mas dias de los que permite febrero no bisiesto
                            respuesta = "error(FD)";
                        }
                        //si no es feberero, se verifican para los meses restantes si tiene 30 o 31 dias
                    } else if ((mes == 4 || mes == 6 || mes == 7 || mes == 11) && dia <= 30) {
                        respuesta = "valida";
                    } else if ((mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) && dia <= 31) {
                        respuesta = "valida";
                    } else {
                        //error en el mes recibido, la fecha cuenta con un valor de mes invalido
                        respuesta = "error(FM)";
                    }
                } else {
                    //error en el dia recibido, la fecha cuenta con más de 31 días o 0
                    respuesta = "error(FD)";
                }
            } catch (NumberFormatException ex) {
                return "error(PC)"; //Protocolo Clima
            }
        } else {
            // error en el formato de la fecha recibida, no se respetó el formato
            respuesta = "error(PC)"; //Protocolo Clima
        }
        return respuesta;
    }
}
