import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import videoReceptor.JavaClient;
import videoTransmisor.JavaServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jared
 */
public class catalogoPeliculas extends javax.swing.JFrame implements Runnable{

    String username,ip,peliculaSeleccionada,peliculaTransmitir;
    int puerto;
    String msj = "obtenerPeliculas";
    ImageIcon iconobtnBuscar = new ImageIcon("src/Iconos/lupa.jpg");
    ImageIcon iconobtnRefresh = new ImageIcon("src/Iconos/refresh.png");
    DefaultListModel<String> peliculasDisponibles = new DefaultListModel();
    boolean transmitir=false,verPelicula=false,refresh=false;
    Socket cl;
    PrintWriter out;
    BufferedReader in;
    boolean videoStreamflag = false;
    
    /**
     * Creates new form catalogoPeliculas
     */
    public catalogoPeliculas() {
        initComponents();
    }
    
    public catalogoPeliculas(String nombre, String dir_IP, int pto){
        initComponents();
        peliculaSeleccionada = "";
        peliculaTransmitir = "";
        username = nombre;
        ip = dir_IP;
        puerto = pto;
        this.setVisible(true);
        lbl_Username.setText(username);
        Icon iconoBuscar = new ImageIcon(iconobtnBuscar.getImage().getScaledInstance(lbl_Buscar.getWidth(),lbl_Buscar.getHeight(),Image.SCALE_SMOOTH));
        Icon iconoRefresh = new ImageIcon(iconobtnRefresh.getImage().getScaledInstance(btn_Refresh.getWidth(),btn_Refresh.getHeight(),Image.SCALE_SMOOTH));
        lbl_Buscar.setIcon(iconoBuscar);
        btn_Refresh.setIcon(iconoRefresh);
        //llenarLista();
        initSocket();
                    
            //para que alguien transmita se envia la siguiente cadena:
            //String registrarPelicula = "transmitir:nombre_de_pelicula";
            //regresa un:ok si si se registro la pelicula en el nodo central
            
            //para degar de transmitir:
            //se envia: dejarTransmitir:nombrepelicula
            //retorna: Se elimino la informacion del nodo central
        try {
            msj = "obtenerPeliculas";
            out.println(msj);
            msj=in.readLine();
            System.out.println("respuesta de servidor:"+ msj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
            
    }
    
    public void initSocket(){
        
    try { //siempre dentre de bloques try catch cuando trabajemos con sockets
            
            cl = new Socket(ip,puerto);    
            out = new PrintWriter(cl.getOutputStream(), true); 
            in = new BufferedReader(new InputStreamReader(cl.getInputStream()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void llenarLista(){
    
        peliculasDisponibles.addElement("Toy Story");
        peliculasDisponibles.addElement("Toy Story 2");
        peliculasDisponibles.addElement("Toy Story 3");
        peliculasDisponibles.addElement("Toy Story 4");
        peliculasDisponibles.addElement("Mi villano favorito 1");
        peliculasDisponibles.addElement("Mi villano favorito 2");
        peliculasDisponibles.addElement("Gool");
        peliculasDisponibles.addElement("La momia");
        peliculasDisponibles.addElement("Hercules");
        peliculasDisponibles.addElement("Mulan");
        peliculasDisponibles.addElement("Soul");
        peliculasDisponibles.addElement("Grandes Heroes");
        peliculasDisponibles.addElement("UP");
        peliculasDisponibles.addElement("La cita perfecta");
        peliculasDisponibles.addElement("La cita perfecta 2");
        jlst_Peliculas.setModel(peliculasDisponibles);
    }
    
    public void actualizarCatalogo(String peliculasActualizadas){
    
        String[] peliculas = peliculasActualizadas.split(",");
        peliculasDisponibles = new DefaultListModel();
        jlst_Peliculas.removeAll();
        for(String pelicula:peliculas)
        {
            pelicula.replace(" ","");
            pelicula.trim();
            if(!pelicula.contains("obtenerPeliculas") || pelicula.isEmpty()){
                peliculasDisponibles.addElement(pelicula);
            }
        }
        jlst_Peliculas.setModel(peliculasDisponibles);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_Username = new javax.swing.JLabel();
        lbl_Peliculas = new javax.swing.JLabel();
        jtxt_Buscador = new javax.swing.JTextField();
        btn_Refresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlst_Peliculas = new javax.swing.JList<>();
        lbl_Seleccion = new javax.swing.JLabel();
        btn_Transmitir = new javax.swing.JButton();
        lbl_Transmitir = new javax.swing.JLabel();
        lbl_Buscar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Catalogo de Peliculas");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        lbl_Username.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        lbl_Username.setText("userName");

        lbl_Peliculas.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        lbl_Peliculas.setText("Peliculas Disponibles:");

        jtxt_Buscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxt_BuscadorKeyReleased(evt);
            }
        });

        btn_Refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_RefreshMouseClicked(evt);
            }
        });
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });

        jlst_Peliculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlst_PeliculasMouseClicked(evt);
            }
        });
        jlst_Peliculas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlst_PeliculasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jlst_Peliculas);

        lbl_Seleccion.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        lbl_Seleccion.setText("Selecciona que pelicula quieres ver");

        btn_Transmitir.setText("Transmitir");
        btn_Transmitir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_TransmitirMouseClicked(evt);
            }
        });

        lbl_Transmitir.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        lbl_Transmitir.setText("¿Quieres transmitir una película?");

        lbl_Buscar.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        lbl_Buscar.setText("lbl");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(btn_Transmitir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(lbl_Transmitir)))
                .addContainerGap(129, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_Peliculas)
                            .addComponent(lbl_Seleccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lbl_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxt_Buscador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_Username)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_Seleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_Peliculas)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Refresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxt_Buscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_Transmitir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_Transmitir)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void btn_RefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_RefreshMouseClicked
        System.out.println("Boton refresh");
        initSocket();
        msj="obtenerPeliculas";
        out.println(msj);
        System.out.println("Mensaje enviaddo: " + msj);
        try {
            msj=in.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("respuesta de servidor:"+ msj);
        actualizarCatalogo(msj);
    }//GEN-LAST:event_btn_RefreshMouseClicked

    private void btn_TransmitirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_TransmitirMouseClicked
        System.out.println("Boton para Transmitir");
        peliculaTransmitir = JOptionPane.showInputDialog(null, "Titulo de pelicula a transmitir", "Transmitir pelicula",JOptionPane.QUESTION_MESSAGE);
        initSocket();
        msj="transmitir:" + peliculaTransmitir;
        out.println(msj);
        System.out.println("Mensaje enviaddo: " + msj);
        try {
            msj=in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(catalogoPeliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("respuesta de servidor:"+ msj);
        try {
             Thread t = new Thread(this);
             t.start();
        } catch (Exception ex) {
            Logger.getLogger(catalogoPeliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_TransmitirMouseClicked

    private void jtxt_BuscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_BuscadorKeyReleased
                JTextField textField = (JTextField) evt.getSource();
                //obtiene contenido del textfield
                String text = textField.getText();
                if (text.trim().length() > 0) {
                    //nuevo Model temporal
                    DefaultListModel<String> tmp = new DefaultListModel();
                    for (int i = 0; i < peliculasDisponibles.getSize(); i++) {//recorre Model original
                        //si encuentra coincidencias agrega a model temporal
                        if (peliculasDisponibles.getElementAt(i).toLowerCase().contains(text.toLowerCase())) {
                            tmp.addElement(peliculasDisponibles.getElementAt(i));
                        }
                    }
                    //agrega nuevo modelo a JList
                    jlst_Peliculas.setModel(tmp);
                } else {//si esta vacio muestra el Model original
                    jlst_Peliculas.setModel(peliculasDisponibles);
                }
    }//GEN-LAST:event_jtxt_BuscadorKeyReleased

    private void jlst_PeliculasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlst_PeliculasValueChanged

    }//GEN-LAST:event_jlst_PeliculasValueChanged

    private void jlst_PeliculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlst_PeliculasMouseClicked

        if (evt.getClickCount() == 2) { 
            initSocket();
            peliculaSeleccionada = jlst_Peliculas.getSelectedValue().toString();
            System.out.println("Pelicula seleccionada: " + peliculaSeleccionada);
            msj="obtener:" + peliculaSeleccionada;
            out.println(msj);
            System.out.println("Mensaje enviaddo: " + msj); 
             try {
            msj=in.readLine();
            } catch (IOException ex) {
            Logger.getLogger(catalogoPeliculas.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("respuesta de servidor:"+ msj);
           try {
               JavaClient.init(msj);
           } catch (Exception ex) {
               Logger.getLogger(catalogoPeliculas.class.getName()).log(Level.SEVERE, null, ex);
           }
        } 
    }//GEN-LAST:event_jlst_PeliculasMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            in.close();
            out.close();
            cl.close();
        } catch (IOException ex) {
            Logger.getLogger(catalogoPeliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Refresh;
    private javax.swing.JButton btn_Transmitir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> jlst_Peliculas;
    private javax.swing.JTextField jtxt_Buscador;
    private javax.swing.JLabel lbl_Buscar;
    private javax.swing.JLabel lbl_Peliculas;
    private javax.swing.JLabel lbl_Seleccion;
    private javax.swing.JLabel lbl_Transmitir;
    private javax.swing.JLabel lbl_Username;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            new JavaServer();
        } catch (Exception e) {
            System.out.println("run de catalogo: ");
            e.printStackTrace();
        }
        initSocket();
        msj="dejarTransmitir:" + peliculaTransmitir;
        out.println(msj);
        System.out.println("Mensaje enviaddo: " + msj);
        initSocket();
        msj="obtenerPeliculas";
        out.println(msj);
        System.out.println("Mensaje enviaddo: " + msj);
        actualizarCatalogo(msj);
    }
}
