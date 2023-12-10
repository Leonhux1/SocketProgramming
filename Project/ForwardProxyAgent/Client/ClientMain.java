package Client;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import Constants.PortConstants;
import Constants.UrlConstants;

public class ClientMain {
    public static void main(String[] args) {
        try {
            String serverIP = "127.0.0.1";
            PortConstants constants = new PortConstants();

            Socket socket = new Socket(serverIP, constants.SERVER_SIDE_PORT_NUMBER);
            OutputStream out = socket.getOutputStream();

            byte[] flag = { (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE };
            byte[] urlBytes = UrlConstants.MIT_URL.getBytes();
            byte[] lengthBytes = ByteBuffer.allocate(8).putInt(urlBytes.length).array();

            out.write(flag);
            out.write(lengthBytes);
            out.write(urlBytes);
            out.flush();

            // Read and print the response from the server (webpage content)
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The proxy forwarding agent is down");
        }
    }

}
