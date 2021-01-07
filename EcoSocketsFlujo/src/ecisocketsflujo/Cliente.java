/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecisocketsflujo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author fnico
 */
public class Cliente {
    public static void main(String[] args) {
        try { //siempre dentre de bloques try catch cuando trabajemos con sockets
            System.out.println("dir:");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            Socket cl = new Socket("10.0.0.10", 1234);
            
            
            PrintWriter out = new PrintWriter(cl.getOutputStream()); 
            BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            
            String msj = "";
            
            while((msj = stdIn.readLine()) != null){
                System.out.println("mensaje escrito");
                out.println(msj);
                
                System.out.println("Eco: " + in.readLine());
                if(msj.equals("exit")){
                    break;
                }
            }
            
            //primero cerrar flujos
            stdIn.close();
            in.close();
            out.flush();
            out.close();
            cl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
