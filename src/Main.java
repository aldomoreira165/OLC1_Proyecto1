import analizador.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Compilador.compilarArchivo();
        String cadenita = "{\n" +
                "<!\n" +
                "Este es un comentario\n" +
                "en nuestro programa\n" +
                "!>"+
                "CONJ:letra1->a~z;\n" +
                "CONJ:digito2->2~5;\n" +
                "//Este es un comentario lol\n" +
                "%%\n" +
                "%%\n" +
                "ExpReg1:\"primerLexemaCokoa1\";\n" +
                "ExpresionReg2:\"34.44\";\n" +
                "}";
        interpretar(cadenita);
    }

    public static String getFile(String direccion) throws IOException {
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            String texto;

            while ((bfRead = bf.readLine()) != null) {
                temp += bfRead;
            }

            texto = temp;

            return texto;
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