package LDN;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author elias
 */
public class JavaServer {

    public static InetAddress[] inet;
    public static int[] port;
    public static int i;
    static int count = 0;
    public static BufferedReader[] inFromClient;
    public static DataOutputStream[] outToClient;

    public static void main(String[] args) throws Exception {
        JavaServer jv = new JavaServer();
    }

    public JavaServer() throws Exception {

        NativeLibrary.addSearchPath("libvlc", "C:\\Program Files\\VideoLAN\\VLC"); // VLC de 64 bits
        //NativeLibrary.addSearchPath("libvlc", "c:\\Program Files (x86)\\VideoLAN\\VLC"); // VLC de 32 bits 

        JavaServer.inet = new InetAddress[30];
        port = new int[30];
        ServerSocket welcomeSocket = new ServerSocket(6782);
        System.out.println(welcomeSocket.isClosed());

        Socket connectionSocket[] = new Socket[30];
        inFromClient = new BufferedReader[30];
        outToClient = new DataOutputStream[30];

        DatagramSocket serv = new DatagramSocket(1234);
        byte[] buf = new byte[62000];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);

        Canvas_Demo canv = new Canvas_Demo();
        System.out.println("Iniciando servidor");
        i = 0;

        SThread[] st = new SThread[30];

        while (true) {

            System.out.println(serv.getPort());
            serv.receive(dp);
            System.out.println(new String(dp.getData()));
            buf = "starts".getBytes();

            inet[i] = dp.getAddress();
            port[i] = dp.getPort();

            DatagramPacket dsend = new DatagramPacket(buf, buf.length, inet[i], port[i]);
            serv.send(dsend);

            Vidthread sendvid = new Vidthread(serv);

            System.out.println("Esperando... \n ");
            connectionSocket[i] = welcomeSocket.accept();
            System.out.println("¡Conectado! " + i);

            inFromClient[i] = new BufferedReader(new InputStreamReader(connectionSocket[i].getInputStream()));
            outToClient[i] = new DataOutputStream(connectionSocket[i].getOutputStream());
            outToClient[i].writeBytes("Conectado > Servidor \n");

            st[i] = new SThread(i);
            st[i].start();

            if (count == 0) {
                Sentencefromserver sen = new Sentencefromserver();
                sen.start();
                count++;
            }

            System.out.println(inet[i]);
            sendvid.start();

            i++;

            if (i == 30) {
                break;
            }
        }
    }
}

// Hilo para la transmision de video
class Vidthread extends Thread {

    int clientno;
    JFrame jf = new JFrame("Iniciamos Streaming");
    JLabel jleb = new JLabel();

    DatagramSocket soc;

    // Objeto Robot para controlar eventos 
    Robot rb = new Robot();

    byte[] outbuff = new byte[62000];

    BufferedImage mybuf;
    ImageIcon img;
    Rectangle rc;

    int bord = Canvas_Demo.panel.getY() - Canvas_Demo.frame.getY();

    public Vidthread(DatagramSocket ds) throws Exception {
        soc = ds;

        System.out.println(soc.getPort());
        jf.setSize(500, 400);
        jf.setLocation(500, 400);
        jf.setVisible(true);
    }

    public void run() {
        while (true) {
            try {

                int num = JavaServer.i;

                rc = new Rectangle(new Point(Canvas_Demo.frame.getX() + 8, Canvas_Demo.frame.getY() + 27),
                        new Dimension(Canvas_Demo.panel.getWidth(), Canvas_Demo.frame.getHeight() / 2));

                mybuf = rb.createScreenCapture(rc);

                img = new ImageIcon(mybuf);

                jleb.setIcon(img);
                jf.add(jleb);
                jf.repaint();
                jf.revalidate();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                //Enviamos la transmision de video por fotogramas JPG
                ImageIO.write(mybuf, "jpg", baos);

                outbuff = baos.toByteArray();

                for (int j = 0; j < num; j++) {
                    DatagramPacket dp = new DatagramPacket(outbuff, outbuff.length, JavaServer.inet[j],
                            JavaServer.port[j]);
                    soc.send(dp);
                    baos.flush();
                }
                Thread.sleep(15);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

}

// Objeto Canvas para mandar la transmision
class Canvas_Demo {

    // Create a media player factory
    private MediaPlayerFactory mediaPlayerFactory;

    // Create a new media player instance for the run-time platform
    private EmbeddedMediaPlayer mediaPlayer;

    public static JPanel panel;
    public static JPanel myjp;
    private Canvas canvas;
    public static JFrame frame;
    public static JTextArea ta;
    public static JTextArea txinp;
    public static int xpos = 0, ypos = 0;
    String url = "D:\\DownLoads\\Video\\freerun.MP4"; //URL de prueba
    // Constructor
    public Canvas_Demo() {

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel mypanel = new JPanel();
        mypanel.setLayout(new GridLayout(2, 1));

        canvas = new Canvas();
        canvas.setBackground(Color.BLACK);
        panel.add(canvas, BorderLayout.CENTER);

        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
        CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
        mediaPlayer.setVideoSurface(videoSurface);

        frame = new JFrame("Servidor de Video");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(200, 0);
        frame.setSize(640, 960);
        frame.setAlwaysOnTop(true);

        mypanel.add(panel);
        frame.add(mypanel);
        frame.setVisible(true);
        xpos = frame.getX();
        ypos = frame.getY();

        // Area del video
        myjp = new JPanel(new GridLayout(4, 1));
        Button bn = new Button("Elige un Archivo");
        myjp.add(bn);
        Button sender = new Button("Mandar mensaje ->");
        JScrollPane jpane = new JScrollPane();
        jpane.setSize(300, 200);
        ta = new JTextArea();
        txinp = new JTextArea();
        jpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jpane.add(ta);
        jpane.setViewportView(ta);
        myjp.add(jpane);
        myjp.add(txinp);
        myjp.add(sender);
        ta.setText("Servidor Iniciado \n");
        ta.setCaretPosition(ta.getDocument().getLength());
        mypanel.add(myjp);
        mypanel.revalidate();
        mypanel.repaint();

        bn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.showOpenDialog(frame);
                File f;
                f = jf.getSelectedFile();
                url = f.getPath();
                System.out.println(url);
                ta.setText("--- Chat iniciado --- \n\n");
                ta.append("Streaming del archivo: \n" + url + "\n\n");
                mediaPlayer.playMedia(url);
            }
        });
        sender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Sentencefromserver.sendingSentence = txinp.getText();
                txinp.setText(null);
                Canvas_Demo.ta.append("Mi mensaje: " + Sentencefromserver.sendingSentence + "\n");
                Canvas_Demo.myjp.revalidate();
                Canvas_Demo.myjp.repaint();
            }
        });

    }
}

// Hilo para reportar los mensajes que le mandan al servidor
class SThread extends Thread {

    public static String clientSentence;
    int srcid;
    BufferedReader inFromClient = JavaServer.inFromClient[srcid];
    DataOutputStream outToClient[] = JavaServer.outToClient;

    public SThread(int a) {
        srcid = a;
    }

    public void run() {
        while (true) {
            try {
                clientSentence = inFromClient.readLine();
                System.out.println("Cliente " + srcid + " > " + clientSentence);
                Canvas_Demo.ta.append("Cliente " + srcid + " > " + clientSentence + "\n");
                for (int i = 0; i < JavaServer.i; i++) {
                    if (i != srcid) {
                        outToClient[i].writeBytes("Cliente " + srcid + " > " + clientSentence + '\n');	//'\n' is necessary
                    }
                }
                Canvas_Demo.myjp.revalidate();
                Canvas_Demo.myjp.repaint();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

//Hilo para reportar lo que pasa desde el servidor
class Sentencefromserver extends Thread {

    public static String sendingSentence;

    public Sentencefromserver() {

    }

    public void run() {
        while (true) {
            try {
                if (sendingSentence.length() > 0) {
                    for (int i = 0; i < JavaServer.i; i++) {
                        JavaServer.outToClient[i].writeBytes("Servidor > " + sendingSentence + '\n');
                    }
                    sendingSentence = null;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
