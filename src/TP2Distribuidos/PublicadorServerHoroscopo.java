package TP2Distribuidos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PublicadorServerHoroscopo {

    private static String ipAdress = "127.0.0.1";
    private static int port = 10001;

    public static void main(String[] args) {
        //Iniciar el Log
        try {
            Log.startLog("LogCentral.txt");
        } catch (SecurityException | IOException ex) {
            System.out.println("->ServerHoroscopo: No se pudo iniciar el Log");
        }

        //Conectar a rmi
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
            Log.logInfo("ServerHoroscopo", "Se conecto a rmi");
        }
        
        //Publicar el ServidorHoroscopo en rmi
        try {
            ServerHoroscopo serverHoroscopo = new ServerHoroscopoImp();
            Naming.rebind("rmi://" + ipAdress + ":" + port + "/ServerHoroscopoImp", serverHoroscopo);            
            Log.logInfo("ServidorHoroscopo",  "Publicado");
            System.out.println("->ServidorHoroscopo: Publicado");
        } catch (RemoteException e) {
            Log.logError("ServidorHoroscopo", "Error de comunicacion - " + e.getMessage());
            System.err.println("->ServidorHoroscopo: Error de comunicacion: " + e.getMessage());
            System.exit(1);
        } catch (MalformedURLException e) {            
            Log.logError("ServidorHoroscopo", "Error en la URL de rmi - " + e.getMessage());
            System.err.println("->ServidorHoroscopo: Error en la URL de rmi: " + e.getMessage());
            System.exit(1);
        }
    }
}
