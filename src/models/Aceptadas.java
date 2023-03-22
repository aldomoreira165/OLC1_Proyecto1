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

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public String getAceptada() {
        return aceptada;
    }

}
