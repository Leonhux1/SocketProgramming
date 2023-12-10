package Server;
import Constants.PortConstants;
import Models.ProxyThread;

import javax.sound.sampled.Port;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        try {
            PortConstants constants = new PortConstants();
            ServerSocket serverSocket = new ServerSocket(constants.SERVER_SIDE_PORT_NUMBER);
            System.out.println("Proxy Agent is listening on port " + constants.SERVER_SIDE_PORT_NUMBER);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ProxyThread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) throws IOException {
//        // don't need to specify a hostname, it will be the current machine
//        ServerSocket ss = new ServerSocket(7777);
//        System.out.println("ServerSocket awaiting connections...");
//        Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
//        System.out.println("Connection from " + socket + "!");
//
//        // get the input stream from the connected socket
//        InputStream inputStream = socket.getInputStream();
//        // create a DataInputStream so we can read data from it.
//        DataInputStream dataInputStream = new DataInputStream(inputStream);
//
//        // read the message from the socket
//        String message = dataInputStream.readUTF();
//        System.out.println("The message sent from the socket was: " + message);
//
//        System.out.println("Closing sockets.");
//        ss.close();
//        socket.close();
//    }
}