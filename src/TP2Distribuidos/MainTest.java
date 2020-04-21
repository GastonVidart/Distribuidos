package TP2Distribuidos;

import java.io.IOException;
import java.rmi.Naming;
import java.util.Random;

public class MainTest {

    private static int CANT = 10;

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] diaMes = obtenerDiaMes();
        String[] horoscopos = {"CC", "AR", "SA", "pe", "LP", ",", "CC", "LE", "PI", "ES"};
        String[] fechas = new String[10];
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String fecha = diaMes[0] + "-" + diaMes[1] + "-" + random.nextInt(10000);
            fechas[i] = fecha;
        }
        try {
            ServerCentral svc = (ServerCentral) Naming.lookup("//localhost:54321/ServerCentralImp");
            for (int i = 0; i < CANT; i++) {
                new Thread(new InstanciaCliente("Cliente " + i,svc, horoscopos, fechas)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] obtenerDiaMes() {
        String[] diaMes = new String[2];
        int auxDia, auxMes;
        Random random = new Random();
        auxDia = random.nextInt(40);
        auxMes = random.nextInt(15);

        if (auxDia >= 0 && auxDia <= 9) {
            diaMes[0] = "0" + 1;
        } else {
            diaMes[0] = Integer.toString(auxDia);
        }

        if (auxMes >= 0 && auxMes <= 9) {
            diaMes[1] = "0" + auxMes;
        } else {
            diaMes[1] = Integer.toString(auxMes);
        }

        return diaMes;
    }
}
