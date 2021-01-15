/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodocentral;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author fnico
 */
public class NodoTest {
    public static void main(String[] args) {
        try { //siempre dentre de bloques try catch cuando trabajemos con sockets
            Socket cl = new Socket("127.0.0.1", 8999);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(cl.getOutputStream(), true); 
            BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            
            String msj = "";
            out.println(msj);
            System.out.println("Eco: " + in.readLine()); 
            if(msj.equals("Adios"))
            
            
            
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
