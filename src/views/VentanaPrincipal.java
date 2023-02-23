package views;

import models.ManipuladorData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class VentanaPrincipal extends JFrame{

    private File archivoSeleccionado;
    private String textoArchivo;
    private JPanel panelPrincipal;
    public VentanaPrincipal() {

        //instancia de la clase para manejo de la informacion del archivo
        ManipuladorData manipulador = new ManipuladorData();

        //area de archivo .olc
        JTextArea areaArchivo = new JTextArea();
        areaArchivo.setBounds(10,10,420,460);
        areaArchivo.setFont(new Font("Arial",Font.BOLD, 15));
        add(areaArchivo);

        //area de consola
        JTextArea areaConsola = new JTextArea();
        areaConsola.setBounds(10,530,865,200);
        areaConsola.setFont(new Font("Arial",Font.BOLD, 15));
        areaConsola.setForeground(Color.RED);
        add(areaConsola);

        //agregando botones
        JButton botonGenerarAutomata = new JButton("Generar Autómata");
        botonGenerarAutomata.setBounds(10, 485, 200, 30);
        add(botonGenerarAutomata);

        JButton botonAnalizarEntrada = new JButton("Analizar Entrada");
        botonAnalizarEntrada.setBounds(230, 485, 200, 30);
        add(botonAnalizarEntrada);

        // Crear el menú "Archivo
        JMenuBar menuBar = new JMenuBar();
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem nuevoArchivoItem = new JMenuItem("Nuevo Archivo");
        JMenuItem abrirArchivoItem = new JMenuItem("Abrir Archivo");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        JMenuItem guardarComoItem = new JMenuItem("Guardar Como");
        archivoMenu.add(nuevoArchivoItem);
        archivoMenu.add(abrirArchivoItem);
        archivoMenu.addSeparator();
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);

        //agregando eventos a los componentes graficos
        nuevoArchivoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                archivoSeleccionado = null;
                textoArchivo = null;
                areaArchivo.setText("");
            }
        });

        abrirArchivoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos .olc", "olc");
                fileChooser.setFileFilter(filter);
                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    archivoSeleccionado = fileChooser.getSelectedFile();
                    // Código para leer el archivo seleccionado como cadena de texto
                    StringBuilder sb = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(archivoSeleccionado))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            sb.append(linea);
                            sb.append(System.lineSeparator());
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    String contenidoArchivo = sb.toString();
                    areaArchivo.setText(contenidoArchivo);
                    areaConsola.setText("");
                    textoArchivo = contenidoArchivo;
                }
            }
        });

        guardarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (archivoSeleccionado != null) {
                    try (FileWriter fw = new FileWriter(archivoSeleccionado)) {
                        String contenido = areaArchivo.getText();
                        fw.write(contenido);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"No has abierto ningún archivo.");
                }
            }
        });

        guardarComoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Guardar como...");
                chooser.setApproveButtonText("Guardar");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos .olc", "olc"));
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File archivo = chooser.getSelectedFile();
                    if (!archivo.getName().endsWith(".olc")) {
                        archivo = new File(archivo.getAbsolutePath() + ".olc");
                    }
                    try (PrintWriter writer = new PrintWriter(archivo)) {
                        writer.print(areaArchivo.getText());
                    } catch (FileNotFoundException E) {
                        E.printStackTrace();
                    }
                    // Actualizar el archivo seleccionado
                    archivoSeleccionado = archivo;
                }
            }
        });

        //funcionalidad de los botones
        botonGenerarAutomata.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manipulador.interpretar(textoArchivo);
                String erroresL = manipulador.obtener_erroresE();
                areaConsola.setText("");
                areaConsola.setText(erroresL);
            }
        });

        panelPrincipal.setBackground(Color.blue);
        add(panelPrincipal);
        setTitle("ExRegan USAC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
