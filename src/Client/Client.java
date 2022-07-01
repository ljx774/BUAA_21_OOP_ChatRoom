package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 11451);

            InputStream is = socket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            OutputStream os = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            new ClientReader(dis).start();
            new ClientWriter(dos).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
