package TP2Distribuidos;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;

public class Cliente implements Runnable {

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
            Log.logger.info("Cliente: comienza su ejecución");
            
            serverCentral = (ServerCentral) Naming.lookup("//" + ipAdress + ":" + port + "/ServerCentral");
            Log.logger.info("Cliente: pudo conectarse al ServerCentral");
            
            respuestas = serverCentral.getPronostico(horoscopo, fecha);
            Log.logger.info("Cliente: solicito un pronostico del ServerCentral");
            
            if (respuestas.size() == 1) {
                mostrarError(respuestas.get(0));
                Log.logger.log(Level.INFO, "Cliente: recibe un mensaje de error: {0}", respuestas.get(0));
            } else if (respuestas.isEmpty() || respuestas.size() > 2) {
                mostrarError("SC");
                Log.logger.info("Cliente: hubo un error con el ServidorCentral");
            } else {
                String mensaje = "->" + name + " recibio: \n"
                        + "----Pronostico Horoscopo: " + respuestas.get(0)
                        + "\n----Pronostico Clima: " + respuestas.get(1);  
                System.out.println(mensaje);
                Log.logger.log(Level.INFO, "Cliente: muestra el mensaje:\n{0}", mensaje);                
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

    private boolean esError(String[] respuesta) {
        //Es un error solo si comienza con el prefijo "/error/"
        String error;
        boolean resultado = false;
        if (respuesta[0].startsWith("/error/")) {
            error = respuesta[0].substring("/error/".length());
            switch (error) {
                case "ESH":
                    System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DE HORÓSCOPOS");
                    break;
                case "PH":
                    System.out.println("ERROR: ERROR EN EL PROTOCOLO DEL HOROSCOPO");
                    break;
                default:
                    System.out.println("ERROR: " + error);
                    break;
            }
            resultado = true;
        }

        if (respuesta[1].startsWith("/error/")) {
            error = respuesta[1].substring("/error/".length());
            switch (error) {
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
                default:
                    System.out.println("ERROR: " + error);
                    break;
            }
            resultado = true;
        }

        /*if (respuesta[0] == null || respuesta[1] == null) {
            System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR CENTRAL");
            resultado = true;
        }*/
        return resultado;
    }

    private boolean esErrorPronostico(String pronosticoHoroscopo) {
        boolean resultado = true;
        if (pronosticoHoroscopo != null) {
            switch (pronosticoHoroscopo) {
                case "ESH":
                    System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DE HORÓSCOPOS");
                    break;
                case "PH":
                    System.out.println("ERROR: ERROR EN EL PROTOCOLO DEL HOROSCOPO");
                    break;
                default:
                    resultado = false;
                    break;
            }
        } else {
            System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR CENTRAL");
        }
        return resultado;
    }

    private boolean esErrorClima(String pronosticoClima) {
        boolean resultado = true;
        switch (pronosticoClima) {
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
        }
        return resultado;
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
