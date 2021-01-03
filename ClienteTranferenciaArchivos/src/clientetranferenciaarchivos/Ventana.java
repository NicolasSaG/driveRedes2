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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author fnico
 */
public class Ventana extends JFrame {
    JButton btn_seleccionarArchivos;
    JTextField txtf_archivosSeleccionados;
    
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
        
        btn_seleccionarArchivos = new JButton("Abrir");
        btn_seleccionarArchivos.setBounds(350, 150, 100, 30);
        btn_seleccionarArchivos.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evt) {
                        //Metodo a llamar cuando se pulse el botón
                        abrirSelectorDeArchivos(evt);
                    }
                });
        contenedor.add(btn_seleccionarArchivos);
    }

    private void abrirSelectorDeArchivos(MouseEvent evt){
        JFileChooser jf = new JFileChooser();
        int r = jf.showOpenDialog(this);
        if (r==JFileChooser.APPROVE_OPTION){
            System.out.println("aproada");
        }
    }
}
