package TP2Distribuidos;

/**
 *
 * @author guido
 */
public class InstanciaCliente implements Runnable {
    //El cliente se conecta al servidor y realiza una consulta por cada horoscopo y fecha en el arreglo

    
    private String name, ipAdress;
    private String[] horoscopo, fecha;
    private ServerCentral server;

    public InstanciaCliente(String name,ServerCentral server, String[] horoscopo, String[] fecha) {  
        this.name = name;
        this.horoscopo = horoscopo;
        this.fecha = fecha;
        this.server=server;
    }

    @Override
    public void run() {
        

        String pronostico;
        for (int i = 0; i < horoscopo.length; i++) {
            pronostico=server.getPronostico(horoscopo[i],fecha[i]);
            if (!esError(pronostico)) {
                pronostico = "Pronostico Horóscopo: " + pronostico.substring(pronostico.indexOf('(') + 1, pronostico.indexOf(';'))
                        + "\nPronostico Clima: " + pronostico.substring(pronostico.indexOf(';') + 1, pronostico.indexOf(')'));
                System.out.println(name + pronostico);
            }
        }

        if (conector.desconectar()) {
            System.out.println(name + " Estado: Desconectado");
        } else {
            System.out.println(name + " ERROR: PROBLEMA EN LA DESCONEXIÓN CON EL SERVIDOR");
        }
    }

    private boolean esError(String error) {
        //Codificación de los errores
        if (error.startsWith("error")) {
            String str = error.substring(error.indexOf('(') + 1, error.indexOf(')'));
            switch (str) {
                case "ESH":
                    System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DE HORÓSCOPOS");
                    break;
                case "ESC":
                    System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR DEL CLIMA");
                    break;
                case "FD":
                    System.out.println("ERROR: DIA NO VALIDO");
                    break;
                case "PC":
                    System.out.println("ERROR: FECHA NO VALIDA");
                    break;
                case "FM":
                    System.out.println("ERROR: MES NO VALIDO");
                    break;
                case "SC":
                    System.out.println("ERROR: ERROR POR PARTE DEL SERVIDOR CENTRAL");
                    break;
                case "SP":
                    System.out.println("ERROR: ERROR DE PROTOCOLO POR PARTE DEL SERVIDOR CENTRAL");
                    break;
                case "CP":
                    System.out.println("ERROR: ERROR DE PROTOCLO POR PARTE DEL CLIENTE");
                    break;
                default:
                    System.out.println("ERROR: " + str);
                    break;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean ipEsValido(String ipadress) {
        //Devuelve [true] si el parametro de entrada es un ip válido
        if (ipadress.equals("localhost")) {
            return true;
        } else if (ipadress == null) {
            return false;
        }
        String n;
        for (int i = 0; i < 3; i++) {
            if (ipadress.indexOf('.') == -1) {
                return false;
            }
            n = ipadress.substring(0, ipadress.indexOf('.'));
            ipadress = ipadress.substring(ipadress.indexOf('.') + 1);
            if (!numIpEsValido(n)) {
                return false;
            }
        }
        if (!numIpEsValido(ipadress)) {
            return false;
        }
        return true;
    }

    private boolean numIpEsValido(String n) {
        try {
            int x = Integer.decode(n);
            if (x >= 0 && x < 256) {
                return true;
            }
        } catch (NumberFormatException e) {

        }
        return false;
    }
}
