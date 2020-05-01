package TP2Distribuidos;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerClima extends Remote {

    public String getClima(String fecha, String clientName) throws RemoteException;

}
