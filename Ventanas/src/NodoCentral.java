import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author fnico
 */
public class NodoCentral {

    Hashtable<String, InetAddress> nodos;
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
                System.out.println(msjRecibido);
                //transmitir:nombrepelicula
                if(msjRecibido.substring(0,11).equals("transmitir:")){//transmitir:pelicula:ip
                    System.out.println("Transmitir pelicula: "+msjRecibido);
                    String pelicula = obtenerNombrePelicula(msjRecibido);
                    //out.println("Aqui esta el servidooor");
                    //buscar pelicula
                    if(!buscarPelicula(pelicula)){//agregar nuevo nodo a la pelicula
                        nodos.put(pelicula, cl.getInetAddress());
                        System.out.println("Nombre de pelicula: |"+pelicula+"|");
                        out.println("Te has registrado en nodo central exitosamente");
                        System.out.println("Te has registrado como nodo");     
                    }else{
                        System.out.println("La pelicula ya existe");
                        out.println("No se pudo registrar, la pelicula ya existe");
                    }
                }else if(msjRecibido.substring(0,16).equals("dejarTransmitir:")){
                    String nombrePelicula = msjRecibido.substring(16);
                    dejarTransmitir(nombrePelicula);
                    out.println("Se elimino la informacion del nodo central");
                }else if(msjRecibido.substring(0,8).equals("obtener:")){//enviar catalogo de peliculas
                    String pelicula = msjRecibido.substring(8);
                    String ip = obtenerIp(pelicula);
                    out.println(ip);  
                    System.out.println("ip enviada");
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
    
    private String obtenerIp(String pelicula){
        String ip = "";
        if(nodos.containsKey(pelicula)){
            ip = nodos.get(pelicula).getHostAddress();
        }
        
        return ip;
    }
    
    private String obtenerNombrePelicula(String text){
        String nombrePelicula = "";
        try {
            int finNombrePelicula = text.indexOf(":");
            nombrePelicula = text.substring(finNombrePelicula+1);
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
                peliculas += key+",";
            }
        }else{
            peliculas += ",";
        }
        System.out.println(peliculas);
        return peliculas;
    }
    
    private void dejarTransmitir(String pelicula){
        if(nodos.containsKey(pelicula)){
            nodos.remove(pelicula);
        }else{
            System.out.println("No existe esa pelicula para remover");
        }
        
    }
}
