package Server;

import java.io.DataOutputStream;
import java.io.IOException;

public class MyDos {
    DataOutputStream dos;
    String name;
    int port;

    public MyDos (DataOutputStream dos, String name, int port) {
        this.dos = dos;
        this.name = name;
        this.port = port;
    }

    public void sendMessage(String message){
        try {
            dos.writeUTF(message);
            dos.writeUTF("ChatRoomShell @" + name + " > ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
