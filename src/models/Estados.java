package models;

import java.util.ArrayList;
import java.util.Optional;

public class Estados {

    private String id;
    private ArrayList<EstadosNext> transiciones;
    private String numContenidos;
    private boolean estadoAceptacion;

    public Estados(String id, String numContenidos, boolean estadoAceptacion) {
        this.id = id;
        this.numContenidos = numContenidos;
        this.transiciones = new ArrayList<>();
        this.estadoAceptacion = estadoAceptacion;
    }

    public String verificarPaso(String caracter, String estadoActual, String cadena) {
        for (EstadosNext actualNext : this.transiciones) {
            if (esConjunto(actualNext)) {
                if (verificarExistenciaCaracterEnConjunto(actualNext, caracter)) {
                    return actualNext.getEstadoNext();
                }
            } else if (esCaracter(actualNext, caracter)) {
                return actualNext.getEstadoNext();
            } else if (esCadena(actualNext, cadena)) {
                return actualNext.getEstadoNext();
            }
        }
        return "****Error****";
    }

    private boolean esConjunto(EstadosNext actualNext) {
        for (Conjunto conjunto : ManipuladorData.listaDeConjuntos) {
            if (actualNext.getValor().equals(conjunto.getId())) {
                return true;
            }
        }
        return false;
    }

    private boolean verificarExistenciaCaracterEnConjunto(EstadosNext actualNext, String caracter) {
        for (Conjunto conjunto : ManipuladorData.listaDeConjuntos) {
            if (actualNext.getValor().equals(conjunto.getId())) {
                return conjunto.verificarExistenciaCaracter(caracter);
            }
        }
        return false;
    }

    private boolean esCaracter(EstadosNext actualNext, String caracter) {
        return actualNext.getValor().equals(caracter);
    }

    private boolean esCadena(EstadosNext actualNext, String cadena) {
        return actualNext.getValor().length() > 1 && cadena.length() <= actualNext.getValor().length() && cadena.equals(actualNext.getValor());
    }

    public boolean isEstadoAceptacion() {
        return estadoAceptacion;
    }

    public String getNumContenidos() {
        return numContenidos;
    }

    public void agregarTransicion(String estadoNext, String valor, int idValor) {
        this.transiciones.add(new EstadosNext(estadoNext, valor, idValor));
    }

    public String getId() {
        return id;
    }

    public ArrayList<EstadosNext> getTransiciones() {
        return transiciones;
    }

    public boolean verificarExistenciaTransicion(String estado, int valor) {
        return this.transiciones.stream().anyMatch(t -> t.getEstadoNext().equals(estado) && t.getIdVal() == valor);
    }

    public String ObtenerTransicionDoc(int val) {
        Optional<EstadosNext> transicion = this.transiciones.stream().filter(t -> t.getIdVal() == val).findFirst();
        return transicion.map(t -> "<td>" + t.getEstadoNext() + "</td>").orElse("<td></td>");
    }

}
