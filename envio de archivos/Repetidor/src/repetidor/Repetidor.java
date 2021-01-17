package repetidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fnico
 */
public class Repetidor {
    public static void main(String[] args) {
        try{
            ServerSocket s = new ServerSocket(7000);
                        
            while(true){
                // Esperamos una conexión 
                Socket cl = s.accept();
                Socket rep = new Socket("127.0.0.1", 7001);
                DataOutputStream dosRep = new DataOutputStream(rep.getOutputStream());
                DataOutputStream dos;
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis = new DataInputStream(cl.getInputStream());
                int numArchivos = dis.readInt();
                int tamBuffer = dis.readInt();
                
                //enviar num de archivos al repetidor o cola
                dosRep.writeInt(numArchivos);
                dosRep.flush();
                //enviar tam de buffer al repetidor o cola
                dosRep.writeInt(tamBuffer);
                dosRep.flush();
                
                
                byte[] b = new byte[tamBuffer];
                
                System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/numero de archivos:"+numArchivos);
                System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/tamano de buffer:"+tamBuffer);
                
                //empieza recepcion de archivos
                for(int i = 0; i < numArchivos; i++){
                    String nombreArchivo = dis.readUTF();
                    dosRep.writeUTF(nombreArchivo);
                    dosRep.flush();               
                    
                    System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/recibiendo nombre:"+nombreArchivo);
                    long tam = dis.readLong();
                    dosRep.writeLong(tam);
                    dosRep.flush();
                    System.out.println(cl.getInetAddress()+":"+cl.getPort()+"/recibiendo tam:"+tam);
                    dos = new DataOutputStream(new FileOutputStream(nombreArchivo));
                    long recibidos = 0;
                    int n, porcentaje;
                    while(tam > 0 && (n = dis.read(b, 0, (int)Math.min(b.length, tam))) != -1){   
                        dos.write(b, 0, n);
                        dosRep.write(b, 0, n);
                        //dos.flush();
                        tam -= n;
                    }//While
                    System.out.println("Archivo " + (i+1)+" recibido.");
                    dos.close();
                    
                }
                dosRep.close();
                dis.close();
                cl.close();
            }
        }catch(Exception e){
                e.printStackTrace();
        }//cat
    }
    
}
