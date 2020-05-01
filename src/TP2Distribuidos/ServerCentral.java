package TP2Distribuidos;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerCentral extends Remote {

    public ArrayList<String> getPronostico(String horoscopo, String fecha, String clientName) throws RemoteException;

}
