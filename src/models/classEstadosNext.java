package models;

public class classEstadosNext {
    private String estadoNext;
    private String valor;
    private int idVal;

    public classEstadosNext(String estadoNext, String valor, int idVal) {
        this.estadoNext = estadoNext;
        this.valor = valor;
        this.idVal = idVal;
    }

    public int getIdVal() {
        return idVal;
    }

    public void setIdVal(int idVal) {
        this.idVal = idVal;
    }

    public String getEstadoNext() {
        return estadoNext;
    }

    public void setEstadoNext(String estadoNext) {
        this.estadoNext = estadoNext;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
