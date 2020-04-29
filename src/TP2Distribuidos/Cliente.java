package TP2Distribuidos;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Cliente implements Runnable {

    private String name;
    private String ipAdress = "127.0.0.1";
    private int port = 10001;
    private ServerCentral serverCentral;
    private String horoscopo;
    private String fecha;

    public Cliente(String name, String horoscopo, String fecha) {
        this.name = name;
        this.horoscopo = horoscopo;
        this.fecha = fecha;
    }

    public Cliente(String name, String horoscopo, String fecha, String ipAdress, int port) {
        this.name = name;
        this.horoscopo = horoscopo;
        this.fecha = fecha;
        this.ipAdress = ipAdress;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            //Conectar al ServerCentral mediante rmi
            serverCentral = (ServerCentral) Naming.lookup("//" + ipAdress + ":" + port + "/ServerCentral");
            Log.logInfo(name, "Se conectó al Servidor Central");            

            //Solicitar un pronostico
            ArrayList<String> respuestas;
            Log.logInfo(name, "Solicita un pronostico con la tupla <" + horoscopo + ", " + fecha + ">");
            respuestas = serverCentral.getPronostico(horoscopo, fecha);

            //Procesar la respuesta
            if (respuestas.size() == 1) {
                mostrarError(respuestas.get(0));
            } else if (respuestas.isEmpty() || respuestas.size() > 2) {
                mostrarError("SC");
            } else {
                Log.logInfo(name, "recibio: \n"
                        + "----Pronostico Horoscopo: " + respuestas.get(0)
                        + "\n----Pronostico Clima: " + respuestas.get(1));
                System.out.println("->" + name + " recibio: \n"
                        + "----Pronostico Horoscopo: " + respuestas.get(0)
                        + "\n----Pronostico Clima: " + respuestas.get(1));
            }
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Log.logError(name, ex.getMessage());
            System.err.println("->" + name + ": ERROR: " + ex.getMessage());
        }
    }

    private void mostrarError(String error) {
        String msg = "ERROR: DESCONOCIDO";
        switch (error) {
            case "ESH":
                msg = "ERROR: ERROR POR PARTE DEL SERVIDOR DE HORÓSCOPOS";
                break;
            case "PH":
                msg = "ERROR: ERROR EN EL PROTOCOLO DEL HOROSCOPO";
                break;
            case "ESC":
                msg = "ERROR: ERROR POR PARTE DEL SERVIDOR DEL CLIMA";
                break;
            case "FD":
                msg = "ERROR: DIA NO VALIDO";
                break;
            case "PC":
                msg = "ERROR: FECHA NO VALIDA";
                break;
            case "FM":
                msg = "ERROR: MES NO VALIDO";
                break;
            case "SC":
                msg = "ERROR: ERROR DEL SERVIDOR CENTRAL";
                break;
        }

        System.err.println("->" + name + ": " + msg);
        Log.logError(name, msg);

    }
}
