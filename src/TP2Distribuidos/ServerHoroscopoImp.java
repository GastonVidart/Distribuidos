package TP2Distribuidos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import estructuras.*;
import java.util.Random;

public class ServerHoroscopoImp extends UnicastRemoteObject implements ServerHoroscopo {

    private ListString protocoloHoroscopo;

    private static String[] predicciones = {"Para sanar sus heridas se debera cumplir un reto que se le presentara al medio dia",
        "Se debera cuidar de sus amigos, pues ellos podran revelarle algo inesperado",
        "Las relaciones con sus pares seran muy llenadoras, aprovechelas porque no se le presentara por un tiempo",
        "Arriesguese a lo que desea, hoy es un dia para hacerle frente a su futuro",
        "Aproveche el tiempo libre para hacer ejercicio, su cuerpo y alma se lo agradecera",
        "Aveces las relaciones son toxicas, mire a su alrededor y observe como se comportan los demas con su compasion",
        "La luna muestra su nueva cara, todo indica que algo nuevo va a ocurrir en su contexto y le cambiara la vida para siempre",
        "Momentos para trabajar duro en sus proyectos y metas, no deje que nadie le diga que no puede hacer algo",
        "Aprovecha para darte atencion a las pequeñas cosas que aportan a que tu vida se lleve a cabo con serenidad",
        "No hagas caso a la gente que habla mal de tu persona, sigue tus instintos, ellos te llevaran muy lejos"};

    public ServerHoroscopoImp() throws RemoteException {
        //modulo que inicializa el servidorHiloHorosocopo; recibe el socket, el identificador de la conexion,
        //un arreglo de las predicciones posibles para cada horosocopo y un arreglo con el protocolo de horosocopo
        super();

        this.protocoloHoroscopo = new ListString();

        String[] horoscopos = {"AR", "TA", "GE", "CC", "LE", "VG", "LB", "ES", "SA", "CP", "AC", "PI"};
        int largoHoroscopo = horoscopos.length;
        for (int i = 0; i < largoHoroscopo; i++) {
            //Llenamos la lista con el protocolo de horoscopo
            this.protocoloHoroscopo.insertar(horoscopos[i], i + 1);
        }

    }

    @Override
    public String getHoroscopo(String peticion) throws RemoteException {
        //modulo que recibe las solicitudes de un cliente y a partir de ella, primero verifca que sea valida
        //y luego si lo es, obtiene una prediccion para el signo solicitado, contestandosela al cliente
        //caso contrario contesta que recibio una solicitud incorrecta

        String solicitud, respuesta;
        Random aleatorio = new Random();

        solicitud = peticion;
        System.out.println("Llamada a getHoroscopo() | Horoscopo Imp | Id Remota: " + this.ref);
        //System.out.println(verificar(solicitud));  
        if (peticion.length() == 2 && this.protocoloHoroscopo.localizar(peticion) != -1) {
            System.out.println("El signo recibido es Valido " + solicitud + " Horoscopo Imp || Id Remota: " + this.ref);            
            //se obtiene una predicción aleatoria y se simula su procesamiento (tiempo de espera 1 seg)
            synchronized (this) {
                try {
                    this.wait(1000);
                } catch (InterruptedException ex) {
                    System.err.println("Ocurrió un error al procesar el pronóstico | Clima Imp | Id Remota: "
                            + this.ref + "\nEx:" + ex);
                    //se notifica al cliente del error en el servidor
                    return "Error al procesar horóscopo";
                }
                respuesta = this.predicciones[aleatorio.nextInt(this.predicciones.length)];
            }
        } else {
            respuesta="error(PH)"; //la solicitud fue invalida, no cumple el protocolo (Protocolo Horoscopo)           
        }

        return respuesta;
    }
}
