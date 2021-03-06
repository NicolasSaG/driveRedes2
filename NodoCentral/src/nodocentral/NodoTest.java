/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodocentral;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author fnico
 */
public class NodoTest {
    public static void main(String[] args) {
        try { //siempre dentre de bloques try catch cuando trabajemos con sockets
            Socket cl = new Socket("127.0.0.1", 1255);
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(cl.getOutputStream(), true); 
            BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            
            //para que alguien transmita se envia la siguiente cadena:
            //String registrarPelicula = "transmitir:nombre_de_pelicula";
            //regresa un:ok si si se registro la pelicula en el nodo central
            
            //para degar de transmitir:
            //se envia: dejarTransmitir:nombrepelicula
            //retorna: Se elimino la informacion del nodo central
            
            //para obtener la ip de una pelicula:
            //se envia: obtener:nombrepelicula
            //se regresa string con la ip que la trasnmite
            
            //para obtener las peliculas
            String msj = "obtenerPeliculas";
            //regresa: nombrepelicula1,nombrepelicula2,...,
            //si no hay peliculas regresa solo una ,
            out.println(msj);
            System.out.println("recibido del nodo central: " + in.readLine());
            
            
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
