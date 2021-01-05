/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetranferenciaarchivos;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author fnico
 */
public class Ventana extends JFrame {
    JButton btn_seleccionarArchivos;
    JLabel lbl_instruccion, lbl_tamBuffer ;
    JTextArea txta_archivos;
    JTextField txtf_tamBuffer;
    JCheckBox chkbx_nagle;
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
        
        lbl_instruccion = new JLabel();
        lbl_instruccion.setText("Seleccione los arhivos a enviar");
        lbl_instruccion.setBounds(10,10,200,30);
        contenedor.add(lbl_instruccion);   
        
        btn_seleccionarArchivos = new JButton("Seleccionar");
        btn_seleccionarArchivos.setBounds(10, 50, 150, 30);
        btn_seleccionarArchivos.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        //Metodo a llamar cuando se pulse el botón
                        abrirSelectorDeArchivos(evt);
                    }
                });        
        contenedor.add(btn_seleccionarArchivos);
        
        txta_archivos = new JTextArea();
        txta_archivos.setBounds(10,100,460,400);
        contenedor.add(txta_archivos);
        
        lbl_tamBuffer = new JLabel();
        lbl_tamBuffer.setText("Tamaño de buffer (En Bytes):");
        lbl_tamBuffer.setBounds(200,20,180,30);
        contenedor.add(lbl_tamBuffer);   
        
        txtf_tamBuffer = new JTextField();
        txtf_tamBuffer.setBounds(380, 20, 80, 30);
        ((AbstractDocument)txtf_tamBuffer.getDocument()).setDocumentFilter(new FiltroNumeros());
        contenedor.add(txtf_tamBuffer);
        
        chkbx_nagle = new JCheckBox("Algoritmo de Nagle");  
        chkbx_nagle.setBounds(300, 50, 150, 50);
        contenedor.add(chkbx_nagle);
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
