package models;

import analizador.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManipuladorData {

    //Instancia de datos
    public static int conteoAnalisis = 0;
    public static int conteo_Expresiones = 0;
    public static ArrayList<ExpresionRegular> listaDeExpresiones = new ArrayList<>();
    public static ArrayList<Conjunto> listaDeConjuntos = new ArrayList<>();
    public static ArrayList<Aceptadas> listAceptadas = new ArrayList<>();

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
        for (int posER = 0; posER < listaDeExpresiones.size(); posER++) {
            ExpresionRegular actualER = listaDeExpresiones.get(posER);
            for (Cadena actualLexema : actualER.getCadenas()) {
                evaluarLexema(actualLexema.getCadena(), actualER.getTablaEstados(), area, actualER.getId(), posER);
            }
        }
    }

    private void evaluarLexema(String lexema, ArrayList<Estados> afd, JTextArea area, String idER, int posER) {
        Estados estadoActual = afd.get(0);
        StringBuilder concatenado = new StringBuilder();
        boolean aceptado = false;
        for (int i = 0; i < lexema.length(); i++) {
            String caracter = Character.toString(lexema.charAt(i));
            String estadoSiguiente = estadoActual.verificarPaso(caracter, estadoActual.getId(), concatenado.toString());
            if (!estadoSiguiente.equals("****Error****")) {
                int posicionEstadoSiguiente = obtenerPosicionEstadoAct(afd, estadoSiguiente);
                if (posicionEstadoSiguiente >= 0) {
                    estadoActual = afd.get(posicionEstadoSiguiente);
                    if (estadoSiguiente.equals(estadoActual.getId())) {
                        concatenado.append(caracter);
                    } else {
                        concatenado.setLength(0);
                    }
                    aceptado = estadoActual.isEstadoAceptacion();
                } else {
                    aceptado = false;
                    break;
                }
            } else {
                aceptado = false;
                break;
            }
        }
        if (aceptado) {
            String acept = "La cadena: \"" + lexema + "\" fue aceptada por la expresión regular: " + idER + "\n";

            // agregando las cadenas aceptadas al arreglo
            listAceptadas.add(new Aceptadas(lexema, idER, "Cadena Válida"));

            // imprimiendo la cadena aceptada en la consola
            area.setText(area.getText() + acept);
        }
    }

    private int obtenerPosicionEstadoAct(ArrayList<Estados> afd, String idEstado) {
        for (int i = 0; i < afd.size(); i++) {
            if (afd.get(i).getId().equals(idEstado)) {
                return i;
            }
        }
        return -1;
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
        listaDeConjuntos.clear();
    }

    public void limpiarListaExpresiones(){
        listaDeExpresiones.clear();
    }

    public void limpiarListaAceptadas(){
        listAceptadas.clear();
    }
}