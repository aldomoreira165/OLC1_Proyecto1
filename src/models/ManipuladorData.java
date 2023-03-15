package models;

import analizador.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManipuladorData {

    //Instancia de datos
    public static int conteoAnalisis = 0;
    public static int conteo_Expresiones = 0;
    public static String name_Img = "";
    public static ArrayList<classER> listER = new ArrayList<>();
    public static ArrayList<classConj> listConj = new ArrayList<>();
    public static ArrayList<String> listLex = new ArrayList<>();
    public static ArrayList<Aceptadas> listAceptadas = new ArrayList<>();
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

    public void analizar(JTextArea area){
        if (!parser.automatas_generados){
            JOptionPane.showMessageDialog(null, "Aún no hay autómatas generados para este archivo", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Ya existen autómatas", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            validarLexemas(area);
            generarJSON();
        }
    }

    public void validarLexemas(JTextArea area) {

        int posER = 0;
        Iterator<classER> iteradorER = listER.iterator();
        while (iteradorER.hasNext()) {
            classER actualER = iteradorER.next();
            Iterator<classCadena> iteradorLexemas = actualER.getCadenas().iterator();
            while (iteradorLexemas.hasNext()) {
                classCadena actualLexema = iteradorLexemas.next();
                evaluarLexema(actualLexema.getCadena(), actualER.getTablaEstados(), area,actualER.getId(), posER);
            }
            posER++;
        }
    }

    private void evaluarLexema(String lexema, ArrayList<classEstados> afd, JTextArea area,String idER, int posER) {
        classEstados estadoActual = afd.get(0);
        String concatenado = "";
        Boolean aceptado = false;
        for (int i = 0; i < lexema.length(); i++) {
            String caracter = Character.toString(lexema.charAt(i));
            String estadoSiguiente = estadoActual.pasoPermitido(caracter, estadoActual.getIdEstado(), concatenado);
            if (!estadoSiguiente.equals("****Error****")) {
                estadoActual = afd.get(listER.get(posER).posEstadoActual(estadoSiguiente));
                if (estadoSiguiente.equals(estadoActual.getIdEstado())) {
                    concatenado += caracter;
                } else {
                    concatenado = "";
                }
                if (estadoActual.isAceptacion()) {
                    aceptado = true;

                } else {
                    aceptado = false;
                }
            } else {
                aceptado = false;
                break;
            }
        }
        if (aceptado){
            String acept = "La cadena: \"" + lexema + "\" " + " fue aceptada por la expresión regular: " + idER + "\n";

            //agregando las cadenas aceptadas al arreglo
            listAceptadas.add(new Aceptadas(lexema, idER, "Cadena Válida"));

            //imprimiendo la cadena aceptada en la consola
            area.setText(area.getText() + acept);
        }

    }

    private void generarJSON(){

        //generando carpeta
        String ruta = new File(".").getAbsolutePath();
        crear_carpeta("SALIDAS_202109754");

        List<JSONObject> cadenas = new ArrayList<>();

        listAceptadas.forEach(acept -> {
            JSONObject cadena = new JSONObject();
            cadena.put("Valor", acept.getValor());
            cadena.put("ExpresionRegular", acept.getExpresionRegular());
            cadena.put("Resultado", acept.getAceptada());
            cadenas.add(cadena);
        });

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(cadenas);

        String filename = ruta +  File.separator + "SALIDAS_202109754" + File.separator + "aceptados.json";
        try (FileWriter file = new FileWriter(filename)) {
            for (JSONObject cadena : cadenas) {
                file.write(cadena.toJSONString());
                file.write(System.lineSeparator()); // Agrega un salto de línea al final de cada objeto JSON
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crear_carpeta(String nombre){
        String path = new File(".").getAbsolutePath();
        File carpeta = new File(path, nombre);
        if (!carpeta.exists()) { // Si la carpeta no existe
            if (carpeta.mkdir()) { // Intenta crear la carpeta
                System.out.println("Carpeta creada exitosamente.");
            } else {
                System.out.println("No se pudo crear la carpeta.");
            }
        } else {
            System.out.println("La carpeta ya existe.");
        }
    }

    public void limpiarListaConjuntos(){
        listConj.clear();
    }

    public void limpiarListaExpresiones(){
        listER.clear();
    }

    public void limpiarListaAceptadas(){
        listAceptadas.clear();
    }
}