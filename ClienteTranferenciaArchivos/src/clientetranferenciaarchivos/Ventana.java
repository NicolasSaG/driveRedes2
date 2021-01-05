/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetranferenciaarchivos;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
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
    JButton btn_seleccionarArchivos, btn_enviarArchivos;
    JLabel lbl_instruccion, lbl_tamBuffer ;
    JTextArea txta_archivos;
    JTextField txtf_tamBuffer;
    JCheckBox chkbx_nagle;
    JProgressBar progressBar;
    
    Socket cl;
    DataOutputStream dos;
    DataInputStream dis;// = new DataInputStream(new FileInputStream(archivo));    
    
    private final int puerto = 7000;
    private final String host = "127.0.0.1";
    
    private File[] files;
    public Ventana() throws IOException{
        //inicializar socket
        cl = new Socket(host, puerto);
        dos = new DataOutputStream(cl.getOutputStream());
        files = null;
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
        
        btn_enviarArchivos = new JButton("Enviar");
        btn_enviarArchivos.setBounds(360, 510, 100, 30);
        btn_enviarArchivos.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        try {
                            //Metodo a llamar cuando se pulse el botón
                            iniciarEnvioArchivos();
                        } catch (SocketException ex) {
                            ex.printStackTrace();
                        }
                    }
                });        
        contenedor.add(btn_enviarArchivos);
        
        progressBar = new JProgressBar(0,100);
        progressBar.setBounds(150, 510, 180, 30);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        contenedor.add(progressBar);
        
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

    private void iniciarEnvioArchivos() throws SocketException{
        
        if(txtf_tamBuffer.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Ingrese el tamano de buffer.");
            return;
        }
        if(Integer.parseInt(txtf_tamBuffer.getText()) < 1){
            JOptionPane.showMessageDialog(this, "El tamaño del buffer debe ser de 1 byte o mayor");
            return;
        }
        if(files == null){
            JOptionPane.showMessageDialog(this, "No se han seleccionado archivos.");
            return;
        }
            
        //algoritmo de nagle
        if(chkbx_nagle.isSelected()){ //descomentarlas cuando se inicialice el socket
            cl.setTcpNoDelay(false);
            System.out.println("Algoritmo de nagle activado");
        }else{
            cl.setTcpNoDelay(true);
            System.out.println("Algoritmo de nagle desactivado");
        }
            
        enviarNumArchivos();
        enviarTamBuffer();
        enviarArchivos();  
    }
    
    private void abrirSelectorDeArchivos(MouseEvent evt){
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

    private void enviarNumArchivos() {
        
    }

    private void enviarTamBuffer() {
        
    }

    private void enviarArchivos() {
        
    }
    
}
