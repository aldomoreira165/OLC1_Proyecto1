package analizador;

public class Compilador {
    public static void compilarArchivo(){
        try{
            String ruta = "src/analizador/";
            String opcFlex[] = {ruta + "AnalizadorLexico.jflex", "-d", ruta};
            jflex.Main.generate(opcFlex);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
