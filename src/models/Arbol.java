package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Arbol {
    private NodoArbol raiz;
    private int hojas = 1;
    private int contador = 0;
    private boolean insertarEstado = false;

    public Arbol() {
        this.raiz = null;
    }

    public NodoArbol getRaiz() {
        return raiz;
    }

    //metodo para inicializar el Arbol
    public void crearNuevoArbol() {
        insertar(".", "operacion");
        insertarHijoDerecha("#", "aceptacion", raiz);
    }

    public void insertar(String valor, String tipo) {
        if (raiz == null) {
            this.raiz = new NodoArbol(valor, tipo, contador);
        } else {
            insertarNodo(valor, tipo, raiz);
        }
        contador++;
    }

    public void idAceptacion() {
        this.raiz.getHijoDerecho().setId(hojas);
    }

    private void insertarEnPreOrden(String valor, String tipo, NodoArbol nodo) {
        Stack<NodoArbol> pila = new Stack<>();
        pila.push(nodo);

        while (!pila.isEmpty()) {
            NodoArbol actual = pila.pop();

            if (actual.getHijoIzquierdo() != null) {
                pila.push(actual.getHijoIzquierdo());
            }

            if (actual.getHijoDerecho() != null) {
                pila.push(actual.getHijoDerecho());
            }

            if (!insertarEstado) {
                switch (actual.getTipo()) {
                    case "valor":
                        break;
                    case "operacion":
                        if (actual.getHijoIzquierdo() == null) {
                            insertarHijoIzquierda(valor, tipo, actual);
                            insertarEstado = true;
                        } else if (actual.getHijoDerecho() == null) {
                            insertarHijoDerecha(valor, tipo, actual);
                            insertarEstado = true;
                        }
                        break;
                    case "cerradura":
                        if (actual.getHijoIzquierdo() == null) {
                            insertarHijoIzquierda(valor, tipo, actual);
                            insertarEstado = true;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void insertarNodo(String valor, String tipo, NodoArbol nodo) {
        if (nodo == this.raiz && this.raiz.getHijoIzquierdo() == null) {
            insertarHijoIzquierda(valor, tipo, nodo);
        } //Verificando el tipo de nodo e insertarlo
        else { //Tipo de nodo que esta actualmente
            insertarEstado = false;
            insertarEnPreOrden(valor, tipo, nodo);
        }
    }

    private void insertarHijoIzquierda(String valor, String tipo, NodoArbol nodo) {
        NodoArbol nodoInsert = new NodoArbol(valor, tipo, contador++);
        if (tipo.equals("valor")) {
            nodoInsert.setId(hojas++);
        }
        nodo.setHijoIzquierdo(nodoInsert);
    }

    private void insertarHijoDerecha(String valor, String tipo, NodoArbol nodo) {
        NodoArbol nodoInsert = new NodoArbol(valor, tipo, contador++);
        if (tipo.equals("valor")) {
            nodoInsert.setId(hojas++);
        }
        nodo.setHijoDerecho(nodoInsert);
    }

    //método para verificar si el árbol está vacío


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

            //Llamar metodo para escribir el Arbol
            crearGrafoArbol(this.raiz, ruta);

            //Terminamos de escribir el codigo
            try (FileWriter escribir = new FileWriter(ruta, true); PrintWriter write = new PrintWriter(escribir)) {
                write.println("label= \"Reporte de árbol\";");
                write.println("}");
                write.close();
            }

            //Generar la imagen con el comando cmd
            String rutaImagen = ruta_absoluta + File.separator + "ARBOLES_202109754" + File.separator + "Arbol" + numDoc + ".png";
            crearImagenArbol(ruta, rutaImagen);
        }
    }

    private void crearImagenArbol(String rutaDot, String rutaPng) {
        try {
            ProcessBuilder pbuild = new ProcessBuilder("dot", "-Tpng", "-o", rutaPng, rutaDot);
            pbuild.redirectErrorStream(true);
            pbuild.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el reporte del árbol." + e, "Error con el árbol.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearGrafoArbol(NodoArbol nodo, String pathDot) throws IOException {
        if (nodo != null) {
            crearGrafoArbol(nodo.getHijoIzquierdo(), pathDot);

            //Escribimos dentro del archivo .dot
            try (FileWriter escribir = new FileWriter(pathDot, true); PrintWriter write = new PrintWriter(escribir)) {
                if (nodo.getValor().length() == 1) {
                    write.println("\"node" + nodo.getNumNodo() + "\"[label = \"<f0>" + nodo.getPrimeros() + " |{ " + anulable(nodo) + " | \\" + nodo.getValor() + " | " + nodo.getId() + " } |<f2>" + nodo.getUltimos() + " \"];");
                } else {
                    write.println("\"node" + nodo.getNumNodo() + "\"[label = \"<f0>" + nodo.getPrimeros() + " |{ " + anulable(nodo) + " | " + nodo.getValor() + " | " + nodo.getId() + " } |<f2>" + nodo.getUltimos() + " \"];");
                }

                //Validar hijo izquierdo
                if (nodo.getHijoIzquierdo() != null) {
                    write.println("\"node" + nodo.getNumNodo() + "\":f0 -> \"node" + nodo.getHijoIzquierdo().getNumNodo() + "\";");
                }

                //Validad hijo derecho
                if (nodo.getHijoDerecho() != null) {
                    write.println("\"node" + nodo.getNumNodo() + "\":f2 -> \"node" + nodo.getHijoDerecho().getNumNodo() + "\";");
                }

                write.close();
            }
            crearGrafoArbol(nodo.getHijoDerecho(), pathDot);
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

    private String anulable(NodoArbol nodo) {
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

    private void setUltimos(NodoArbol nodo) {
        if (nodo.getHijoIzquierdo() != null) {
            setUltimos(nodo.getHijoIzquierdo());
        }

        if (nodo.getHijoDerecho() != null) {
            setUltimos(nodo.getHijoDerecho());
        }

        switch (nodo.getTipo()) {
            case "valor":
                nodo.setUltimos(Integer.toString(nodo.getId()));
                break;
            case "operacion":
                if (nodo.getValor().equals("|")) {
                    String ultimos = nodo.getHijoIzquierdo().getUltimos() + "," + nodo.getHijoDerecho().getUltimos();
                    nodo.setUltimos(ultimos);
                } else if (nodo.getHijoDerecho().getAnulable() == true) {
                    String ultimos = nodo.getHijoIzquierdo().getUltimos() + "," + nodo.getHijoDerecho().getUltimos();
                    nodo.setUltimos(ultimos);
                } else {
                    nodo.setUltimos(nodo.getHijoDerecho().getUltimos());
                }
                break;
            case "cerradura":
                nodo.setUltimos(nodo.getHijoIzquierdo().getUltimos());
                break;
            case "aceptacion":
                nodo.setUltimos(Integer.toString(nodo.getId()));
                break;
        }

    }

    private void setPrimeros(NodoArbol nodo) {
        if (nodo.getHijoIzquierdo() != null) {
            setPrimeros(nodo.getHijoIzquierdo());
        }

        if (nodo.getHijoDerecho() != null) {
            setPrimeros(nodo.getHijoDerecho());
        }

        switch (nodo.getTipo()) {
            case "valor":
                nodo.setPrimeros(Integer.toString(nodo.getId()));
                break;
            case "operacion":
                if (nodo.getValor().equals("|")) {
                    String primeros = nodo.getHijoIzquierdo().getPrimeros() + "," + nodo.getHijoDerecho().getPrimeros();
                    nodo.setPrimeros(primeros);
                } else if (nodo.getHijoIzquierdo().getAnulable() == true) {
                    String primeros = nodo.getHijoIzquierdo().getPrimeros() + "," + nodo.getHijoDerecho().getPrimeros();
                    nodo.setPrimeros(primeros);
                } else {
                    nodo.setPrimeros(nodo.getHijoIzquierdo().getPrimeros());
                }
                break;
            case "cerradura":
                nodo.setPrimeros(nodo.getHijoIzquierdo().getPrimeros());
                break;
            case "aceptacion":
                nodo.setPrimeros(Integer.toString(nodo.getId()));
                break;
        }

    }

    private void setAnulables(NodoArbol nodo) {
        if (nodo.getHijoIzquierdo() != null) {
            setAnulables(nodo.getHijoIzquierdo());
        }

        if (nodo.getHijoDerecho() != null) {
            setAnulables(nodo.getHijoDerecho());
        }

        switch (nodo.getTipo()) {
            case "valor":
                nodo.setAnulable(false);
                break;
            case "operacion":
                if (nodo.getValor().equals("|")) {
                    if (nodo.getHijoIzquierdo().getAnulable() == true || nodo.getHijoDerecho().getAnulable() == true) {
                        nodo.setAnulable(true);
                    } else {
                        nodo.setAnulable(false);
                    }
                } else if (nodo.getHijoIzquierdo().getAnulable() == true && nodo.getHijoDerecho().getAnulable() == true) {
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

    public ArrayList<Siguientes> generarTablaDeSiguientes() {
        ArrayList<Siguientes> tablaSiguientes = new ArrayList<>();
        insertarHojas(tablaSiguientes, this.raiz);
        insertarSiguientes(tablaSiguientes, this.raiz);
        return tablaSiguientes;
    }

    private void insertarSiguientes(ArrayList<Siguientes> tablaSiguientes, NodoArbol nodo) {
        if (nodo == null) {
            return;
        }

        if (nodo.getTipo().equals("valor") || nodo.getTipo().equals("aceptacion")) {
            tablaSiguientes.add(new Siguientes(nodo.getValor(), nodo.getId()));
        }

        insertarSiguientes(tablaSiguientes, nodo.getHijoIzquierdo());
        insertarSiguientes(tablaSiguientes, nodo.getHijoDerecho());

        if (nodo.getTipo().equals("operacion") || nodo.getTipo().equals("cerradura")) {
            switch (nodo.getValor()) {
                case "*":
                    insertarS(tablaSiguientes, nodo.getPrimeros(), nodo.getUltimos());
                    break;
                case ".":
                    insertarS(tablaSiguientes, nodo.getHijoDerecho().getPrimeros(), nodo.getHijoIzquierdo().getUltimos());
                    break;
                case "+":
                    insertarS(tablaSiguientes, nodo.getPrimeros(), nodo.getUltimos());
                    break;
                default:
                    //Ignorar
                    break;
            }
        }
    }

    private void insertarS(ArrayList<Siguientes> tablaSiguientes, String siguientes, String valores) {
        String[] numerosValores = valores.split(",");
        for (String numero : numerosValores) {
            int indice = obtenerIndiceValor(tablaSiguientes, numero);
            if (indice != -1) {
                tablaSiguientes.get(indice).setSiguientes(siguientes);
            }
        }
    }

    private int obtenerIndiceValor(ArrayList<Siguientes> tablaSiguientes, String numero) {
        for (int i = 0; i < tablaSiguientes.size(); i++) {
            if (tablaSiguientes.get(i).getId() == Integer.parseInt(numero)) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPosicionValor(ArrayList<Siguientes> tablaSiguientes, String numero) {
        for (int i = 0; i < tablaSiguientes.size(); i++) {
            Siguientes actualSiguiente = tablaSiguientes.get(i);
            if (actualSiguiente.getId() == Integer.parseInt(numero)) {
                return i;
            }
        }
        return -1;
    }

    private void insertarHojas(ArrayList<Siguientes> tablaSiguientes, NodoArbol nodo) {

        if (nodo.getTipo().equals("valor") || nodo.getTipo().equals("aceptacion")) {
            tablaSiguientes.add(new Siguientes(nodo.getValor(), nodo.getId()));
        }

        if (nodo.getHijoIzquierdo() != null) {
            insertarHojas(tablaSiguientes, nodo.getHijoIzquierdo());
        }

        if (nodo.getHijoDerecho() != null) {
            insertarHojas(tablaSiguientes, nodo.getHijoDerecho());
        }
    }

    private boolean arbolVacio() {
        return this.raiz == null;
    }
}
