package models;

public class Siguientes {
    private String valor;
    private int id;
    private String siguientes;

    public Siguientes(String valor, int id) {
        this.valor = valor;
        this.id = id;
        this.siguientes = "";
    }

    public String getValor() {
        return valor;
    }

    public int getId() {
        return id;
    }

    public String getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(String siguientes) {
        if (this.siguientes.equals("")) {
            this.siguientes = siguientes;
        } else {
            this.siguientes = siguientes + "," + this.siguientes;
        }
    }
}
