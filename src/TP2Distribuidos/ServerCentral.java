
package TP2Distribuidos;

// @author guido

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerCentral extends Remote{
    
    public ArrayList<String> getPronostico(String horoscopo, String fecha) throws RemoteException;
    
//    public String getPronosticoHoroscopo(String horoscopo) throws RemoteException;
//    
//    public String getPronosticoClima(String fecha) throws RemoteException;
    
    
}
