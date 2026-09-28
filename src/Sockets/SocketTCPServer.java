package Sockets;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketTCPServer {
    int PortEnvio;
    int PortRecib;

    public SocketTCPServer(int env, int rec) {
        this.PortEnvio = env;
        this.PortRecib = rec;
    }


    public void rebreMissatgeTCPServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PortRecib);

        Socket cliSocket = serverSocket.accept();

        String text;

        Thread lector = new Thread(() -> {
            Scanner reader = null;
            try {
                reader = new Scanner(cliSocket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (reader.hasNextLine()) {
                String linea = reader.nextLine();
                System.out.println("El cliente dice " + linea);
                System.out.flush();
            }
        });
        lector.start();

        ////////////////////////////////////////////////////////////

        Scanner sc = new Scanner(System.in);

        try {

            PrintStream writer = new PrintStream(cliSocket.getOutputStream());
            do {
                System.out.println("Dame el mensaje");
                text = sc.nextLine();

                writer.println(text);
                writer.flush();

            } while (!Objects.equals(text, "/"));

            writer.flush();
            cliSocket.close();

        } catch (IOException ex) {
            Logger.getLogger(SocketTCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}

