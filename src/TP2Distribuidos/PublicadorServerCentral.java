package TP2Distribuidos;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PublicadorServerCentral {
    
    private static final String ip = "127.0.0.1";
    private static final int puerto = 10001;

    public static void main(String[] args) {
        try {
            Log.startLog("LogCentral.txt");
        } catch (SecurityException | IOException ex) {            
            //Logger.getLogger(PublicadorServerCentral.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }
        
        try {
            ServerCentral serverCentral = new ServerCentralImp(ip, puerto);
            Log.logger.info("Se creo el servercentral");
            Naming.rebind("rmi://" + ip + ":" + puerto + "/ServerCentral", serverCentral);
            Log.logger.info("Se publico el servercentral");
            System.out.println("Servidor Central Publicado");
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Excepcion en ServidorEco:");
            e.printStackTrace();
            System.exit(1);
        }
    }    
}
