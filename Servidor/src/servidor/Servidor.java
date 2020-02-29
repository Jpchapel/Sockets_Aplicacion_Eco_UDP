/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 *
 * @author Joaquin Pereira Chapel
 */
public class Servidor {

    public static final String DIRECCION = "localhost";
    public static final int PUERTO = 5555;

    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;
        DatagramPacket datagramaRecibido;
        DatagramPacket datagramaEnviado;

        try {
            InetSocketAddress addr = new InetSocketAddress(DIRECCION, PUERTO);
            datagramSocket = new DatagramSocket(addr);

            datagramSocket.setSoTimeout(30000);

            String mensaje;

            do {
                byte[] bytesMensaje = new byte[1024];
                datagramaRecibido = new DatagramPacket(bytesMensaje, bytesMensaje.length);
                datagramSocket.receive(datagramaRecibido);

                mensaje = (new String(bytesMensaje)).trim();

                System.out.println("Cliente: " + mensaje
                        + ". Recibido de " + datagramaRecibido.getAddress().toString()
                        + ":" + datagramaRecibido.getPort());

                datagramaEnviado = new DatagramPacket(bytesMensaje, bytesMensaje.length,
                        datagramaRecibido.getAddress(), datagramaRecibido.getPort());

                datagramSocket.send(datagramaEnviado);

            } while (!mensaje.trim().equalsIgnoreCase("Adios"));

            System.out.println("Proceso finalizado");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }

}
