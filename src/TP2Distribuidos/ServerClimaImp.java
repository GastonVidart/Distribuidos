package TP2Distribuidos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Gastón
 */
public class ServerClimaImp extends UnicastRemoteObject implements ServerClima {

    public ServerClimaImp() throws RemoteException {
        super();
    }

    @Override
    public String getClima(String fecha) throws RemoteException {

    }

}
