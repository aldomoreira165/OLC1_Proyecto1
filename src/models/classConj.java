package models;

import java.util.ArrayList;
import java.util.Iterator;

public class classConj {

    private String id;
    private ArrayList<String> conjunto;

    public classConj(String id) {
        this.id = id;
        this.conjunto = new ArrayList<>();
    }

    public boolean existeCaracter(String caracter) {
        Iterator<String> caracteres = conjunto.iterator();
        while (caracteres.hasNext()) {
            String caracterEvaluar = caracteres.next();

            if (caracter.equals(caracterEvaluar)) {
                return true;
            }

        }

        return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList getConjunto() {
        return conjunto;
    }

    public void setConjunto(ArrayList<String> conjunto) {
        this.conjunto = conjunto;
    }

    public void addItem(String item) {
        this.conjunto.add(item);
    }

    public String getStringStart() {
        return this.conjunto.get(0);
    }

    public char getStart() {
        return this.conjunto.get(0).charAt(0);
    }
}