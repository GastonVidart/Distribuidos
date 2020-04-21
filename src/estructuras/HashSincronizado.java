/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Lock;

/**
 *
 * @author Gastón
 */
public class HashSincronizado {
    //clase que vuelve sincroniza a la estructura HashTable implementada por java
    //esta clase bloquea toda la estructura ante lecturas y escrituras

    private Hashtable<String, String> cache;
    private Lock mutex = new Lock();

    public HashSincronizado() {
        this.cache = new Hashtable<String, String>();
    }

    public boolean put(String clave, String valor) {
        //metodo sincronizado para guardar un elemento en la estructura hash
        //bloquea toda la estructura
        boolean exito = false;
        try {
            this.mutex.lock();
            //si el metodo put() retorna null, significa que es una clave nueva, sino retorna el valor registrado
            if (this.cache.put(clave, valor) == null) {
                exito = true;
            }
            this.mutex.unlock();
        } catch (InterruptedException ex) {
            Logger.getLogger(HashSincronizado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }

    public String get(String clave) {
        //metodo sincronizado para buscar un elemento en la estructura hash
        //bloquea toda la estructura
        String valor = null;
        try {
            this.mutex.lock();
            //si el metodo put() retorna null, significa que es una clave nueva, sino retorna el valor registrado
            valor = this.cache.get(clave);
            this.mutex.unlock();
        } catch (InterruptedException ex) {
            Logger.getLogger(HashSincronizado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }

    public String toString() {
        String estructura = "";
        try {
            this.mutex.lock();
            Enumeration e = cache.keys();
            Object clave;
            Object valor;
            while (e.hasMoreElements()) {
                clave = e.nextElement();
                valor = cache.get(clave);
                estructura += "Clave : " + clave + " - Valor : " + valor + "\n";
            }
            this.mutex.unlock();
        } catch (Exception ex) {
            System.err.println("Error de CacheSincronizado toString()" + ex);
        }
        return estructura;
    }

}
