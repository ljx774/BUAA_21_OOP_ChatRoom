package Client;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientReader extends Thread{
    private DataInputStream dis;

    public ClientReader (DataInputStream dis) {
        this.dis = dis;
    }

    public void run () {
        String info;
        try {
            while (true) {
                info = dis.readUTF();
                System.out.println(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
