/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetranferenciaarchivos;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author fnico
 */
public class Ventana extends JFrame {
    JButton btn_seleccionarArchivos;
    JLabel txtf_instruccion;
    JTextArea txta_archivos;
    private File[] files;
    public Ventana(){
        init();
    }
    
    public void init(){
        Container contenedor = getContentPane();
        setBounds(0, 0, 500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Servicio de transferencia de archivos");
        setResizable(false);
        
        txtf_instruccion = new JLabel();
        txtf_instruccion.setText("Seleccione los arhivos a enviar");
        txtf_instruccion.setBounds(10,10,200,30);
        btn_seleccionarArchivos = new JButton("Seleccionar");
        btn_seleccionarArchivos.setBounds(10, 50, 150, 30);
        txta_archivos = new JTextArea();
        txta_archivos.setBounds(10,100,460,400);
        btn_seleccionarArchivos.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        //Metodo a llamar cuando se pulse el botón
                        abrirSelectorDeArchivos(evt);
                    }
                });
        contenedor.add(txtf_instruccion);
        contenedor.add(btn_seleccionarArchivos);
        contenedor.add(txta_archivos);
    }

    private void abrirSelectorDeArchivos(MouseEvent evt){
       /* JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(this);
        if (r==JFileChooser.APPROVE_OPTION){
            System.out.println("aprobada");
        }*/
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.showOpenDialog(this);  
        files = fc.getSelectedFiles();
        txta_archivos.append("Archivos seleccionados (" + files.length +")\n");
        for (int i=0; i<files.length; i++) {
            txta_archivos.append("Nombre del archivo: " + files[i].getName() + " tamaño del archivo: " + files[i].length() + " bytes\n");
            System.out.println("Nombre del archivo: " + files[i].getName() + " tamaño del archivo: " + files[i].length() + " bytes");
        }                      
    }
    
}
