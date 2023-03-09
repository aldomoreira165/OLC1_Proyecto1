package models;

import analizador.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorReporteErrores {
    private String cadena;
    private int contador = 0;
    public void generarHTML() {
        cadena += "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "        *{\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            font-family: Arial, Helvetica, sans-serif;\n" +
                "        }\n" +
                "        h1{\n" +
                "            text-align: center;\n" +
                "            font-size: 45px;\n" +
                "            background-color: black;\n" +
                "            color: white;\n" +
                "        }\n" +
                "        body{\n" +
                "            background-color: #242a63;\n" +
                "        }\n" +
                "        #table-container{\n" +
                "            margin: 20px;\n" +
                "            display: flex;\n" +
                "            align-items: center;\n" +
                "            justify-content: center;\n" +
                "        }\n" +
                "        table{\n" +
                "            background-color: white;\n" +
                "            text-align: left;\n" +
                "            border-collapse: collapse;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "        th, td{\n" +
                "            padding: 20px; \n" +
                "        }\n" +
                "        tr:nth-child(even){\n" +
                "            background-color: #ddd;\n" +
                "        }\n" +
                "        tr:hover td{\n" +
                "            background-color: #369681;\n" +
                "            color: white;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>REPORTE DE ERRORES</h1>\n" +
                "    <div id=\"table-container\">\n" +
                "        <table>\n" +
                "            <tr>\n" +
                "                <th>#</th>\n" +
                "                <th>Tipo de Error</th>\n" +
                "                <th>Descripción</th>\n" +
                "                <th>Línea</th>\n" +
                "                <th>Columna</th>\n" +
                "            </tr>\n";

        if (!scanner.erroresLexicos.isEmpty()) {
            for (Excepcion error : scanner.erroresLexicos) {
                contador += 1;
                cadena += "<tr>\n" +
                        "                <td>" + contador + "</td>\n" +
                        "                <td>" + error.getTipo() + "</td>\n" +
                        "                <td>" + error.getDescripcion() + "</td>\n" +
                        "                <td>" + error.getLinea() + "</td>\n" +
                        "                <td>" + error.getColumna() + "</td>\n" +
                        "            </tr>";
            }
        }
        if (!parser.erroresSintacticos.isEmpty()) {
            for (Excepcion error : parser.erroresSintacticos) {
                contador += 1;
                cadena += "<tr>\n" +
                        "                <td>" + contador + "</td>\n" +
                        "                <td>" + error.getTipo() + "</td>\n" +
                        "                <td>" + error.getDescripcion() + "</td>\n" +
                        "                <td>" + error.getLinea() + "</td>\n" +
                        "                <td>" + error.getColumna() + "</td>\n" +
                        "            </tr>";
            }
        }

        cadena += "       </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";


        //creando la carpeta
        String path = new File(".").getAbsolutePath();
        File carpeta = new File(path, "ERRORES_202109754");
        if (!carpeta.exists()) { // Si la carpeta no existe
            if (carpeta.mkdir()) { // Intenta crear la carpeta
                System.out.println("Carpeta creada exitosamente.");
            } else {
                System.out.println("No se pudo crear la carpeta.");
            }
        } else {
            System.out.println("La carpeta ya existe.");
        }

        //generando archivo
        try {
            FileWriter archivo = new FileWriter(path + File.separator + "ERRORES_202109754" + File.separator + "errores.html");
            archivo.write(cadena);
            archivo.close();
            System.out.println("El archivo se ha creado correctamente.");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al crear el archivo: " + e.getMessage());
        }
    }

}
