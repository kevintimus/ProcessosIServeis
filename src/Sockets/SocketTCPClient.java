package Sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketTCPClient {
    int PortEnvio;
    int PortRecib;

    public SocketTCPClient(int env, int rec) throws IOException {
        this.PortEnvio = env;
        this.PortRecib = rec;
    }

    public void enviarMissatgeTCPClient(String IpDesti) throws IOException {

        Socket client_socket = new Socket(InetAddress.getByName(IpDesti), PortEnvio);
        String message;

        Scanner reader = new Scanner(client_socket.getInputStream());

        Thread lector = new Thread(() -> {

            while (reader.hasNextLine()) {
                String linea = reader.nextLine();
                System.out.println("El servidor dice " + linea);
                System.out.flush();

            }

            System.out.println("El Server ha cerrado la conexión");


        });
        lector.start();

        //////////////////////////////////////////////

        try {
            Scanner sc = new Scanner(System.in);

            PrintStream writer = new PrintStream(client_socket.getOutputStream());

            do {
                System.out.println("Dame el mensaje");
                message = sc.nextLine();

                writer.println(message);
                writer.flush();

            } while (!Objects.equals(message, "/"));

            writer.flush();
            client_socket.close();

        } catch (IOException ex) {
            Logger.getLogger(SocketTCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
