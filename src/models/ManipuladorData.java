package models;

import analizador.*;

public class ManipuladorData {
    public void interpretar(String entrada){
        try{
            scanner scanner = new scanner(new java.io.StringReader(entrada));
            parser parser = new parser(scanner);
            parser.parse();
            System.out.println("Análisis Finalizado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String obtener_erroresE(){
        StringBuilder cadena = new StringBuilder();
        int size = scanner.errores.size();

        if (size == 0){
            cadena.append("Análisis Finalizado");
        }else{
            for (int i = 0; i < size; i++) {
                cadena.append(scanner.errores.get(i));
                cadena.append("\n");
            }
        }
        return cadena.toString();
    }
}
