package models;

import analizador.*;

public class ManipuladorData {
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
