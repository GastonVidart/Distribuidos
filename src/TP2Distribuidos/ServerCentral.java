
package TP2Distribuidos;

// @author guido

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerCentral extends Remote{

    public String[] getPronostico(String horoscopo, String fecha) throws RemoteException;
    
}
