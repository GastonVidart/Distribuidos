package TP2.TP2Distribuidos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PublicadorServerCentral {

    private static String ipAdress = "127.0.0.1"; //La ip se define local por defecto
    private static int port;

    public static void main(String[] args) {
        //Verificar los parametros recibidos
        String ipHoroscopo, ipClima;
        int portHoroscopo, portClima;
        if (args.length != 5) {
            System.err.println("Los parametros recibidos son incorrectos se requiere:"
                    + "\nDireccion ip y puerto del servidor horoscopo, "
                    + "\nDireccion ip y puerto del servidor clima,"
                    + "\nPuerto donde se ejecutara el servidor central");
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

        //Se verifican las propiedades de seguridad
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
