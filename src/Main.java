import java.io.IOException;

import analizador.Compilador;
import views.VentanaPrincipal;

public class Main {
    public static void main(String[] args) throws IOException {
        Compilador.compilarArchivo();
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setVisible(true);
    }
}