package Models;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.*;
import Constants.StringConstants;

public class ProxyThread extends Thread {
    private Socket clientSocket;

    public ProxyThread(Socket clientSocket) {
        Init(clientSocket);
    }
    private void Init(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream in = clientSocket.getInputStream();
            byte[] headerBytes = new byte[16]; // 8 bytes flag + 8 bytes length
            in.read(headerBytes);

            if (isValidRequest(headerBytes)) {
                int length = ByteBuffer.wrap(headerBytes, 8, 8).getInt();
                byte[] urlBytes = new byte[length];
                in.read(urlBytes);

                String url = new String(urlBytes, StandardCharsets.UTF_8);
                System.out.println("Received URL: " + url);
                System.out.println(url);

                // Fetch webpage and send to client
                String webpage = fetchWebpage(url);
                OutputStream out = clientSocket.getOutputStream();
                out.write(webpage.getBytes(StandardCharsets.UTF_8));
                out.flush();
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Shit not working");
        }
    }

    private boolean isValidRequest(byte[] headerBytes) {
        byte[] flag = { (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE, (byte) 0xFE };
        for (int i = 0; i < 8; i++) {
            if (headerBytes[i] != flag[i]) {
                return false;
            }
        }
        return true;
    }

    private String fetchWebpage(String url) {

        // For simplicity, we'll just return dummy HTML content for now
        if(!url.equals(StringConstants.Empty)) {

            // Web page returning logic goes over here
            return url + " Shit working";
        }
        return " ";
    }
}
