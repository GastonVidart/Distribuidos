package estructuras;

public class ListString {

    private NodoString header;
    private int length;

    public ListString() {
        this.header = null;
        this.length = 0;
    }

    public boolean insertar(String a, int pos) {
        //Inserta el elemento nuevo en la posicion pos
        //detecta y reporta error posicion invalida
        boolean exito = true;
        if (pos < 1 || pos > this.length + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.header = new NodoString(a, this.header);
                this.length++;

            } else {

                NodoString aux = this.header;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                //crea el nodo y lo enlaza
                NodoString nuevo = new NodoString(a, aux.getEnlace());
                aux.setEnlace(nuevo);
                this.length++;

            }
        }
//Nunca hay error de lista llena,entonces devuelve true
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito;
        int i = 1;
        NodoString aux = this.header;
        if (pos < 1 || pos > this.length + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.header=this.header.getEnlace();
                this.length--;
                exito=true;
                
            } else {
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                this.length--;
                aux.setEnlace(aux.getEnlace().getEnlace());
                exito = true;
            }
        }
        return exito;

    }

    public int localizar(String elem) {
        System.out.println(this.toString());
        int back = -1, i = 1;
        boolean exit = false;
        if (this.isEmpty()) {
            back = -1;
        } else {
            NodoString aux = this.header;
            while (!exit) {
                if (aux.getElem().equals(elem)) {
                    back = i;
                    exit = true;
                } else {
                    aux = aux.getEnlace();
                    i++;
                    if(i>this.length){
                    exit=true;}
                }

            }
        }
        return back;
    }

    public String recuperar(int pos) {
        int back = -1, i = 1;
        String resul="";
        boolean exito = false;
        if (pos < 1 || pos > this.length + 1) {
            back = -1;
        } else {
            NodoString aux = this.header;
            while (aux != null && !exito) {
                if (i == pos) {
                    resul = aux.getElem();
                    exito = true;
                }
                i++;
                aux = aux.getEnlace();
            }
        }
        return resul;

    }

    public boolean isEmpty() {
        return this.header == null;
    }

    public void vaciar() {
        this.header = null;

    }

    public ListString clonar() {
        //Pila a devolver es nueVa
        ListString nueVa = new ListString();
        nueVa.length++;
        if (header != null) {
            //Marca la posicion a copiar 
            NodoString aux = this.header;
            nueVa.header = new NodoString(aux.getElem());
            //el aux 2 marca el enlace a conectar
            NodoString aux2 = nueVa.header;
            aux = aux.getEnlace();
            while (aux != null) {
                nueVa.length++;
                //Elemento a enlazar es nuevo
                NodoString nuevo = new NodoString(aux.getElem());
                aux2.setEnlace(nuevo);
                aux2 = nuevo;
                //Paso a la pos siguiente
                aux = aux.getEnlace();

            }

        }
        return nueVa;
    }

    public String toString() {
        String cadena;
        int i = 1;
        if (this.header == null) {
            cadena = "VACIA";
        } else {
            NodoString aux = this.header;
            cadena = "[";
            while (i <= this.length) {
                cadena = cadena + aux.getElem();
                i++;
                aux = aux.getEnlace();
                if (aux != null) {
                    cadena = cadena + ",";
                }

            }
            cadena = cadena + "]";
        }
        return cadena;
    }

    public int getLength() {
        return this.length;
    }

}
