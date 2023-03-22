package models;

public class NodoArbol {
    private int id;
    private String valor;
    private boolean anulable;
    private String tipo;
    private String primeros;
    private String ultimos;
    private int numNodo;
    private NodoArbol hijoIzquierdo;
    private NodoArbol hijoDerecho;

    public NodoArbol(String valor, String tipo, int numNodo) {
        this.valor = valor;
        this.tipo = tipo;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
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

    public String getTipo() {
        return tipo;
    }

    public NodoArbol getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoArbol hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoArbol getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoArbol hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
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


}


