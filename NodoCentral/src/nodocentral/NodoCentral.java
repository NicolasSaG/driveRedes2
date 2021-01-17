package nodocentral;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author fnico
 */
public class NodoCentral {

    Hashtable<String, Nodos> nodos;
    public void init() {
        nodos = new Hashtable(); 
        try {
            System.out.println("Iniciando nodo central en puerto "+1255+"...");
            ServerSocket s = new ServerSocket(1255);
            System.out.println("Nodo central iniciado.");
            System.out.println("Esperando conexion de nodos...");
            
            while(true){
                Socket cl = s.accept();
                System.out.println("Conexion establecida con nodo " + cl.getInetAddress() + ":" + cl.getPort());
                
                PrintWriter out = new PrintWriter(cl.getOutputStream(), true);//con println se auto flushea 
                BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
                
                String msjRecibido;
                
                msjRecibido = in.readLine(); 
                
                if(msjRecibido.substring(0,11).equals("transmitir:")){//transmitir:pelicula:ip
                    String pelicula = obtenerNombrePelicula(msjRecibido.substring(11));
                    //out.println("Aqui esta el servidooor");
                    //buscar pelicula
                    if(buscarPelicula(pelicula)){//agregar nuevo nodo a la pelicula
                        nodos.get(pelicula).anadirNodo(cl.getInetAddress());
                        System.out.println("alguien ya esta transmitiendo la pelicula");
                    }else{//agregar nuevo elemento a la hashtable
                        nodos.put(pelicula, new Nodos(cl.getInetAddress()));
                        out.println("Te has registrado en nodo central exitosamente");
                        System.out.println("Te has registrado como nodo");
                    } 
                }else if(msjRecibido.substring(0,16).equals("obtenerPeliculas")){//enviar catalogo de peliculas
                    String pelis = obtenerCatalogoDePeliculas();
                    out.println(pelis);  
                    System.out.println("Peliculas enviadas");
                }
 
                
                in.close();
                out.flush();
                out.close();
                cl.close();
            }
        } catch (Exception e) {
        }
    }
    
    public String obtenerNombrePelicula(String text){
        String nombrePelicula = "";
        try {
            int finNombrePelicula = text.indexOf(":");
            nombrePelicula = text.substring(0, finNombrePelicula);
        } catch (Exception e) {
            System.out.println("obtenerNombrePelicula(): Error obteniendo nombre de pelicula.");
        }
        return nombrePelicula;   
    }

    private boolean buscarPelicula(String pelicula) {
        boolean peliculaEncontrada = false;
        if(nodos.containsKey(pelicula)){
            peliculaEncontrada = true;
        }
        return peliculaEncontrada;
    }
    
    private void leerCatalogoDePeliculas(){
        Set<String> keys = nodos.keySet();
        System.out.println("Peliculas:");
        for (String key: keys) {
            System.out.println(key);
        }
        System.out.println("--------");
    }
    
    private int obtenerNumeroPeliculas(){
        return nodos.keySet().size();
    }
    
    private String obtenerCatalogoDePeliculas(){
        Set<String> keys = nodos.keySet();
        String peliculas = "";
        if(obtenerNumeroPeliculas() > 0){
            for (String key : keys) {
                peliculas += nodos.get(key)+",";
            }
        }else{
            peliculas += ",";
        }
        return peliculas;
    }
}
