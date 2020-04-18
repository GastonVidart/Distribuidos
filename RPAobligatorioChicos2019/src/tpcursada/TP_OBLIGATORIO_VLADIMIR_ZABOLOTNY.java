package tpcursada;

import Utiles.TecladoIn;
public class TP_OBLIGATORIO_VLADIMIR_ZABOLOTNY {

    //METODOS
       
    
    //Opcion 1 verificar si la oracion posee unicamente letras y espacio
    public static boolean verificacion_de_caracteres (String user_cadena) {
        boolean letra, espacio, verificacion = false;
        int cantidad= 0 ;
        
        for (int i=0; i< user_cadena.length(); i++) {
            espacio= Character.isWhitespace(user_cadena.charAt(i));
            letra= Character.isLetter(user_cadena.charAt(i));
            
            if ((letra) || (espacio)) {
                    cantidad++ ;            
            }
            else {   
                //si la cadena no es valida se le asigna false a la variable
                verificacion=false;
                System.out.println("La frase ingresada debe estar compuesta solo de letras");
                System.out.println("El caracter: " +user_cadena.charAt(i)+ " no es letra y tampoco espacio");
                break;
            }
        }
        
            if (cantidad== user_cadena.length()) {
            //si la cadena es valida se le asigna true a la variable
            verificacion=true;
            System.out.println("La oracion es valida");

            }
            
        //retorna la variable booleana con verificacion de la cadena
        return verificacion;
    }
    

    //Opcion 2 "Cuenta la cantidad de palabras de longitud menor a longitud dada"
    public static int opcion2 (String user_cadena, int longitud) {
        
        boolean letra, espacio;
        int cant= 0;
        int lugar_palabra = 0;
        
        user_cadena = user_cadena + " ";
        
        for (int i=0; i< user_cadena.length(); i++) {
            
            espacio= Character.isWhitespace(user_cadena.charAt(i));
            letra= Character.isLetter(user_cadena.charAt(i));
            
            if (letra) {
                lugar_palabra++;
            }
            
            if (espacio) {
                
                if (lugar_palabra < longitud) {
                    cant++;
                        
                }
                
                lugar_palabra= 0;
            }
        }
                return cant;
    }
    
    
    //Opcion 3 "Buscar la palabra mas larga"
    public static String opcion3(String user_cadena) {
        
        String palabra= "";
        String pal_mas_larga="";
        char letra; 
        user_cadena= user_cadena + " ";
        
        for (int i=0; i<user_cadena.length(); i++) {
            letra= user_cadena.charAt(i);
            
            if (letra != ' ') {
                palabra = palabra + letra; 
                
            }
            else {
                if ( pal_mas_larga.length() < palabra.length() ) {
                    pal_mas_larga= palabra; 
                }
                
                palabra= ""; 
            }
        }
        return pal_mas_larga;
    }
    
    
    //Opcion 4 "Armar una nueva cadena compuesta únicamente por las palabras en las posiciones pares"
    public static String opcion4 (String user_cadena){
        
        
        String nuevaCadena = "";
        int posPalabra= 0;
        int largo= user_cadena.length();
        int inicio = 0;
        int fin = 0;

        do{    
            String palabra = "";
            
            fin = inicio;
            char nextChar = '-';
            
            while (nextChar != ' ') {
                if ((fin+1) < largo) {
                    
                    fin= fin+1;
                    nextChar= user_cadena.charAt(fin);
                }
                else {      
                    break; 
                }   
            } 
            
            palabra= user_cadena.substring(inicio, (fin+1)).toLowerCase();
            palabra.trim();
            
            
            inicio= fin+1;
            
            posPalabra= posPalabra + 1;
            
            if ( (posPalabra%2) == 0) {
                nuevaCadena= nuevaCadena.concat(" "+ palabra);
            }
            
        } while ((fin+1) < largo);
        
        return nuevaCadena;
    }
        
    
    //Programa principal
    public static void main(String[] args) {
        
        //cadena que ingresa el usuario
        String user_cadena;
        
        //opcion general para entrar al menú
        int opcion;
        
        //respuesta a seguir en el bucle de respuesta principal
        String respuesta;
        
        //opcion2 y opcion4
        int longitud = 0; 
        
        //Opcion 3
        String palabra_mas_grande;
        
        //opcion4
        String palabraspares;


        System.out.println("Ingrese una cadena de caracteres");
        user_cadena= TecladoIn.readLine();
        
        do {
        
        while (verificacion_de_caracteres(user_cadena) != true) {
            System.out.println("Ingrese una cadena de caracteres");
            user_cadena= TecladoIn.readLine();
            
        }
            
        System.out.println("");
        System.out.println("");
        System.out.println("ELEGIR OPCION");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("| (1) Leer una cadena compuesta solo por letras + espacio.                           |");
        System.out.println("|                                                                                    |");
        System.out.println("| (2) Contar la cantidad de palabras de longitud menor a una longitud dada           |");
        System.out.println("|                                                                                    |");
        System.out.println("| (3) Buscar la palabra más larga.                                                   |");
        System.out.println("|                                                                                    |");
        System.out.println("| (4) Mostrar palabras que sean numero par                                           |");
        System.out.println("|                                                                                    |");
        System.out.println("|            Escriba \'si\' para seleccionar otra opcion                               |");
        System.out.println("--------------------------------------------------------------------------------------");
        
        opcion= TecladoIn.readLineInt();

        switch (opcion) {
            case 1: 
                System.out.println("");
                System.out.println("Ha elegido la opcion 1");
                System.out.println("Ingrese una cadena de caracteres");
                user_cadena= TecladoIn.readLine();
                verificacion_de_caracteres(user_cadena);
            break;
                
            case 2:
                System.out.println("");
                System.out.println("Ha elegido la opcion 2");
                if (verificacion_de_caracteres(user_cadena) == true) {
                System.out.println("Ingrese cantidad de caracteres que debe poseer la palabra");
                longitud= TecladoIn.readLineInt();
                
                int mostrar_longitud = opcion2(user_cadena, longitud);
                
                System.out.println("La cantidad de palabras con menor longitud de "+ longitud + " son: "+ mostrar_longitud);
                
                }
            break;
                
            case 3:
                System.out.println("");
                System.out.println("Ha elegido la opcion 3");
                
                if (verificacion_de_caracteres(user_cadena) == true) {
                
                palabra_mas_grande= opcion3(user_cadena);
                System.out.println("La palabra mas larga es "+ palabra_mas_grande);
                
                }
            break;
                
            case 4:
                System.out.println("");
                System.out.println("Ha elegido la opcion 4");
                if (verificacion_de_caracteres(user_cadena) == true) {
                    
                    palabraspares= opcion4(user_cadena);
                    System.out.println("Las palabras pares son"+ palabraspares);
                
                }
            break;
                
            default:
                System.out.println("Error numero no valido - seleccione un numero del 1 al 4");
            break;
        }
        
           System.out.println("Para continuar escriba cualquier cosa o si desea detener el programa escriba \"salir\" ");
           respuesta= TecladoIn.readLine();
           
        } while (respuesta.compareTo("salir") != 0 );  
    }
}