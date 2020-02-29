/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class Cliente {

    public static final String DIRECCION = "localhost";
    
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        DatagramSocket datagram = null;
        DatagramPacket datagramaRecibido;

        try {
            datagram = new DatagramSocket();
            
            datagram.setSoTimeout(30000);

            InetAddress address = InetAddress.getByName(DIRECCION);
            
            String mensaje;

            do {
                System.out.print("Mensaje: ");
                mensaje = teclado.nextLine();

                DatagramPacket datagrama = new DatagramPacket(mensaje.getBytes(),
                        mensaje.getBytes().length, address, 5555);

                datagram.send(datagrama);

                System.out.println("CLIENTE: Enviando "
                        + new String(datagrama.getData()) + " a "
                        + datagrama.getAddress().toString() + ":"
                        + datagrama.getPort());

                byte[] bytesMensaje = new byte[1024];
                datagramaRecibido = new DatagramPacket(bytesMensaje, bytesMensaje.length);
                datagram.receive(datagramaRecibido);

                System.out.println("CLIENTE: Recibido "
                        + new String(datagramaRecibido.getData(), 0, datagramaRecibido.getLength())
                        + " de " + datagramaRecibido.getAddress().toString() + ":"
                        + datagramaRecibido.getPort());

            } while (!mensaje.equalsIgnoreCase("Adios"));
            
         } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagram.close();
        }
    }

}
