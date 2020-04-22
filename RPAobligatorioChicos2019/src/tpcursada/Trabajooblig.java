package tpcursada;

import java.util.Scanner;

public class Trabajooblig {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int i=0;
                
        System.out.println("Selecione una de las siguientes opciones del menu con su número correspondiente: \n1-Leer cadena\n2-Contar palabras menores a dicha longitud\n3-Buscar la palabra mas larga\n4-Extraer palabras en posiciones pares\n5-Salir");
        int opcion=in.nextInt();
        in.nextLine();
        
        while(opcion!=5){
        System.out.println("Ingrese sentencia: ");
        String input=in.nextLine();
        String [] palabra=input.split(" "); //Split separa el String en un array segun el separador indicado y despues se llama la posicion que quiera

        switch (opcion) {
            case 1:
                for(i=0;i<palabra.length;i++){
                  System.out.println("(palabra) "+palabra[i]+" [espacio/salto de linea] ");
                }
                break;
            case 2:
                System.out.println("Ingrese longitud mínima: ");
                int longitud=in.nextInt();
                int palabras=0;
                for(i=0;i<palabra.length;i++){
                    if(palabra[i].length()<longitud){
                        palabras++;
                    }
                } System.out.println("Las palabras de menos de "+longitud+" caracteres son: "+palabras);
                break;
            case 3:
                String masLarga="";
                for(i=0;i<palabra.length;i++){
                    if(palabra[i].length()>=masLarga.length()){
                        masLarga=palabra[i];
                    }
                }
                System.out.println("La palabra mas larga es: "+masLarga);
                break;
            case 4:
                String soloPares="";
                for(i=1;i<palabra.length;i=i+2){
                    soloPares=soloPares+palabra[i];
                }
                System.out.print(soloPares);
                break;
            case 5:
                System.out.println("Usted a finalizado el programa");
                break;
            default:
                System.out.println("Opcion no válida");
                break;
        }
        System.out.println("Selecione una de las siguientes opciones del menu con su número correspondiente: \n1-Leer cadena\n2-Contar palabras menores a dicha longitud\n3-Buscar la palabra mas larga\n4-Extraer palabras en posiciones pares\n5-Salir");
        opcion=in.nextInt();
        in.nextLine();
    } System.out.println("Usted a finalizado el programa");
    }
}
