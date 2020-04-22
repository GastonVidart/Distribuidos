/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpcursada;

import Utiles.TecladoIn;
public class TpCursada {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Este codigo permite leer una cadena de texto y hacer varias operaciones con ella
        
        String cadena;
        int opciones;
        
        System.out.println("Ingrese una cadena de texto.");
        cadena = TecladoIn.readLine();
     
        System.out.println("Elija una operacion para la cadena:");
        System.out.println("1) Leer solo las letras y los espacios en blanco.");
        System.out.println("2) Contar las palabras de menor longitud a una longitud deseada (Introducir la longitud).");
        System.out.println("3) Buscar la palabra mas larga.");
        System.out.println("4) Armar una cadena nueva con las palabras en posiciones pares.");
        opciones = TecladoIn.readLineInt();
        
        switch (opciones) 
        {
            //OPCION 1
            case 1:
                //Lee solo las letras y espacios y devuelve una cadena nueva conformada solo por estas
                int i;
                char caracter;
                String cadena2 = "";
                int longitud = cadena.length();
                for (i = 0; i < longitud; i++)
                {
                    caracter = cadena.charAt(i);
                    if (checkChar(caracter) == true)
                    {
                        cadena2 = cadena2 + caracter;
                    }
                }
                System.out.println("La nueva cadena de texto es: \""+cadena2+"\".");
            break;
            
            //OPCION 2
            case 2:
                //Cuenta las palabras que son menores a n longitud
                System.out.println("Ingrese la longitud a la que deben ser menor las palabras: ");
                int longPalabra = TecladoIn.readLineInt(), j, acum = 0;
                String palabra = "";
                int ultimaPos = cadena.length() - 1;
                System.out.println(longPalabra);
                for (j = 0; j < cadena.length(); j++)       //Repite el ciclo "la longitud de la cadena" veces
                {
                    //palabra = palabra + cadena.charAt(j);       //Crea una plabra
                    
                    if ((cadena.charAt(j) == ' ') || (j == ultimaPos))      //determina cuando termina una palabra
                    {
                        if (palabra.length() < longPalabra)
                        {System.out.println(palabra);
                            acum = acum + 1;
                            palabra = "";
                        }else{palabra="";}
                    }else{ palabra = palabra + cadena.charAt(j);}
                }
                
                System.out.println("La cantidad de palabras que son menores a '"+longPalabra+"' longitud son: "+acum);   
            break;
            
            //OPCION 3
            case 3:
                //Devuelve la palabra mas larga de la cadena
                palabraLarga(cadena);
            break;
            
            //OPCION 4
            case 4:
                //Devuelve una cadena comformada por las palabras en posiciones pares
                cadenaPar(cadena);
            break;
        }    
    }
    
    public static boolean checkChar (char carac)
    {
        //Verifica que el caracter recibido sea una letra o un espacio en blanco, si lo es retorna true, sino, false
        boolean valor;
        if ((carac == 'a') || (carac == 'b') || (carac == 'b') || (carac == 'c') || (carac == 'd') || 
            (carac == 'e') || (carac == 'f') || (carac == 'g') || (carac == 'h') || (carac == 'i') || 
            (carac == 'j') || (carac == 'k') || (carac == 'l') || (carac == 'm') || (carac == 'ñ') ||
            (carac == 'n') || (carac == 'o') || (carac == 'p') || (carac == 'q') || (carac == 'r') || 
            (carac == 's') || (carac == 't') || (carac == 'u') || (carac == 'v') || (carac == 'w') || 
            (carac == 'x') || (carac == 'y') || (carac == 'z') || (carac == ' ') ||
            (carac == 'A') || (carac == 'B') || (carac == 'b') || (carac == 'C') || (carac == 'D') || 
            (carac == 'E') || (carac == 'F') || (carac == 'G') || (carac == 'H') || (carac == 'I') || 
            (carac == 'J') || (carac == 'K') || (carac == 'L') || (carac == 'M') || (carac == 'Ñ') ||
            (carac == 'N') || (carac == 'O') || (carac == 'P') || (carac == 'Q') || (carac == 'R') || 
            (carac == 'S') || (carac == 'T') || (carac == 'U') || (carac == 'V') || (carac == 'W') || 
            (carac == 'X') || (carac == 'Y') || (carac == 'Z'))
        {
            valor = true;
        }
        else
        {
            valor = false;
        }
        return valor;
    }
    
    public static void palabraLarga (String cadena)
    {
        //Verifica cual es la palabra mas larga de una cadena
        int ultimaPos = cadena.length() - 1, p;
        String palabra = "", palabraL = "";
        
        for (p = 0; p < cadena.length(); p++)   //Repite el ciclo "la longitud de la cadena" veces
        {
            palabra = palabra + cadena.charAt(p);   //Crea una palabra
            
            if ((cadena.charAt(p) == ' ') || (p == ultimaPos))      //Determina cuando termina una palabra
            {
                if (palabra.length() > palabraL.length())   //Compara el largo de la palabra con la palabra mas larga.
                {                                           //Si es mas grande la palabra que la palabra mas larga actual,
                    palabraL = palabra;                     //asigna la nueva palabra a la mas larga
                }
                palabra = "";
            }
        }
        System.out.println("La palabra mas larga es: " +palabraL);
    }
    
    public static void cadenaPar (String cadena)
    {
        //Devuelve las palabras en posiciones pares de una cadena
        int ultimaPos = cadena.length() - 1, p, posicion = 0, esPar = 0;
        String palabra = "", cadenaPares = "";
        
        for (p = 0; p < cadena.length(); p++)   //Repite el ciclo "la longitud de la cadena" veces
        {
           
            palabra=palabra+cadena.charAt(p);
            if ((cadena.charAt(p) == ' ') || (p == ultimaPos))      //Determina cuando termina una palabra
            {
                posicion = posicion + 1;
                esPar = posicion % 2;
                
                if (esPar == 0)      //Por medio de una cuenta, se determina si la palabra esta en una posicion par o no           
                {                                 
                    cadenaPares = cadenaPares + palabra;
                }
                palabra = "";
            }}
        System.out.println("La nueva cadena es: " +cadenaPares);
    }
}