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
}
