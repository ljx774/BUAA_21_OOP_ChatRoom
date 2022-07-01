package learnSocket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(8888);
            while (true) {
                Socket s1 = s.accept();
                OutputStream os = s1.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF("客户端地址信息: " + s1.getInetAddress() +
                        "\t客户端通信端口号: " + s1.getPort());
                dos.writeUTF("Good Bye!");
                dos.close();
                s1.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
