package D_Ej1Cliente;

import java.rmi.*;
import java.rmi.server.*;

class ClienteEco {

    static public void main(String args[]) {
        /*if (args.length<2) {
            System.err.println("Uso: ClienteEco IPServidor PuertoServidor ...");
            return;
        }*/

        System.setProperty("java.security.policy", "cliente.permisos");
        //if (System.getSecurityManager() == null)        
        System.setSecurityManager(new SecurityManager());

        try {

            String ip = "localhost", puerto = "54321";

            //ServicioEco srv = (ServicioEco) Naming.lookup("//" + args[0] + ":" + args[1] + "/Eco");
            ServicioEco srv = (ServicioEco) Naming.lookup("//" + ip + ":" + puerto + "/Eco");

            for (int i = 2; i < args.length; i++) {
                System.out.println(srv.eco("hola"));
            }
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
        } catch (Exception e) {
            System.err.println("Excepcion en ClienteEco:");
            e.printStackTrace();
        }
    }
}
