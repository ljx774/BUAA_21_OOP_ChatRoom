package learnSocket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 8888);
            InputStream is = s.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            System.out.println("服务器端地址: " + s.getInetAddress());
            System.out.println("服务器端端口: " + s.getPort());
            System.out.println("接收到的信息为: ");
            System.out.println(dis.readUTF());
            System.out.println(dis.readUTF());
            dis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
