package learnSocket;

import java.io.DataInputStream;
import java.io.IOException;

public class MyServerReader extends Thread{
    private DataInputStream dis;
    public MyServerReader (DataInputStream dis) {
        this.dis = dis;
    }
    public void run() {
        String info;
        try {
            while (true) {
                info = dis.readUTF();
                System.out.println("对方说: " + info);
                if (info.equals("bye")) {
                    System.out.println("对方下线，程序退出！");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
