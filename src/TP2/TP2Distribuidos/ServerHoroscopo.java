package TP2.TP2Distribuidos;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerHoroscopo extends Remote {

    public String getHoroscopo(String solicitud, String clientName) throws RemoteException;

}
