package estructuras;


public class NodoString {
    //Atributos
    private NodoString enlace;
    private String elemento;
    
    //Constructor
    public NodoString(String elemento,NodoString enlace){
        this.enlace=enlace;
        this.elemento=elemento;
        
    
    }
    public NodoString(String elemento){
    this.elemento=elemento;
    this.enlace=null;}
    
    //Observadora
    public String getElem(){
    return elemento;
    }
    public NodoString getEnlace(){
    return enlace;}
    //Modificadoras
    public void setElem(String elemento){
        this.elemento=elemento;
    }
    public void setEnlace(NodoString enlace){
    this.enlace=enlace;
    }
    
    
}
