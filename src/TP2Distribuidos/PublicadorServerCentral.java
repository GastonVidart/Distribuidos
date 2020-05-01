import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class PublicadorServerCentral {

    private static String ipAdress = "127.0.0.1";
    private static int port = 10001;

    public static void main(String[] args) {
        //Iniciar el Log
        try {
            Log.startLog("LogCentral.txt");
        } catch (SecurityException | IOException ex) {
            System.out.println("->ServerCentral: No se pudo iniciar el Log");
        }

        //Conectar a rmi
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname", "localhost");
        }

        //Publicar el ServidorCentral en rmi
        try {
            ServerCentral serverCentral = new ServerCentralImp(ipAdress, port);
            Naming.rebind("rmi://" + ipAdress + ":" + port + "/ServerCentral", serverCentral);
            Log.logInfo("ServidorCentral", "Publicado");
            System.out.println("->ServidorCentral: Publicado");
        } catch (RemoteException e) {
            Log.logError("ServidorCentral", "Error de comunicacion - " + e.getMessage());
            System.err.println("->ServidorCentral: Error de comunicacion: " + e.getMessage());
            System.exit(1);
        } catch (MalformedURLException e) {
            Log.logError("ServidorCentral", "Error en la URL de rmi - " + e.getMessage());
            System.err.println("->ServidorCentral: Error en la URL de rmi: " + e.getMessage());
            System.exit(1);
        }
    }
}
