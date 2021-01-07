/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secotcpnb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 * @author fnico
 */
public class Cli {
    String dir="127.0.0.1";
    int pto = 9999;
    ByteBuffer b1 = null, b2 = null;
    InetSocketAddress dst;
    SocketChannel cl;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Selector sel;   
    public Cli(){
        try {
            dst = new InetSocketAddress(dir, pto);
            cl = SocketChannel.open();
            br = new BufferedReader(new InputStreamReader(System.in));
            cl.configureBlocking(false);
            sel = Selector.open();
            cl.register(sel, SelectionKey.OP_CONNECT);
            cl.connect(dst);
        } catch (Exception e) {
        }
    }    
    
    public void conectarse() {
        try {
            sel.select();
            Iterator<SelectionKey>it = sel.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey k = (SelectionKey)it.next();
                it.remove();
                if(k.isConnectable()){
                        SocketChannel ch = (SocketChannel)k.channel();
                        if(ch.isConnectionPending()){
                            System.out.println("Estableciendo conexion con el servidor... espere..");
                            try{
                                ch.finishConnect();
                            }catch(Exception e){
                                e.printStackTrace();
                            }//catch
                            System.out.println("Conexion establecida...\nEscribe texto <Enter> para enviar, SALIR para terminar:");
                        }//if
                        ch.register(sel, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        continue;
                    }//if
            }
        } catch (Exception e) {
        }
    }
    
    public void enviarDatos(){
        try {
            sel.select();
            Iterator<SelectionKey>it = sel.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey k = (SelectionKey)it.next();
                it.remove();
                if(k.isWritable()){
                    SocketChannel ch = (SocketChannel)k.channel();
                    String datos=""; 
                    datos=br.readLine();
                    byte[]mm = datos.getBytes();
                    System.out.println("Enviando eco de "+mm.length+" bytes..");
                    b2 = ByteBuffer.wrap(mm);
                    ch.write(b2);
                    k.interestOps(SelectionKey.OP_READ);
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void recibirDatos(){
        try {
            sel.select();
            Iterator<SelectionKey>it = sel.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey k = (SelectionKey)it.next();
                it.remove();
                if(k.isReadable()){
                    SocketChannel ch = (SocketChannel)k.channel();
                    b1 = ByteBuffer.allocate(2000);
                    b1.clear();
                    int n = ch.read(b1);
                    b1.flip();
                   String eco = new String(b1.array(),0,n);
                    System.out.println("Eco  de "+n+" bytes recibido: "+eco);
                    k.interestOps(SelectionKey.OP_WRITE);
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void desconectarse(){
        try {
            sel.select();
            Iterator<SelectionKey>it = sel.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey k = (SelectionKey)it.next();
                it.remove();
                if(k.isWritable()){
                    SocketChannel ch = (SocketChannel)k.channel();
                    System.out.println("Termina aplicacion...");
                    byte[]mm = "SALIR".getBytes();
                    b2 = ByteBuffer.wrap(mm);
                    ch.write(b2);
                    k.interestOps(SelectionKey.OP_READ);
                    k.cancel();
                    ch.close();
                    System.exit(0);
                }
            }
        } catch (Exception e) {
        }
    }
}
