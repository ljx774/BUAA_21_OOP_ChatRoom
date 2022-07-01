package learnSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServer {
    public static void main(String[] args) {
        try {
            //连接服务器
            ServerSocket ss = new ServerSocket(8888);
            ExecutorService es = Executors.newFixedThreadPool(50);
            while (true) {
                Socket s = ss.accept();
                //发送流
                OutputStream os = s.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                //接收流
                InputStream is = s.getInputStream();
                DataInputStream dis = new DataInputStream(is);

                new MyServerReader(dis).start();
                new MyServerWriter(dos).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
