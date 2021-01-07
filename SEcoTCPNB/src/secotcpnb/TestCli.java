/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secotcpnb;

/**
 *
 * @author fnico
 */
public class TestCli {
    public static void main(String[] args) {
        Cli cl = new Cli();
        cl.conectarse();
        cl.enviarDatos();
        cl.recibirDatos();
        cl.enviarDatos();
        cl.recibirDatos();
        cl.desconectarse();
    }
}
