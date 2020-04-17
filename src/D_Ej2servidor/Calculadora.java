// interface que contiene los métodos del servicio

import java.rmi.*;


public interface Calculadora extends Remote {
    public int suma (int a, int b) throws RemoteException; 
    public int resta (int a, int b) throws RemoteException;
    public int div (int a, int b) throws RemoteException;
    public int mul (int a, int b) throws RemoteException;

} 




