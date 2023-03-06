package models;

public class nodoArbol {

    private boolean anulable;
    private String tipo;
    private int id;
    private String primeros;
    private String ultimos;
    private String valor;
    private int numNodo;
    private nodoArbol left;
    private nodoArbol right;

    public nodoArbol(String valor, String tipo, int numNodo) {
        this.valor = valor;
        this.tipo = tipo;
        this.left = null;
        this.right = null;
        this.numNodo = numNodo;
    }

    public boolean getAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    public int getNumNodo() {
        return numNodo;
    }

    public void setNumNodo(int numNodo) {
        this.numNodo = numNodo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public nodoArbol getLeft() {
        return left;
    }

    public void setLeft(nodoArbol left) {
        this.left = left;
    }

    public nodoArbol getRight() {
        return right;
    }

    public void setRight(nodoArbol right) {
        this.right = right;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimeros() {
        return primeros;
    }

    public void setPrimeros(String primeros) {
        this.primeros = primeros;
    }

    public String getUltimos() {
        return ultimos;
    }

    public void setUltimos(String ultimos) {
        this.ultimos = ultimos;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}


