package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

public class arbol {
    private nodoArbol raiz;
    private int hojas = 1;
    private int conteoNodos = 0;
    private boolean insertBoolean = false;

    public arbol() {
        this.raiz = null;
    }

    public nodoArbol getRaiz() {
        return raiz;
    }

    public void inicializarArbol() {
        insert(".", "operacion");
        insertRight("#", "aceptacion", raiz);
    }

    public void insert(String valor, String tipo) {
        if (raiz != null) {
            insertNodo(valor, tipo, raiz);
        } else {
            this.raiz = new nodoArbol(valor, tipo, conteoNodos);
            conteoNodos++;
        }
    }

    public void idAceptacion() {
        this.raiz.getRight().setId(hojas);
    }

    private void preOrdenInsert(String valor, String tipo, nodoArbol nodo) {
        if (nodo.getLeft() != null) {
            preOrdenInsert(valor, tipo, nodo.getLeft());
        }

        if (nodo.getRight() != null) {
            preOrdenInsert(valor, tipo, nodo.getRight());
        }

        if (!insertBoolean) {
            switch (nodo.getTipo()) {
                //Omitir
                case "valor":
                    break;
                case "operacion":
                    if (nodo.getLeft() == null) {
                        insertLeft(valor, tipo, nodo);
                        insertBoolean = true;
                    } else if (nodo.getRight() == null) {
                        insertRight(valor, tipo, nodo);
                        insertBoolean = true;
                    }
                    break;
                case "cerradura":
                    if (nodo.getLeft() == null) {
                        insertLeft(valor, tipo, nodo);
                        insertBoolean = true;
                    }
                    break;
                default:
                    break;
            }

        }

    }

    private void insertNodo(String valor, String tipo, nodoArbol nodo) {
        //Insertar nodo inicio analisis
        if (nodo == this.raiz && this.raiz.getLeft() == null) {
            insertLeft(valor, tipo, nodo);
        } //Insertar nodo dependiendo el tipo
        else { //Tipo de nodo que esta actualmente
            insertBoolean = false;
            preOrdenInsert(valor, tipo, nodo);
        }
    }

    private void insertLeft(String valor, String tipo, nodoArbol nodo) {
        nodoArbol nodoInsert = new nodoArbol(valor, tipo, conteoNodos);
        if (tipo.equals("valor")) {
            nodoInsert.setId(hojas);
            hojas++;
        }
        nodo.setLeft(nodoInsert);
        conteoNodos++;
    }

    private void insertRight(String valor, String tipo, nodoArbol nodo) {
        nodoArbol nodoInsert = new nodoArbol(valor, tipo, conteoNodos);
        if (tipo.equals("valor")) {
            nodoInsert.setId(hojas);
            hojas++;
        }
        nodo.setRight(nodoInsert);
        conteoNodos++;
    }

    private boolean arbolVacio() {
        return this.raiz == null;
    }

    public void graficarArbol(String numDoc) throws IOException {
        if (!arbolVacio()) {
            //creacion de la carpeta que contiene los arboles
            String ruta = new File(".").getAbsolutePath();
            String ruta_absoluta = ruta;
            crear_carpeta("ARBOLES_202109754");
            ruta += File.separator + "ARBOLES_202109754" + File.separator + "Arbol" + numDoc + ".dot";
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            //Escribimos dentro del archivo .dot
            try (PrintWriter write = new PrintWriter(ruta, "UTF-8")) {
                write.println("digraph Arbol{");
                write.println("node [shape=record, height=.1];");
                write.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(null, "Error al crear el reporte de archivos." + e, "Error con los archivos.", JOptionPane.ERROR_MESSAGE);
            }

            //Llamar metodo para escribir el arbol
            crearArbol(this.raiz, ruta);

            //Terminamos de escribir el codigo
            try (FileWriter escribir = new FileWriter(ruta, true); PrintWriter write = new PrintWriter(escribir)) {
                write.println("label= \"Reporte de árbol\";");
                write.println("}");
                write.close();
            }

            //Generar la imagen con el comando cmd
            String rutaImagen = ruta_absoluta + File.separator + "ARBOLES_202109754" + File.separator + "Arbol" + numDoc + ".png";
            crearImagen(ruta, rutaImagen);
        }
    }

    private void crearImagen(String rutaDot, String rutaPng) {
        try {
            ProcessBuilder pbuild = new ProcessBuilder("dot", "-Tpng", "-o", rutaPng, rutaDot);
            pbuild.redirectErrorStream(true);
            pbuild.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el reporte del árbol." + e, "Error con el árbol.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearArbol(nodoArbol nodo, String pathDot) throws IOException {
        if (nodo != null) {
            crearArbol(nodo.getLeft(), pathDot);

            //Escribimos dentro del archivo .dot
            try (FileWriter escribir = new FileWriter(pathDot, true); PrintWriter write = new PrintWriter(escribir)) {
                if (nodo.getValor().length() == 1) {
                    write.println("\"node" + nodo.getNumNodo() + "\"[label = \"<f0>" + nodo.getPrimeros() + " |{ " + anulable(nodo) + " | \\" + nodo.getValor() + " | " + nodo.getId() + " } |<f2>" + nodo.getUltimos() + " \"];");
                } else {
                    write.println("\"node" + nodo.getNumNodo() + "\"[label = \"<f0>" + nodo.getPrimeros() + " |{ " + anulable(nodo) + " | " + nodo.getValor() + " | " + nodo.getId() + " } |<f2>" + nodo.getUltimos() + " \"];");
                }

                //Validar hijo izquierdo
                if (nodo.getLeft() != null) {
                    write.println("\"node" + nodo.getNumNodo() + "\":f0 -> \"node" + nodo.getLeft().getNumNodo() + "\";");
                }

                //Validad hijo derecho
                if (nodo.getRight() != null) {
                    write.println("\"node" + nodo.getNumNodo() + "\":f2 -> \"node" + nodo.getRight().getNumNodo() + "\";");
                }

                write.close();
            }
            crearArbol(nodo.getRight(), pathDot);
        }
    }

    //metodo que se encarga de crear carpetas
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

    private String anulable(nodoArbol nodo) {
        if (nodo.getAnulable()) {
            return "Anulable";
        } else {
            return "No Anulable";
        }
    }

    public void calculos() {
        setAnulables(this.raiz);
        setPrimeros(this.raiz);
        setUltimos(this.raiz);
    }

    private void setUltimos(nodoArbol nodo) {
        if (nodo.getLeft() != null) {
            setUltimos(nodo.getLeft());
        }

        if (nodo.getRight() != null) {
            setUltimos(nodo.getRight());
        }

        switch (nodo.getTipo()) {
            case "valor":
                nodo.setUltimos(Integer.toString(nodo.getId()));
                break;
            case "operacion":
                if (nodo.getValor().equals("|")) {
                    String ultimos = nodo.getLeft().getUltimos() + "," + nodo.getRight().getUltimos();
                    nodo.setUltimos(ultimos);
                } else if (nodo.getRight().getAnulable() == true) {
                    String ultimos = nodo.getLeft().getUltimos() + "," + nodo.getRight().getUltimos();
                    nodo.setUltimos(ultimos);
                } else {
                    nodo.setUltimos(nodo.getRight().getUltimos());
                }
                break;
            case "cerradura":
                nodo.setUltimos(nodo.getLeft().getUltimos());
                break;
            case "aceptacion":
                nodo.setUltimos(Integer.toString(nodo.getId()));
                break;
        }

    }

    private void setPrimeros(nodoArbol nodo) {
        if (nodo.getLeft() != null) {
            setPrimeros(nodo.getLeft());
        }

        if (nodo.getRight() != null) {
            setPrimeros(nodo.getRight());
        }

        switch (nodo.getTipo()) {
            case "valor":
                nodo.setPrimeros(Integer.toString(nodo.getId()));
                break;
            case "operacion":
                if (nodo.getValor().equals("|")) {
                    String primeros = nodo.getLeft().getPrimeros() + "," + nodo.getRight().getPrimeros();
                    nodo.setPrimeros(primeros);
                } else if (nodo.getLeft().getAnulable() == true) {
                    String primeros = nodo.getLeft().getPrimeros() + "," + nodo.getRight().getPrimeros();
                    nodo.setPrimeros(primeros);
                } else {
                    nodo.setPrimeros(nodo.getLeft().getPrimeros());
                }
                break;
            case "cerradura":
                nodo.setPrimeros(nodo.getLeft().getPrimeros());
                break;
            case "aceptacion":
                nodo.setPrimeros(Integer.toString(nodo.getId()));
                break;
        }

    }

    private void setAnulables(nodoArbol nodo) {
        if (nodo.getLeft() != null) {
            setAnulables(nodo.getLeft());
        }

        if (nodo.getRight() != null) {
            setAnulables(nodo.getRight());
        }

        switch (nodo.getTipo()) {
            case "valor":
                nodo.setAnulable(false);
                break;
            case "operacion":
                if (nodo.getValor().equals("|")) {
                    if (nodo.getLeft().getAnulable() == true || nodo.getRight().getAnulable() == true) {
                        nodo.setAnulable(true);
                    } else {
                        nodo.setAnulable(false);
                    }
                } else if (nodo.getLeft().getAnulable() == true && nodo.getRight().getAnulable() == true) {
                    nodo.setAnulable(true);
                } else {
                    nodo.setAnulable(false);
                }
                break;
            case "cerradura":
                if (nodo.getValor().equals("+")) {
                    nodo.setAnulable(false);
                } else {
                    nodo.setAnulable(true);
                }
                break;
            default:
                break;
        }

    }

    public ArrayList<classSiguientes> crearTablaSiguientes() {
        ArrayList<classSiguientes> tablaSiguientes = new ArrayList<>();

        ingresarHojas(tablaSiguientes, this.raiz);
        insertarSiguientes(tablaSiguientes, this.raiz);

        return tablaSiguientes;
    }

    private void insertarSiguientes(ArrayList<classSiguientes> tablaSiguientes, nodoArbol nodo) {

        if (nodo.getTipo().equals("operacion") || nodo.getTipo().equals("cerradura")) {
            switch (nodo.getValor()) {
                case "*":
                    insertNext(tablaSiguientes, nodo.getPrimeros(), nodo.getUltimos());
                    break;
                case ".":
                    insertNext(tablaSiguientes, nodo.getRight().getPrimeros(), nodo.getLeft().getUltimos());
                    break;
                case "+":
                    insertNext(tablaSiguientes, nodo.getPrimeros(), nodo.getUltimos());
                    break;
                default:
                    //Ignorar
                    break;
            }
        }

        if (nodo.getLeft() != null) {
            insertarSiguientes(tablaSiguientes, nodo.getLeft());
        }

        if (nodo.getRight() != null) {
            insertarSiguientes(tablaSiguientes, nodo.getRight());
        }

    }

    private void insertNext(ArrayList<classSiguientes> tablaSiguientes, String siguientes, String valores) {
        String[] numerosValores = valores.split(",");
        for (String numero : numerosValores) {
            tablaSiguientes.get(posValor(tablaSiguientes, numero)).setSiguientes(siguientes);
        }
    }

    private int posValor(ArrayList<classSiguientes> tablaSiguientes, String numero) {
        int pos = 0;
        Iterator<classSiguientes> iteradorSiguientes = tablaSiguientes.iterator();
        while (iteradorSiguientes.hasNext()) {
            classSiguientes actualSiguiente = iteradorSiguientes.next();
            if (actualSiguiente.getId() == Integer.parseInt(numero)) {
                break;
            }
            pos++;
        }
        return pos;
    }

    private void ingresarHojas(ArrayList<classSiguientes> tablaSiguientes, nodoArbol nodo) {

        if (nodo.getTipo().equals("valor") || nodo.getTipo().equals("aceptacion")) {
            tablaSiguientes.add(new classSiguientes(nodo.getValor(), nodo.getId()));
        }

        if (nodo.getLeft() != null) {
            ingresarHojas(tablaSiguientes, nodo.getLeft());
        }

        if (nodo.getRight() != null) {
            ingresarHojas(tablaSiguientes, nodo.getRight());
        }
    }
}
