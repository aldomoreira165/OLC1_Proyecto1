import analizador.Compilador;
import analizador.parser;
import analizador.scanner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException {
        Compilador.compilarArchivo();
        String cadenita = "{\nCONJ:letra->a~z;\nCONJ:digito->0~9;\n}";
        interpretar(cadenita);
    }
    public static void interpretar(String entrada){
        try{
            scanner scanner = new scanner(new java.io.StringReader(entrada));
            parser parser = new parser(scanner);
            parser.parse();
            System.out.printf("Análisis Finalizado");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}