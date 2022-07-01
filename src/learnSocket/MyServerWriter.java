package learnSocket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyServerWriter extends Thread{
    private DataOutputStream dos;
    public MyServerWriter (DataOutputStream dos) {
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
                if(info.equals("bye")){
                    System.out.println("下线，程序退出！");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
