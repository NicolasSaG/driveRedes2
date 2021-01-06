/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Cola {
    public static void main(String[] args) {
        try{
            ServerSocket s = new ServerSocket(7001);
            
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
                        //System.out.println("Recibido: "+recibidos*100/tam+" de 100");
                        
                        n = dis.read(b);
//                        
                        dos.write(b, 0, n);
                        dos.flush();
                        recibidos = recibidos + n;
                        
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
