package Sockets;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class SocketUDP {
    int PortEnvio;
    int PortRecib;
    public SocketUDP(int env, int rec) {
        this.PortEnvio = env;
        this.PortRecib = rec;
    }

    public int getEnv() {
        return PortEnvio;
    }

    public void setEnv(int env) {
        this.PortEnvio = env;
    }

    public int getRec() {
        return PortRecib;
    }

    public void setRec(int rec) {
        this.PortRecib = rec;
    }

    public void enviarMissatge (String IpDesti) throws IOException {
        Scanner sc = new Scanner(System.in);
        String message;

        do {
            System.out.println("Dame el mensaje");
            message = sc.nextLine();
            byte[] missatge = message.getBytes();
            InetAddress adrecaDesti = InetAddress.getByName(IpDesti);
            DatagramPacket packet = new DatagramPacket(missatge, missatge.length, adrecaDesti, PortEnvio);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
        } while (message!="/");

    }


    public String rebreMissatge () throws IOException {

        String mensaje;

        byte[] missatge = new byte[1024];
        DatagramPacket packet = new DatagramPacket(missatge, missatge.length);
        DatagramSocket socket = new DatagramSocket(PortEnvio);

        socket.setSoTimeout(10000);

        socket.receive(packet);
        mensaje = new String(packet.getData(), packet.getOffset(), packet.getLength());

        socket.close();


        return mensaje;

    }

    public void enviarMulticast (String IpDesti, int SocketMulticast ) throws IOException {
        Scanner sc = new Scanner(System.in);
        String message;
        MulticastSocket socket;
        socket = new MulticastSocket();


        do {
            System.out.println("Dame el mensaje");
            message = sc.nextLine();
            byte[] missatge = message.getBytes();

            InetAddress adrecaDesti = InetAddress.getByName(IpDesti);

            DatagramPacket packet = new DatagramPacket(missatge, missatge.length, adrecaDesti, SocketMulticast);

            socket.send(packet);


        } while (!message.equals("/"));

        socket.close();

    }

    public String recibirMulticast (int SocketMulticast, String IpMulticast) throws IOException {

        String mensaje;

        MulticastSocket socket = new MulticastSocket(SocketMulticast);

        NetworkInterface net = NetworkInterface.getByName("bge0");

        InetSocketAddress group = new InetSocketAddress(InetAddress.getByName(IpMulticast), 5555);

        socket.joinGroup(group,net);

        byte[] missatge = new byte[1024];
        DatagramPacket packet = new DatagramPacket(missatge, missatge.length);

        socket.receive(packet);
        mensaje = new String(packet.getData(), packet.getOffset(), packet.getLength());

        socket.leaveGroup(group,net);
        socket.close();

        return mensaje;

    }
}

