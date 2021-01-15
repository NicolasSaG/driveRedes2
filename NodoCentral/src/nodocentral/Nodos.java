/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodocentral;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 *
 * @author fnico
 */
public class Nodos {
    ArrayList <InetAddress> nodos;
    public Nodos(InetAddress firstIP){
        nodos = new ArrayList<InetAddress>();
        nodos.add(firstIP);
    }
    public void anadirNodo(InetAddress ip){
        if(!nodos.contains(ip)){
            nodos.add(ip);
        }
    }
    
}
