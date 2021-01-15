package nodocentral;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

/**
 *
 * @author fnico
 */
public class NodoCentral {

    Hashtable<String, Nodos> nodos;
    public void init() {
        try {
            int puerto = 8999;
            System.out.println("Iniciando nodo central en puerto "+puerto+"...");
            ServerSocket s = new ServerSocket(puerto);
            
            nodos = new Hashtable();         
            System.out.println("Nodo central iniciado.");
            System.out.println("Esperando conexion de nodos...");
            
            while(true){
                Socket cl = s.accept();
                System.out.println("Conexion establecida con nodo " + cl.getInetAddress() + ":" + cl.getPort());
                
                PrintWriter out = new PrintWriter(cl.getOutputStream());//con println se auto flushea 
                BufferedReader in = new BufferedReader(new InputStreamReader(cl.getInputStream()));
                
                String msjRecibido;
                
                if((msjRecibido = in.readLine()) != null){
                    out.println(msjRecibido);
                    
                    if(msjRecibido.substring(0,10).equals("transmitir")){//transmitir:pelicula:ip
                        String pelicula = obtenerNombrePelicula(msjRecibido);
                        System.out.println("Pelicula a registrar para transmitir: " + pelicula);
                        //buscar pelicula
                        if(buscarPelicula(pelicula)){//agregar nuevo nodo a la pelicula
                            nodos.get(pelicula).anadirNodo(cl.getInetAddress());
                        }else{//agregar nuevo elemento a la hashtable
                            nodos.put(pelicula, new Nodos(cl.getInetAddress()));
                        }
                        
                    }else if(msjRecibido.substring(0,12).equals("obtenerNodos")){//nodos que transmitan x pelicula
                        
                    }
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
}
