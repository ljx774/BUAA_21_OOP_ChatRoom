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
        try {
            while (true) {
                info = br.readLine();
                dos.writeUTF(info);
                if (info.endsWith("bye")) {
                    System.out.println("下线！");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
