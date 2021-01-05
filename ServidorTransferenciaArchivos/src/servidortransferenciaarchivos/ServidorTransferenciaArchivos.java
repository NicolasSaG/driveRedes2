package servidortransferenciaarchivos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fnico
 */
public class ServidorTransferenciaArchivos {
    public static void main(String[] args) {
        try{
            ServerSocket s = new ServerSocket(7000);
            while(true){
                // Esperamos una conexión 
                Socket cl = s.accept();
                DataOutputStream dos;
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                int numArchivos = dis.readInt();
                int tamBuffer = dis.readInt();
                byte[] b = new byte[tamBuffer];
                
                System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/numero de archivos:"+numArchivos);
                System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/tamano de buffer:"+tamBuffer);
                
                //empieza recepcion de archivos
                for(int i = 0; i < numArchivos; i++){
                    String nombreArchivo = dis.readUTF();
                    System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/recibiendo nombre:"+nombreArchivo);
                    long tam = dis.readLong();
                    System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/recibiendo tam:"+tam);
                    dos = new DataOutputStream(new FileOutputStream(nombreArchivo));
                    long recibidos = 0;
                    int n, porcentaje;
                    while(recibidos < tam){
                        n = dis.read(b);
                        dos.write(b,0,n);
                        dos.flush();
                        recibidos = recibidos + n;
                        porcentaje = (int)(recibidos*100/tam);
                        System.out.print("Recibido: "+porcentaje+"%\r");
                    }//While
                    System.out.println("Archivo " + (i+1)+" recibido.");
                    dos.close();
                }

                dis.close();
                cl.close();
            }
        }catch(Exception e){
                e.printStackTrace();
        }//cat
    }
    
}
