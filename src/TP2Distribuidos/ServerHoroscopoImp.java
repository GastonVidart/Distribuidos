package TP2Distribuidos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerHoroscopoImp extends UnicastRemoteObject implements ServerClima {

    public ServerHoroscopoImp() throws RemoteException {
        super();
    }

    @Override
    public String getClima(String fecha) throws RemoteException {

    }

}
