package models;

import java.util.ArrayList;
import java.util.Iterator;

public class classEstados {

    private String idEstado;
    private ArrayList<classEstadosNext> transiciones;
    private String numContenidos;
    private boolean aceptacion;

    public classEstados(String idEstado, String numContenidos, boolean aceptacion) {
        this.idEstado = idEstado;
        this.numContenidos = numContenidos;
        this.transiciones = new ArrayList<>();
        this.aceptacion = aceptacion;
    }

    public String pasoPermitido(String caracter, String estadoActual, String cadena) {
        Iterator<classEstadosNext> iteradorNext = this.transiciones.iterator();
        while (iteradorNext.hasNext()) {
            classEstadosNext actualNext = iteradorNext.next();

            boolean esConjunto = false;
            int posConj = 0;

            for (int i = 0; i < ManipuladorData.listConj.size(); i++) {
                if (actualNext.getValor().equals(ManipuladorData.listConj.get(i).getId())) {
                    esConjunto = true;
                    posConj = i;
                    break;
                }
            }
            if (esConjunto) {
                if (ManipuladorData.listConj.get(posConj).existeCaracter(caracter)) {
                    System.out.println("Evaluado como conjunto");
                    return actualNext.getEstadoNext();
                }
            } else if (actualNext.getValor().equals(caracter)) {
                System.out.println("Evaluado como caracter");
                return actualNext.getEstadoNext();
            } else if (actualNext.getValor().length() > 1 && cadena.length() <= actualNext.getValor().length()) {
                //Evaluar
                System.out.println("Evaluado como cadena");
                System.out.println("Cadena: " + cadena);
                System.out.println("Cadena Evaluar: " + actualNext.getValor());

                if (cadena.equals(actualNext.getValor())) {
                    return actualNext.getEstadoNext();
                } else {
                    if(!iteradorNext.hasNext()){
                        return estadoActual;
                    }
                }

            }

        }

        System.out.println("Error");
        return "****Error****";
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }

    public String getNumContenidos() {
        return numContenidos;
    }

    public void setNumContenidos(String numContenidos) {
        this.numContenidos = numContenidos;
    }

    public void addTransicion(String estadoNext, String valor, int idValor) {
        this.transiciones.add(new classEstadosNext(estadoNext, valor, idValor));
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public ArrayList<classEstadosNext> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<classEstadosNext> transiciones) {
        this.transiciones = transiciones;
    }

    public boolean existeTransicion(String estado, int valor) {
        Iterator<classEstadosNext> iteradorNext = this.transiciones.iterator();
        while (iteradorNext.hasNext()) {
            classEstadosNext nextActual = iteradorNext.next();
            if (nextActual.getEstadoNext().equals(estado) && nextActual.getIdVal() == valor) {
                return true;
            }
        }
        return false;
    }

    public void printTransiciones() {
        for (int i = 0; i < this.transiciones.size(); i++) {
            classEstadosNext estadoNextActual = this.transiciones.get(i);
            System.out.println("Transicion: " + estadoNextActual.getValor() + "->" + estadoNextActual.getEstadoNext());
        }
    }

    public String getTransicionHTML(int val) {
        String salidaHTML = "";
        for (int i = 0; i < this.transiciones.size(); i++) {
            classEstadosNext estadoNextActual = this.transiciones.get(i);
            if (val == estadoNextActual.getIdVal()) {
                salidaHTML = "<td>" + estadoNextActual.getEstadoNext() + "</td>";
                break;
            } else {
                salidaHTML = "<td></td>";
            }
        }
        return salidaHTML;
    }

}
