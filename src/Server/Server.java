package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerShell shell = new ServerShell();
        shell.start();
        try {
            ServerSocket serverSocket = new ServerSocket(11451);
            ServerWriter serverWriter = new ServerWriter(shell.users);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("新用户连接成功！端口号：" + socket.getPort());
                System.out.print("ChatRoomShell @Server > ");

                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);

                new ServerReader(dis, socket.getPort(), serverWriter).start();
                serverWriter.addDos(dos, socket.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
