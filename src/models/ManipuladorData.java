package models;

import analizador.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ManipuladorData {

    //Instancia de datos
    public static int conteoAnalisis = 0;
    public static int conteo_Expresiones = 0;
    public static String name_Img = "";
    public static ArrayList<classER> listER = new ArrayList<>();
    public static ArrayList<classConj> listConj = new ArrayList<>();
    public static arbol tree = new arbol();

    public void interpretar(String entrada){
        try{
            scanner scanner = new scanner(new java.io.StringReader(entrada));
            parser parser = new parser(scanner);
            parser.parse();

            //verificando que el archivo de entrada no contenga errores para generar autómata
            if (analizador.scanner.erroresLexicos.isEmpty() && analizador.parser.erroresSintacticos.isEmpty()){
                JOptionPane.showMessageDialog(null, "El archivo se ha compilado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "El archivo contiene errores", "Error", JOptionPane.ERROR_MESSAGE);
                analizador.scanner.erroresLexicos.forEach(error -> {
                    System.out.println(error.getTipo() + "," + error.getDescripcion() + "," + error.getLinea() + "," + error.getColumna());
                });
                analizador.parser.erroresSintacticos.forEach(error -> {
                    System.out.println(error.getTipo() + "," + error.getDescripcion() + "," + error.getLinea() + "," + error.getColumna());
                });

                //generando archivo html de errores
                GeneradorReporteErrores generador = new GeneradorReporteErrores();
                generador.generarHTML();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void analizar(){
        if (!parser.automatas_generados){
            JOptionPane.showMessageDialog(null, "Aún no hay autómatas generados para este archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Ya existen autómatas", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listConj.forEach(conj -> {
                System.out.println(conj.getId()+ "  " + conj.getConjunto());
            });
        }
    }

    public void limpiarListaConjuntos(){
        listConj.clear();
    }

    public void limpiarListaExpresiones(){
        listER.clear();
    }
}