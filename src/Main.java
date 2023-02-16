import analizador.Compilador;
import analizador.parser;
import analizador.scanner;
public class Main {
    public static void main(String[] args) {
        Compilador.compilarArchivo();
        interpretar("{}");
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