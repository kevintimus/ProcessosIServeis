package Sockets;

import Algoritmes.Metodes;

import javax.crypto.*;
import java.io.IOException;
import java.net.MulticastSocket;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.*;

public class MainSockets {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner sc = new Scanner(System.in);
        int opcio;


        do {
            System.out.println("\n========================================");
            System.out.println("     Algorismes criptogràfics");
            System.out.println("========================================");
            System.out.println("1 Cliente");
            System.out.println("2 Server");
            System.out.println("3 Multicast Server");
            System.out.println("4 Multicast Cliente");
            System.out.println("5 Recibir 1 mensaje TCP server");
            System.out.println("6 Enviar 1 mensaje TCP cliente");
            System.out.println("7 ");
            System.out.println("8 ");
            System.out.println("9 ");
            System.out.println("10 ");
            System.out.println("11 ");
            System.out.println("12");
            System.out.println("13");
            System.out.println("0. Sortir");
            System.out.println("========================================");
            System.out.print("Tria una opció: ");

            SocketUDP puertos = new SocketUDP(6767, 6969);

            SocketTCPClient puertosTCPClient = new SocketTCPClient(1234, 2345);
            SocketTCPServer puertosTCPServer = new SocketTCPServer(2345, 1234);


            String IpMulticast = "230.0.0.1";
            int socketMulticast = 5555;

            System.out.println("Puerto origen " + puertos.getRec());
            System.out.println("Puerto destino " + puertos.getEnv());
            System.out.println("Multicast IP " + IpMulticast);
            System.out.println("Multicast Socket " + socketMulticast);

            opcio = sc.nextInt();
            sc.nextLine();

            switch (opcio) {
                case 1:

                    puertos.enviarMissatge("localhost");
                    System.out.println("Se ha enviado");
                    break;

                case 2:

                    String mensaje;
                    do {
                        mensaje = puertos.rebreMissatge();
                        if (!mensaje.equals("/")) {
                            System.out.println(mensaje);
                            System.out.println("Se ha recibido");
                        }
                    } while (!Objects.equals(mensaje, "/"));
                    break;

                case 3:
                    puertos.enviarMulticast(IpMulticast, socketMulticast);
                    System.out.println("Se ha enviado");
                    break;

                case 4:
                    String mensajeMulticast;
                    do {
                        mensajeMulticast = puertos.recibirMulticast(socketMulticast, IpMulticast);
                        if (!mensajeMulticast.equals("/")) {
                            System.out.println(mensajeMulticast);
                            System.out.println("Se ha recibido");
                        }
                    } while (!Objects.equals(mensajeMulticast, "/"));
                    break;


                case 5:
                    puertosTCPServer.rebreMissatgeTCPServer();
                    System.out.println("Se ha recibido");

                   break;

                case 6:
                    puertosTCPClient.enviarMissatgeTCPClient("localhost");
                    System.out.println("Se ha enviado");
                    break;

                case 7:

                    break;
                case 8:

                    break;
                case 9:

                    break;
                case 10:

                    break;
                case 11:

                    break;
                case 12:

                    break;
                case 13:

                    break;
                case 14:

                    break;

                default:
                    System.out.println("Opció no vàlida!");
            }
        } while (opcio != 0);
        sc.close();
    }
}