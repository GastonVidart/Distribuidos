
package TP2Distribuidos;


import java.rmi.Naming;
import java.rmi.RemoteException;


public class Servidor {
    static public void main(String args[]) {
 	    if (args.length!=1) { System.err.println("Uso: Servidor Puerto"); return; }
            if (System.getSecurityManager() == null) {
        	// System.setSecurityManager(new RMISecurityManager()); 
	       	System.setProperty("java.rmi.server.hostname","localhost");}
        try { 
            ServerHoroscopo svrH = new ServerHoroscopoImp();
            Naming.rebind("rmi://localhost:" + args[0] + "/ServerHoroscopoImp", svrH);  }
        catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); }
        catch (Exception e) {
            System.err.println("Excepcion en ServidorEco:");
            e.printStackTrace();
            System.exit(1); }
        }
    
}
