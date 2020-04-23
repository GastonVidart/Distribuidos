package TP2Distribuidos;

import java.rmi.*;

class PublicadorServerClima {

    private static final String ip = "127.0.0.1", puerto = "10001";

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }
        try {
            ServerClima serverClima = new ServerClimaImp();
            Naming.rebind("rmi://" + ip + ":" + puerto + "/ServerClima", serverClima);
            System.out.println("Servidor Clima Publicado");
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
