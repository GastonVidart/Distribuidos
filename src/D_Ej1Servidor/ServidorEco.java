package D_Ej1Servidor;

import java.rmi.*;

class ServidorEco {

    static public void main(String args[]) {
        /*if (args.length!=2) {
            System.err.println("Uso: ServidorEco IP Puerto");
            return;
        }*/
        //System.setProperty("java.security.policy", "servidor.permisos");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {            
            
            String ip = "localhost", puerto="54321";

            ServicioEcoImpl srv = new ServicioEcoImpl();
            //Naming.rebind("rmi://"+ args[0] + ":" + args[1] + "/Eco", srv);            
            Naming.rebind("rmi://" + ip + ":" + puerto + "/Eco", srv);
            System.out.println("Esperando conexiones");
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Excepcion en ServidorEco: " + e.toString());
            System.exit(1);
        }
    }
}
