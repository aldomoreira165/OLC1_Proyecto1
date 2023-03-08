package models;

import analizador.*;

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
                System.out.println("Análisis Finalizado");
            }else{
                analizador.scanner.erroresLexicos.forEach(error -> {
                    System.out.println(error.getTipo() + "," + error.getDescripcion() + "," + error.getLinea() + "," + error.getColumna());
                });
                analizador.parser.erroresSintacticos.forEach(error -> {
                    System.out.println(error.getTipo() + "," + error.getDescripcion() + "," + error.getLinea() + "," + error.getColumna());
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
