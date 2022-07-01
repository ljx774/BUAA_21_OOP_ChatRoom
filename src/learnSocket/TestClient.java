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

            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String info;

            while (true) {
                info = br.readLine();
                dos.writeUTF(info);
                if(info.equals("bye"))
                    break;

                info = dis.readUTF();
                System.out.println("对方说: " + info);
                if (info.equals("bye"))
                    break;
            }

            dis.close();
            dos.close();
            s.close();
        } catch (SocketException e) {
            System.out.println("网络连接异常");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
