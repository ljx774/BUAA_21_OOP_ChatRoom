package learnSocket;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class TestClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8888);

            InputStream is = s.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            OutputStream os = s.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            new MyServerReader(dis).start();
            new MyServerWriter(dos).start();
        } catch (SocketException e) {
            System.out.println("网络连接异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
