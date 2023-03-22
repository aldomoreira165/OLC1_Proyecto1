package models;

public class EstadosNext {
    private String estadoNext;
    private String valor;
    private int idVal;

    public EstadosNext(String estadoNext, String valor, int idVal) {
        this.estadoNext = estadoNext;
        this.valor = valor;
        this.idVal = idVal;
    }

    public int getIdVal() {
        return idVal;
    }

    public String getEstadoNext() {
        return estadoNext;
    }

    public String getValor() {
        return valor;
    }
}
