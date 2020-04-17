import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculadoraImp extends UnicastRemoteObject implements Calculadora
{
    protected CalculadoraImp() throws RemoteException
    { super(); }
   
    public int suma(int a, int b) throws RemoteException 
    { return a+b; }
    
    public int resta(int a, int b) throws RemoteException 
    { return a-b; }
    
    public int div(int a, int b) throws RemoteException 
    { return a/b; }
     
    public int mul(int a, int b) throws RemoteException 
    { return a*b; }
}


