package TP2Distribuidos;

// @author guido
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cliente implements Runnable {

    private String name;
    private String ipAdress = "127.0.0.1";
    private int port = 10000;
    private ServerCentral server;
    private String horoscopo, fecha;

    @Override
    public void run() {
        try {
            server = (ServerCentral) Naming.lookup("//" + ipAdress + ":" + port + "/CalculadoraImp");
            String[] respuesta = server.getPronostico(horoscopo, fecha);
            if (!esError(respuesta)) {
                System.out.println("->" + name + " recibio: \n"
                        + "----Pronostico Horoscopo:" + respuesta[0]
                        + "----Pronostico Clima: " + respuesta[1]);
            }
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println(ex);
        }
    }

    private boolean esError(String[] respuesta) {
        //Es un error solo si comienza con el prefijo "/error/"
        String error;
        boolean resultado = false;
        if (respuesta[0].startsWith("/error/")) {
            error = respuesta[0].substring("/error/".length());
            switch (error) {
                case "ESH":
                    System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DE HORÓSCOPOS");
                    break;
                case "PH":
                    System.out.println("ERROR: ERROR EN EL PROTOCOLO DEL HOROSCOPO");
                    break;
                default:
                    System.out.println("ERROR: " + error);
                    break;
            }
            resultado = true;
        }

        if (respuesta[1].startsWith("/error/")) {
            error = respuesta[1].substring("/error/".length());
            switch (error) {
                case "ESC":
                    System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DEL CLIMA");
                    break;
                case "FD":
                    System.out.println("ERROR: DIA NO VALIDO");
                    break;
                case "PC":
                    System.out.println("ERROR: FECHA NO VALIDA");
                    break;
                case "FM":
                    System.out.println("ERROR: MES NO VALIDO");
                    break;
                default:
                    System.out.println("ERROR: " + error);
                    break;
            }
            resultado = true;
        }

        if (respuesta[0] == null || respuesta[1] == null) {
            System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR CENTRAL");
            resultado = true;
        }
        return resultado;
    }
}
