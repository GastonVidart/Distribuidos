import java.rmi.*;

public class Cliente {
    public Cliente() 
    {
        try {		
            Calculadora calc = (Calculadora)Naming.lookup ("//localhost:5000/CalculadoraImp");
            System.out.print ("2 + 3 = "); System.out.println (calc.suma(2,3));
	    System.out.print ("7 - 3 = "); System.out.println (calc.resta(7,3));
	    System.out.print ("9 / 3 = "); System.out.println (calc.div(9,3));
	    System.out.print ("2 * 3 = "); System.out.println (calc.mul(2,3));    }
        catch (Exception e)  {
            e.printStackTrace();   }
    }
    public static void main(String[] args) {
     new Cliente();
    }
    
}



