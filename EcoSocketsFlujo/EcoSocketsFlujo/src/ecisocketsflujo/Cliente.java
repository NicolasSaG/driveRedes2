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
            
            
            Socket cl = new Socket("189.133.77.160", 1234);
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(cl.getOutputStream(), true); 
            BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            
            String msj = "";
            while((msj = stdIn.readLine()) != null){
                out.println(msj);
                System.out.println("Eco: " + in.readLine());
                
                if(msj.equals("Adios"))
                        break;
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
