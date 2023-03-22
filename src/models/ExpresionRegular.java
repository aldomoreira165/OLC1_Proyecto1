package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

public class ExpresionRegular {

    private int estados = 0;
    private String id;
    private Arbol arbolExpresion;
    private ArrayList<Cadena> cadenas;
    private ArrayList<Siguientes> tablaSiguientes;
    private ArrayList<Estados> tablaEstados;
    private String numDocumentos;

    public ExpresionRegular(String id, String numDocumentos) {
        this.id = id;
        this.arbolExpresion = new Arbol();
        this.cadenas = new ArrayList<>();
        this.tablaSiguientes = new ArrayList<>();
        this.tablaEstados = new ArrayList<>();
        this.arbolExpresion.crearNuevoArbol();
        this.numDocumentos = numDocumentos;
    }

    public String getNumDocumentos() {
        return numDocumentos;
    }

    public ArrayList<Estados> getTablaEstados() {
        return tablaEstados;
    }

    //falta
    public void crearTablaEstados() {
        ArrayList<Estados> tabla_Estados = new ArrayList<>();
        estados = 0;

        //Obtener el Estado S0 (Primeros del nodo raiz)
        tabla_Estados.add(new Estados("S0", arbolExpresion.getRaiz().getPrimeros(), estadoAceptacion(arbolExpresion.getRaiz().getPrimeros())));

        int repetirFor = 1;

        //Insertar los encabezados de estados
        while (repetirFor != 0) {
            repetirFor--;
            //Desglozar todos los estados
            for (int i = 0; i < tabla_Estados.size(); i++) {
                Estados actualEstado = tabla_Estados.get(i);

                String numConjunto = actualEstado.getNumContenidos();
                String[] numerosID = numConjunto.split(",");

                //Insertar los encabezados de estados
                for (String numero : numerosID) {
                    try {
                        if (this.tablaSiguientes.get(posIdTablaSiguientes(numero)).getSiguientes() != "") {
                            if (!existeEstadoNumeros(tabla_Estados, this.tablaSiguientes.get(posIdTablaSiguientes(numero)).getSiguientes())) {
                                estados++;
                                tabla_Estados.add(new Estados("S" + estados, this.tablaSiguientes.get(posIdTablaSiguientes(numero)).getSiguientes(), estadoAceptacion(this.tablaSiguientes.get(posIdTablaSiguientes(numero)).getSiguientes())));
                                repetirFor++;
                            }
                        }

                    } catch (Exception e) {
                        //No hacer nada
                    }
                }

            }
        }

        //Insertar transiciones
        for (int i = 0; i < tabla_Estados.size(); i++) {
            String numConjunto = tabla_Estados.get(i).getNumContenidos();
            String[] numerosID = numConjunto.split(",");
//            System.out.println(tabla_Estados.get(i).getIdEstado() + "{" + numConjunto + "}");
            for (String numero : numerosID) {
                if (!"".equals(this.tablaSiguientes.get(posIdTablaSiguientes(numero)).getSiguientes())) {
                    String siguientes = this.tablaSiguientes.get(Integer.parseInt(numero) - 1).getSiguientes();
                    int posEstado = posEstadoNumeros(tabla_Estados, siguientes);
                    if (!tabla_Estados.get(i).verificarExistenciaTransicion(tabla_Estados.get(posEstado).getId(), Integer.parseInt(numero))) {
                        tabla_Estados.get(i).agregarTransicion(tabla_Estados.get(posEstado).getId(), this.tablaSiguientes.get(Integer.parseInt(numero) - 1).getValor(), Integer.parseInt(numero));
                        //System.out.println("Sig{" + numero + "}=" + this.tablaSiguientes.get(Integer.parseInt(numero) - 1).getSiguientes() + "=" + tabla_Estados.get(posEstado).getIdEstado());
                    }
                }
            }
        }
        //printEstados();
        //Insertar las conexiones
        this.tablaEstados = tabla_Estados;

    }

    private boolean estadoAceptacion(String numConjunto) {
        String[] numerosID = numConjunto.split(",");
        for (String numero : numerosID) {
            if (Integer.parseInt(numero) == this.tablaSiguientes.get(this.tablaSiguientes.size() - 1).getId()) {
                return true;
            }
        }
        return false;
    }

    public int posEstadoActual(String idEstado) {
        int pos = 0;
        Iterator<Estados> iteradorEstados = tablaEstados.iterator();
        while (iteradorEstados.hasNext()) {
            Estados actualEstado = iteradorEstados.next();
            if (actualEstado.getId().equals(idEstado)) {
                break;
            }
            pos++;
        }
        return pos;
    }

    public int posIdTablaSiguientes(String numero) {
        int pos = 0;
        Iterator<Siguientes> iteradorSiguientes = this.tablaSiguientes.iterator();
        while (iteradorSiguientes.hasNext()) {
            Siguientes actualSiguiente = iteradorSiguientes.next();
            if (actualSiguiente.getId() == Integer.parseInt(numero)) {
                break;
            }
            pos++;
        }
        return pos;
    }

    public int posEstadoNumeros(ArrayList<Estados> tablaEstados, String numerosConj) {
        int pos = 0;
        Iterator<Estados> iteradorEstados = tablaEstados.iterator();
        while (iteradorEstados.hasNext()) {
            Estados actualEstado = iteradorEstados.next();
            if (actualEstado.getNumContenidos().equals(numerosConj)) {
                break;
            }
            pos++;
        }
        return pos;
    }

    public boolean existeEstadoNumeros(ArrayList<Estados> tablaEstados, String numerosConj) {
        Iterator<Estados> iteradorEstados = tablaEstados.iterator();
        while (iteradorEstados.hasNext()) {
            Estados actualEstado = iteradorEstados.next();
            if (actualEstado.getNumContenidos().equals(numerosConj)) {
                return true;
            }
        }
        return false;
    }

    public void crearTablaSiguientes() {
        this.tablaSiguientes = this.arbolExpresion.generarTablaDeSiguientes();
    }

    public ArrayList<Siguientes> getTablaSiguientes() {
        return tablaSiguientes;
    }

    public String getId() {
        return id;
    }

    public Arbol getArbolExpresion() {
        return arbolExpresion;
    }

    public ArrayList<Cadena> getCadenas() {
        return cadenas;
    }

    public void insertCadena(String cadena) {
        this.cadenas.add(new Cadena(cadena));
    }

    public void insertNodo(String tipo, String valor) {
        this.arbolExpresion.insertar(valor, tipo);
    }

    public void graficarTablaEstados() throws IOException {
        if (!tablaSiguientes.isEmpty()) {
            //creacion de la carpeta que contiene los arboles
            String ruta = new File(".").getAbsolutePath();
            String ruta_absoluta = ruta;
            crear_carpeta("TRANSICIONES_202109754");
            ruta += File.separator + "TRANSICIONES_202109754" + File.separator + "TablaEstados" + getNumDocumentos() + ".dot";
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            int countSimbolos = tablaSiguientes.size();
            //Escribimos dentro del archivo .dot
            try (PrintWriter write = new PrintWriter(ruta, "UTF-8")) {
                write.println("digraph TablaEstados{");
                write.println("tbl [");
                write.println("shape = plaintext");
                write.println("label = <");
                write.println("<table border='0' cellborder='1' color='black' cellspacing='0'>");

                //Encabezado de la tabla
                write.print("<tr><td></td>");
                for (int i = 0; i < countSimbolos - 1; i++) {
                    switch (tablaSiguientes.get(i).getValor()) {
                        case "<":
                            write.print("<td>&lt;</td>");
                            break;
                        case ">":
                            write.print("<td>&gt;</td>");
                            break;
                        case "&":
                            write.print("<td>&amp;</td>");
                            break;
                        default:
                            write.print("<td>" + tablaSiguientes.get(i).getValor() + "</td>");
                    }
                }
                write.println("</tr>");

                //Codigo HTML Tabla
                Iterator<Estados> iteradorEstados = tablaEstados.iterator();
                while (iteradorEstados.hasNext()) {
                    Estados actualEstado = iteradorEstados.next();

                    String estadosHTML = "";

                    if (actualEstado.getTransiciones().size() != 0) {
                        estadosHTML = "<tr><td>" + actualEstado.getId() + "</td>";
                    }

                    for (int j = 0; j < countSimbolos - 1; j++) {
                        estadosHTML += actualEstado.ObtenerTransicionDoc(this.tablaSiguientes.get(j).getId());
                    }

                    if (actualEstado.getTransiciones().size() != 0) {
                        estadosHTML += "</tr>";
                    }

                    write.println(estadosHTML);
                }
                //---------------

                write.println("</table>");
                write.println(">];");
                write.println("}");
                write.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(null, "Error al crear el reporte de archivos." + e, "Error con los archivos.", JOptionPane.ERROR_MESSAGE);
            }

            //Generar la imagen con el comando cmd
            String rutaImagen = ruta_absoluta + File.separator + "TRANSICIONES_202109754"+ File.separator + "TablaEstados" + getNumDocumentos() + ".png";
            crearImagen(ruta, rutaImagen);
        }
    }

    public void graficarTablaSiguientes() throws IOException {
        if (!tablaSiguientes.isEmpty()) {
            //creacion de la carpeta que contiene la tabla de siguientes
            String ruta = new File(".").getAbsolutePath();
            String ruta_absoluta = ruta;
            crear_carpeta("SIGUIENTES_202109754");
            ruta += File.separator + "SIGUIENTES_202109754" + File.separator + "TablaSiguientes" + getNumDocumentos() + ".dot";
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            //Escribimos dentro del archivo .dot
            try (PrintWriter write = new PrintWriter(ruta, "UTF-8")) {
                write.println("digraph TablaSiguientes{");
                write.println("tbl [");
                write.println("shape = plaintext");
                write.println("label = <");
                write.println("<table border='0' cellborder='1' color='black' cellspacing='0'>");
                write.println("<tr><td>Valor</td><td>Id</td><td>Siguientes</td></tr>");
                //Codigo HTML Tabla
                Iterator<Siguientes> iteradorSiguientes = tablaSiguientes.iterator();
                while (iteradorSiguientes.hasNext()) {
                    Siguientes actualSiguiente = iteradorSiguientes.next();
                    switch (actualSiguiente.getValor()) {
                        case "<":
                            write.println("<tr><td>&lt;</td><td>" + actualSiguiente.getId() + "</td><td>" + actualSiguiente.getSiguientes() + "</td></tr>");
                            break;
                        case ">":
                            write.println("<tr><td>&gt;</td><td>" + actualSiguiente.getId() + "</td><td>" + actualSiguiente.getSiguientes() + "</td></tr>");
                            break;
                        case "&":
                            write.println("<tr><td>&amp;</td><td>" + actualSiguiente.getId() + "</td><td>" + actualSiguiente.getSiguientes() + "</td></tr>");
                            break;
                        default:
                            write.println("<tr><td>" + actualSiguiente.getValor() + "</td><td>" + actualSiguiente.getId() + "</td><td>" + actualSiguiente.getSiguientes() + "</td></tr>");
                            break;
                    }

                }
                //---------------
                write.println("</table>");
                write.println(">];");
                write.println("}");
                write.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(null, "Error al crear el reporte de archivos." + e, "Error con los archivos.", JOptionPane.ERROR_MESSAGE);
            }

            //Generar la imagen con el comando cmd
            String rutaImagen = ruta_absoluta+ File.separator + "SIGUIENTES_202109754" + File.separator + "TablaSiguientes" + getNumDocumentos() + ".png";
            crearImagen(ruta, rutaImagen);
        }
    }

    public void graficarAFD() throws IOException {
        if (!tablaEstados.isEmpty()) {
            String ruta = new File(".").getAbsolutePath();
            String ruta_absoluta = ruta;
            crear_carpeta("AFD_202109754");
            ruta += File.separator + "AFD_202109754"+ File.separator + "AFD" + getNumDocumentos() + ".dot";
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
            //Escribimos dentro del archivo .dot
            try (PrintWriter write = new PrintWriter(ruta, "UTF-8")) {
                write.println("digraph AFD{");
                write.println("rankdir=LR;");
                write.println("size=\"13\"");

                //Crear nodo de aceptacion
                for (int i = 0; i <= this.tablaEstados.size() - 1; i++) {
                    if (this.tablaEstados.get(i).isEstadoAceptacion()) {
                        write.println(this.tablaEstados.get(i).getId() + "[peripheries = 2, shape=circle];");
                    }
                }

                //Datos de los demas nodos
                write.println("node [shape=circle,peripheries = 1];");
                write.println("node [fontcolor=black];");
                write.println("edge [color=black];");
                write.println("secret_node [style=invis];");
                write.println("secret_node -> S0 [label=\"inicio\"];");

                //Insertar todas las conexiones
                for (int i = 0; i <= this.tablaEstados.size() - 1; i++) {
                    for (int j = 0; j <= this.tablaEstados.get(i).getTransiciones().size() - 1; j++) {
                        write.println(this.tablaEstados.get(i).getId() + " -> " + this.tablaEstados.get(i).getTransiciones().get(j).getEstadoNext() + "[label=\"" + this.tablaEstados.get(i).getTransiciones().get(j).getValor() + "\"];");
                    }
                }

                write.println("}");
                write.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(null, "Error al crear el reporte de archivos." + e, "Error con los archivos.", JOptionPane.ERROR_MESSAGE);
            }

            //Generar la imagen con el comando cmd
            String rutaImagen = ruta_absoluta + File.separator + "AFD_202109754" + File.separator + "AFD" + getNumDocumentos() + ".png";
            crearImagen(ruta, rutaImagen);
        }
    }

    private void crearImagen(String rutaDot, String rutaPng) {
        try {
            ProcessBuilder pbuild = new ProcessBuilder("dot", "-Tpng", "-o", rutaPng, rutaDot);
            pbuild.redirectErrorStream(true);
            pbuild.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el reporte." + e, "Error con la tabla.", JOptionPane.ERROR_MESSAGE);
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

}
