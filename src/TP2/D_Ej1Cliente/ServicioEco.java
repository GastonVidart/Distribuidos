package TP2.D_Ej1Cliente;

// interface que contiene los métodos del servicio

import java.rmi.*;

interface ServicioEco extends Remote {
        String eco (String s) throws RemoteException;
}
