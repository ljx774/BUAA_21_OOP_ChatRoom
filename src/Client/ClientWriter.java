package Client;

import java.io.*;

public class ClientWriter extends Thread{
    private DataOutputStream dos;

    public ClientWriter(DataOutputStream dos) {
        this.dos = dos;
    }

    public void run() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String info;
        System.out.println("欢迎使用ChatRoomLjx for Client!");
        System.out.println("版本号: 0.1");
        System.out.println("ChatRoomShell @unknown > ");
        try {
            while (true) {
                info = br.readLine();
                dos.writeUTF(info);
                if (info.equals("quit")) {
                    System.out.println("----------Bye!----------");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
