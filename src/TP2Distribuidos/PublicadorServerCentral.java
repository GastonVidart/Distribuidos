package TP2Distribuidos;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author Gastón
 */
public class PublicadorServerCentral {
    
    private static final String ip = "127.0.0.1";
    private static final int puerto = 10001;

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }
        
        try {
            ServerCentral serverCentral = new ServerCentralImp(ip, puerto);
            Naming.rebind("rmi://" + ip + ":" + puerto + "/ServerClima", serverCentral);
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
