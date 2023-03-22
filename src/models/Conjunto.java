package models;

import java.util.ArrayList;

public class Conjunto {

    private String id;
    private ArrayList<String> elementos;

    public Conjunto(String id) {
        this.id = id;
        this.elementos = new ArrayList<>();
    }

    public boolean verificarExistenciaCaracter(String caracter) {
        return elementos.contains(caracter);
    }

    public String getId() {
        return id;
    }

    public ArrayList getElementos() {
        return elementos;
    }

    public String obtenerInicioString() {
        String[] elementosArray = elementos.toArray(new String[0]);
        return elementosArray[0];
    }

    public char obtenerInicio() {
        String primerElemento = elementos.get(0);
        return primerElemento.charAt(0);
    }
}