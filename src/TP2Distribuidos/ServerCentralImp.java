package TP2Distribuidos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gastón
 */
public class ServerCentralImp {

    private ServerHoroscopo svrHoroscopo;

    public ServerCentralImp() {

        try {
            this.svrHoroscopo = (ServerHoroscopo) Naming.lookup("//localhost:54321/ServerHoroscpoImp");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(horoscopo.recibirSolicitud("AR"));
    }

    public String recibirSolicitudCliente(String horoscopo, String fecha) {
        String resultado = "";
        try {

            resultado = this.svrHoroscopo.recibirSolicitud(horoscopo);
        } catch (RemoteException ex) {
            Logger.getLogger(ServerCentralImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultado;
    }

}
