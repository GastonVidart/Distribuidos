package TP2Distribuidos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.*;

class PublicadorServerClima {

    private static String ipAdress = "127.0.0.1";
    private static int port = 10001;

    public static void main(String[] args) {
        //Iniciar el Log
        try {
            Log.startLog("LogCentral.txt");
        } catch (SecurityException | IOException ex) {
            System.out.println("->ServerClima: No se pudo iniciar el Log");
        }

        //Conectar a rmi
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }
        
        //Publicar el ServidorClima en rmi
        try {
            ServerClima serverClima = new ServerClimaImp();
            Naming.rebind("rmi://" + ipAdress + ":" + port + "/ServerClimaImp", serverClima);           
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
