package models;

public class classCadena {

    private String cadena;
    private Boolean valido;

    public classCadena(String cadena) {
        this.cadena = cadena;
        this.valido = false;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

}
