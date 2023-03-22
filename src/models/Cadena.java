package models;

public class Cadena {

    private String cadena;
    private Boolean aceptada;

    public Cadena(String cadena) {
        this.cadena = cadena;
        this.aceptada = false;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
}
