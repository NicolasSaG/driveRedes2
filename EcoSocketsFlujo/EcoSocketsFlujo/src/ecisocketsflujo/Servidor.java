/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecisocketsflujo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author fnico
 */
public class Servidor {
    public static void main(String[] args) {
        try {
            //se tiene el listener para empezar a recibir conexiones
            System.out.println("Servidor de Eco");
            ServerSocket s = new ServerSocket(7001);
            System.out.println("Esperando cliente...");
            while(true){
                //del lado del server hay serversocket y socket
                
                Socket cl = s.accept();
                System.out.println("conexion establecida desde " + cl.getInetAddress() + ":" + cl.getPort());
                
                PrintWriter out = new PrintWriter(cl.getOutputStream(), true);//con println se auto flushea 
                BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
                
                String msjRecibido;
                while ((msjRecibido = in.readLine()) != null) {
                    System.out.println("Recibiendo " + msjRecibido +"de "+ cl.getInetAddress() + ":" + cl.getPort() );
                    out.println(msjRecibido);
                    if(msjRecibido.equals("exit"))
                        break;
                }

                
                
                in.close();
                out.flush();
                out.close();
                cl.close();
                //el serversocket siempre se mantiene abierto para recibir la siguiente conexion
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
