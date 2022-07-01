package learnSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TestServer {
    public static void main(String[] args) {
        try {
            //连接服务器
            ServerSocket ss = new ServerSocket(8888);
            Socket s = ss.accept();
            //发送流
            OutputStream os = s.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            //接收流
            InputStream is = s.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            //读用户写的信息
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String info;
            while (true) {
                info = dis.readUTF();
                System.out.println("对方说: " + info);
                if(info.equals("bye")) {
                    break;
                }
                info = br.readLine();
                dos.writeUTF(info);
                if(info.equals("bye")) {
                    break;
                }
            }
            dis.close();
            dos.close();
            s.close();
            ss.close();
        } catch (SocketException e) {
            System.out.println("网络连接异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
