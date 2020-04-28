package TP2Distribuidos;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Cliente implements Runnable {
    //comentario para commit////////////////////////////////////////

    private String name;
    private String ipAdress = "127.0.0.1";
    private int port = 10001;
    private ServerCentral serverCentral;
    private ServerClima serverC;
    private ServerHoroscopo serverH;
    private String horoscopo;
    private String fecha;

    public Cliente(String name, String horoscopo, String fecha) {
        this.name = name;
        this.horoscopo = horoscopo;
        this.fecha = fecha;
    }

    @Override
    public void run() {
        try {
//            String pronosticoHoroscopo, pronosticoClima;
            ArrayList<String> respuestas;
            serverCentral = (ServerCentral) Naming.lookup("//" + ipAdress + ":" + port + "/ServerCentral");

            respuestas = serverCentral.getPronostico(horoscopo, fecha);

            if (respuestas.size() == 1) {
                mostrarError(respuestas.get(0));
            } else if (respuestas.isEmpty() || respuestas.size() > 2) {
                mostrarError("SC");
            } else {
                System.out.println("->" + name + " recibio: \n"
                        + "----Pronostico Horoscopo: " + respuestas.get(0)
                        + "\n----Pronostico Clima: " + respuestas.get(1));
            }

//            pronosticoClima = serverCentral.getPronosticoClima(fecha);
//            if (!esErrorClima(pronosticoClima)) {
//
//                pronosticoHoroscopo = serverCentral.getPronosticoHoroscopo(horoscopo, fecha);
//                if (!esErrorPronostico(pronosticoHoroscopo)) {
//                    
//                }
//            }
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println(ex);
        }
    }

    private void mostrarError(String error) {
        switch (error) {
            case "ESH":
                System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DE HORÓSCOPOS");
                break;
            case "PH":
                System.out.println("ERROR: ERROR EN EL PROTOCOLO DEL HOROSCOPO");
                break;
            case "ESC":
                System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DEL CLIMA");
                break;
            case "FD":
                System.out.println("ERROR: DIA NO VALIDO");
                break;
            case "PC":
                System.out.println("ERROR: FECHA NO VALIDA");
                break;
            case "FM":
                System.out.println("ERROR: MES NO VALIDO");
                break;
            case "SC":
                System.out.println("ERROR: ERROR DEL SERVIDOR CENTRAL");
                break;
        }
    }
}
