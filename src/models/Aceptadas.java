package models;

public class Aceptadas {
    private String valor;
    private String expresionRegular;
    private String aceptada;

    private static int contadorArchivos;

    public Aceptadas(String valor, String expresionRegular, String aceptada) {
        this.valor = valor;
        this.expresionRegular = expresionRegular;
        this.aceptada = aceptada;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public String getAceptada() {
        return aceptada;
    }

    public void setAceptada(String aceptada) {
        this.aceptada = aceptada;
    }

    public int getContadorArchivos() {
        return contadorArchivos;
    }

    public void setContadorArchivos(int contadorArchivos) {
        this.contadorArchivos = contadorArchivos;
    }
}
