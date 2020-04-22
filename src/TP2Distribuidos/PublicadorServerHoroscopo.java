
package TP2Distribuidos;

import java.rmi.Naming;
import java.rmi.RemoteException;


public class PublicadorServerHoroscopo {
    private static final String ip = "127.0.0.1", puerto = "10001";

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }
        try {
            ServerHoroscopo serverHoroscopo = new ServerHoroscopoImp();
            Naming.rebind("rmi://" + ip + ":" + puerto + "/ServerHoroscopoImp", serverHoroscopo);
            System.out.println("Servidor Horoscopo Publicado");
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
