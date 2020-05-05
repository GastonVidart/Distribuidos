package TP2Distribuidos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ServerHoroscopoImp extends UnicastRemoteObject implements ServerHoroscopo {

    private ArrayList<String> protocoloHoroscopo;
    private String SERVIDORHOROSCOPO = "ServidorHoroscopo";

    private static String[] pronostico = {"Para sanar sus heridas se debera cumplir un reto que se le presentara al medio dia",
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
        super();

        Log.logInfo(SERVIDORHOROSCOPO, "Se crea una nueva instancia con id: " + this.ref);
        System.out.println("->ServidorHoroscopo: Se crea una nueva instancia");
        this.protocoloHoroscopo = new ArrayList<>();
        this.protocoloHoroscopo.addAll(Arrays.asList(
                new String[]{"AR", "TA", "GE", "CC", "LE", "VG", "LB", "ES", "SA", "CP", "AC", "PI"}));
    }

    @Override
    public String getHoroscopo(String horoscopo, String clientName) throws RemoteException {
        //Se verifica que la horoscopo sea válida y se responde con un 
        //pronostico si lo es, o un mensaje de error en caso contrario
        SERVIDORHOROSCOPO += "-" + clientName;
        Log.logInfo(SERVIDORHOROSCOPO, "Se solicita un horoscopo para el signo: " + horoscopo);
        System.out.println("->ServidorHoroscopo: Se solicita un horoscopo");
        String respuesta;
        Random aleatorio = new Random();

        if (horoscopo.length() == 2 && protocoloHoroscopo.contains(horoscopo)) {
            Log.logInfo(SERVIDORHOROSCOPO, "Solicitud valida");
            synchronized (this) {
                //Se obtiene una predicción aleatoria y se simula su procesamiento (tiempo de espera 1 seg)
                try {
                    this.wait(1000);
                } catch (InterruptedException ex) {
                    Log.logError(SERVIDORHOROSCOPO, "Error en el procesamiento del pronóstico: " + ex.getMessage());
                    System.err.println("->ServidorHoroscopo: Error en el procesamiento del pronóstico");
                    return "ESH";
                }
                respuesta = pronostico[aleatorio.nextInt(pronostico.length)];
            }
        } else {
            Log.logError(SERVIDORHOROSCOPO, "Solicitud invalida");
            respuesta = "PH"; //Solicitud no valida por el protocolo
        }
        Log.logInfo(SERVIDORHOROSCOPO, "Se responde al Cliente: " + respuesta);
        System.out.println("->ServidorHoroscopo: Se responde a una horoscopo");
        return respuesta;
    }
}
