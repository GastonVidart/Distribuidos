package D_Ej1Servidor;
// Implementación del servicio

import java.rmi.*;
import java.rmi.server.*;

class ServicioEcoImpl extends UnicastRemoteObject implements ServicioEco {

    ServicioEcoImpl() throws RemoteException {
    }

    @Override
    public String eco(String s) throws RemoteException {
        return s.toUpperCase();
    }
}
