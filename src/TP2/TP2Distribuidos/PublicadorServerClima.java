package TP2.TP2Distribuidos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;

class PublicadorServerClima {

    private static String ipAdress = "127.0.0.1"; //La ip se define local por defecto
    private static int port;

    public static void main(String[] args) {
        //Verificar los parametros recibidos
        if (args.length != 1) {
            System.err.println("El parametro es incorrecto, ingrese el puerto donde se conectara");
            return;
        } else {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Puerto ingresado no valido");
                return;
            }
        }

        //Iniciar el Log
        try {
            Log.startLog("LogClima.txt");
        } catch (SecurityException | IOException ex) {
            System.out.println("->ServerClima: No se pudo iniciar el Log");
        }

        //Se verifican las propiedades de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }

        //Publicar el ServidorClima en rmi
        try {
            ServerClima serverClima = new ServerClimaImp();
            Naming.rebind("rmi://" + ipAdress + ":" + port + "/ServerClima", serverClima);           
            Log.logInfo("ServidorClima",  "Publicado");
            System.out.println("->ServidorClima: Publicado");
        } catch (RemoteException e) {
            Log.logError("ServidorClima", "Error de comunicacion - " + e.getMessage());
            System.err.println("->ServidorClima: Error de comunicacion: " + e.getMessage());
            System.exit(1);
        } catch (MalformedURLException e) {
            Log.logError("ServidorClima", "Error en la URL de rmi - " + e.getMessage());
            System.err.println("->ServidorClima: Error en la URL de rmi: " + e.getMessage());
            System.exit(1);
        }
    }
}
