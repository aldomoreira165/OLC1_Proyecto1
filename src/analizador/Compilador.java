package analizador;

public class Compilador {
    public static void compilarArchivo(){
        try{
            String ruta = "src/analizador/";
            String opcFlex[] = {ruta + "AnalizadorLexico.jflex", "-d", ruta};
            jflex.Main.generate(opcFlex);
            String opcCUP[] = {"-destdir", ruta, "-parser", "parser", ruta + "AnalizadorSintactico.cup"};
            java_cup.Main.main(opcCUP);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
