package TP2Distribuidos;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Modificar estos valores para cambiar la consulta        
        String[] horoscopo = {"KK", "TA", "GE", "CC", "LE", "VG", "LB", "ES", "SA", "CP", "AC", "PI", "AA", "ABC"};
        String[] fecha = {"05-05-2020", "29-02-2020", "AA-05-2020", "00-05-2020", "01-33-2020", "-33-2020", "01--2020", "01-05-", "29-02-2019", "32-01-2019", "32-04-2019", "32-AA-2019", "05-05-AAAA", "030-05-2016"};
        for (int i = 0; i < fecha.length; i++) {
            new Thread(new Cliente("Cliente " + i, horoscopo[i], fecha[i])).start();
        }

    }
}
