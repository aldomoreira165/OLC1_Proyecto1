package models;

public class classSiguientes {
    private String valor;
    private int id;
    private String siguientes;

    public classSiguientes(String valor, int id) {
        this.valor = valor;
        this.id = id;
        this.siguientes = "";
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
