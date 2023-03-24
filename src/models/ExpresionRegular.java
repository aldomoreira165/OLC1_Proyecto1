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
    private String numero;

    public ExpresionRegular(String id, String numero) {
        this.id = id;
        this.arbolExpresion = new Arbol();
        this.cadenas = new ArrayList<>();
        this.tablaSiguientes = new ArrayList<>();
        this.tablaEstados = new ArrayList<>();
        this.arbolExpresion.crearNuevoArbol();
        this.numero = numero;
    }

    public void cadenaInsertar(String cadena) {
        this.cadenas.add(new Cadena(cadena));
    }

    //creando la tabla de estados
    public void crearTabST() {
        ArrayList<Estados> tabla_Estados = new ArrayList<>();
        estados = 0;

        tabla_Estados.add(new Estados("S0", arbolExpresion.getRaiz().getPrimeros(), estadoAceptacion(arbolExpresion.getRaiz().getPrimeros())));

        int iteraciones = 1;

        while (iteraciones != 0) {
            iteraciones--;

            for (int i = 0; i < tabla_Estados.size(); i++) {
                Estados estadoActual = tabla_Estados.get(i);

                String numeroDeConjunto = estadoActual.getNumContenidos();
                String[] numerosDeID = numeroDeConjunto.split(",");

                for (String numero : numerosDeID) {
                    try {
                        if (this.tablaSiguientes.get(posicionIdEnTablaSig(numero)).getSiguientes() != "") {
                            if (!existeEstadoNum(tabla_Estados, this.tablaSiguientes.get(posicionIdEnTablaSig(numero)).getSiguientes())) {
                                estados++;
                                tabla_Estados.add(new Estados("S" + estados, this.tablaSiguientes.get(posicionIdEnTablaSig(numero)).getSiguientes(), estadoAceptacion(this.tablaSiguientes.get(posicionIdEnTablaSig(numero)).getSiguientes())));
                                iteraciones++;
                            }
                        }

                    } catch (Exception e) {

                    }
                }

            }
        }

        for (int i = 0; i < tabla_Estados.size(); i++) {
            String numeroDeConjunto = tabla_Estados.get(i).getNumContenidos();
            String[] numerosDeID = numeroDeConjunto.split(",");
            for (String numeroID : numerosDeID) {
                if (!"".equals(this.tablaSiguientes.get(posicionIdEnTablaSig(numeroID)).getSiguientes())) {
                    String siguientes = this.tablaSiguientes.get(Integer.parseInt(numeroID) - 1).getSiguientes();
                    int posicionEstado = posicionEstadoNumeros(tabla_Estados, siguientes);
                    if (!tabla_Estados.get(i).verificarExistenciaTransicion(tabla_Estados.get(posicionEstado).getId(), Integer.parseInt(numeroID))) {
                        tabla_Estados.get(i).agregarTransicion(tabla_Estados.get(posicionEstado).getId(), this.tablaSiguientes.get(Integer.parseInt(numeroID) - 1).getValor(), Integer.parseInt(numeroID));
                    }
                }
            }
        }

        this.tablaEstados = tabla_Estados;
    }

    private boolean estadoAceptacion(String numeroConjunto) {
        int idUltimoTablaSiguientes = this.tablaSiguientes.get(this.tablaSiguientes.size() - 1).getId();
        String[] identificadores = numeroConjunto.split(",");
        for (String identificador : identificadores) {
            if (Integer.parseInt(identificador) == idUltimoTablaSiguientes) {
                return true;
            }
        }
        return false;
    }

    public int posicionIdEnTablaSig(String numero) {
        int posicion = 0;
        for (Siguientes siguiente : tablaSiguientes) {
            if (siguiente.getId() == Integer.parseInt(numero)) {
                break;
            }
            posicion++;
        }
        return posicion;
    }

    public int posicionEstadoNumeros(ArrayList<Estados> tablaEstados, String numerosConj) {
        int posicion = 0;
        for (Estados estado : tablaEstados) {
            if (estado.getNumContenidos().equals(numerosConj)) {
                break;
            }
            posicion++;
        }
        return posicion;
    }

    public boolean existeEstadoNum(ArrayList<Estados> tablaEstados, String numerosConj) {
        for (Estados estado : tablaEstados) {
            if (estado.getNumContenidos().equals(numerosConj)) {
                return true;
            }
        }
        return false;
    }

    public void nodoInsertar(String tipo, String valor) {
        this.arbolExpresion.insertar(valor, tipo);
    }

    public void crearGraficaTablaEstados() throws IOException {
        if (!tablaSiguientes.isEmpty()) {
            //creacion de la carpeta que contiene los arboles
            String ruta = new File(".").getAbsolutePath();
            String ruta_absoluta = ruta;
            crear_carpeta("TRANSICIONES_202109754");
            ruta += File.separator + "TRANSICIONES_202109754" + File.separator + "TablaEstados" + getNumero() + ".dot";
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            int contadorSimbolos = tablaSiguientes.size();
            //Escribimos dentro del archivo .dot
            try (PrintWriter write = new PrintWriter(ruta, "UTF-8")) {
                write.println("digraph TablaEstados{");
                write.println("tbl [");
                write.println("shape = plaintext");
                write.println("label = <");
                write.println("<table border='0' cellborder='1' color='black' cellspacing='0'>");

                //Encabezado de la tabla
                write.print("<tr><td></td>");
                for (int i = 0; i < contadorSimbolos - 1; i++) {
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

                //Codigo  Tabla
                Iterator<Estados> iteradorEstados = tablaEstados.iterator();
                while (iteradorEstados.hasNext()) {
                    Estados actualEstado = iteradorEstados.next();

                    String estadosTabla = "";

                    if (actualEstado.getTransiciones().size() != 0) {
                        estadosTabla = "<tr><td>" + actualEstado.getId() + "</td>";
                    }

                    for (int j = 0; j < contadorSimbolos - 1; j++) {
                        estadosTabla += actualEstado.ObtenerTransicionDoc(this.tablaSiguientes.get(j).getId());
                    }

                    if (actualEstado.getTransiciones().size() != 0) {
                        estadosTabla += "</tr>";
                    }

                    write.println(estadosTabla);
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
            String rutaImagen = ruta_absoluta + File.separator + "TRANSICIONES_202109754"+ File.separator + "TablaEstados" + getNumero() + ".png";
            crearPNG(ruta, rutaImagen);
        }
    }

    public void crearGraficoTablaSiguientes() throws IOException {
        if (!tablaSiguientes.isEmpty()) {
            //creacion de la carpeta que contiene la tabla de siguientes
            String ruta = new File(".").getAbsolutePath();
            String ruta_absoluta = ruta;
            crear_carpeta("SIGUIENTES_202109754");
            ruta += File.separator + "SIGUIENTES_202109754" + File.separator + "TablaSiguientes" + getNumero() + ".dot";
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
                //Codigo  Tabla
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
            String rutaImagen = ruta_absoluta+ File.separator + "SIGUIENTES_202109754" + File.separator + "TablaSiguientes" + getNumero() + ".png";
            crearPNG(ruta, rutaImagen);
        }
    }

    public void crearGraficoAutomataFinito() throws IOException {
        if (!tablaEstados.isEmpty()) {
            String ruta = new File(".").getAbsolutePath();
            String ruta_absoluta = ruta;
            crear_carpeta("AFD_202109754");
            ruta += File.separator + "AFD_202109754"+ File.separator + "AFD" + getNumero() + ".dot";
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            try (PrintWriter write = new PrintWriter(ruta, "UTF-8")) {
                write.println("digraph AFD{");
                write.println("rankdir=LR;");
                write.println("size=\"13\"");

                for (int i = 0; i <= this.tablaEstados.size() - 1; i++) {
                    if (this.tablaEstados.get(i).isEstadoAceptacion()) {
                        write.println(this.tablaEstados.get(i).getId() + "[peripheries = 2, shape=circle];");
                    }
                }

                write.println("node [shape=circle,peripheries = 1];");
                write.println("node [fontcolor=black];");
                write.println("edge [color=black];");
                write.println("secret_node [style=invis];");
                write.println("secret_node -> S0 [label=\"inicio\"];");

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

            String rutaImagen = ruta_absoluta + File.separator + "AFD_202109754" + File.separator + "AFD" + getNumero() + ".png";
            crearPNG(ruta, rutaImagen);
        }
    }

    private void crearPNG(String rutaDot, String rutaPng) {
        try {
            ProcessBuilder builder = new ProcessBuilder("dot", "-Tpng", "-o", rutaPng, rutaDot);
            builder.redirectErrorStream(true);
            builder.start();
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

    public void obtenerTabS() {
        this.tablaSiguientes = this.arbolExpresion.generarTablaDeSiguientes();
    }

    public String getId() {
        return id;
    }

    public Arbol obtenerArbolER() {
        return arbolExpresion;
    }

    public ArrayList<Cadena> getCadenas() {
        return cadenas;
    }

    public String getNumero() {
        return numero;
    }

    public ArrayList<Estados> getTablaEstados() {
        return tablaEstados;
    }
}
