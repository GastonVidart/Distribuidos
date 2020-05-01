package TP2Distribuidos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PublicadorServerCentral {

    private static String ipAdress = "127.0.0.1";
    private static int port = 10001;

    public static void main(String[] args) {
        //Verificar los parametros recibidos
        String ipHoroscopo, ipClima;
        int portHoroscopo, portClima;
        if (args.length != 5) {
            System.err.println("Los parametros recibidos son incorrectos, "
                    + "se requiere direccion ip y puerto del servidor horoscopo, "
                    + "direccion ip y puerto del servidor clima y puerto donde"
                    + " se ejecutara el servidor central");
            return;
        } else {
            ipHoroscopo = args[0];
            ipClima = args[2];
            try {
                port = Integer.parseInt(args[4]);
                portHoroscopo = Integer.parseInt(args[1]);
                portClima = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                System.err.println("Puerto ingresado no valido");
                return;
            }
        }

        //Iniciar el Log
        try {
            Log.startLog("LogCentral.txt");
        } catch (SecurityException | IOException ex) {
            System.out.println("->ServerCentral: No se pudo iniciar el Log");
        }

        //Conectar a rmi
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }

        //Publicar el ServidorCentral en rmi
        try {
            ServerCentral serverCentral = new ServerCentralImp(ipHoroscopo, portHoroscopo, ipClima, portClima);
            Naming.rebind("rmi://" + ipAdress + ":" + port + "/ServerCentral", serverCentral);
            Log.logInfo("ServidorCentral", "Publicado");
            System.out.println("->ServidorCentral: Publicado");
        } catch (RemoteException e) {
            Log.logError("ServidorCentral", "Error de comunicacion - " + e.getMessage());
            System.err.println("->ServidorCentral: Error de comunicacion: " + e.getMessage());
            System.exit(1);
        } catch (MalformedURLException e) {
            Log.logError("ServidorCentral", "Error en la URL de rmi - " + e.getMessage());
            System.err.println("->ServidorCentral: Error en la URL de rmi: " + e.getMessage());
            System.exit(1);
        }
    }
}
