package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ServerWriter extends Thread{
    private ArrayList<MyDOS> doss = new ArrayList<>();
    private String[] toSend = new String[50];
    private ArrayList<User> users = new ArrayList<>();

    public ServerWriter(ArrayList<User> users) {
        this.users = users;
    }

    public void addDos(DataOutputStream dos, int port) {
        doss.add(new MyDOS(dos, "unknown", port));
    }

    public void getNewMessage(String name, String message){
        for (MyDOS myDOS : doss) {
            if (myDOS.name.equals(name)) {
                myDOS.sendMessage(message);
                return;
            }
        }
    }

    public void getNewMessage(int port, String message){
        for (MyDOS myDOS : doss) {
            if (myDOS.port == port) {
                myDOS.sendMessage(message);
                return;
            }
        }
    }

    public String login(String uid, String passWord, int port){
        MyDOS tmpDos = null;
        for (MyDOS myDOS : doss) {
            if (myDOS.port == port) {
                tmpDos = myDOS;
            }
        }
        assert tmpDos != null;
        boolean loginFlag = false;
        for (User user : users) {
            if (uid.equals(user.uid)) {
                loginFlag = true;
                if (passWord.equals(user.password)) {
                    tmpDos.name = user.name;
                    tmpDos.sendMessage("系统提示: 登陆成功, 您的昵称为" + tmpDos.name);
                    System.out.println("端口号为" + port + "的用户以账号" + user.uid + "成功登录");
                    System.out.print("ChatRoomShell @Server > ");
                    return tmpDos.name;
                }
                else {
                    tmpDos.sendMessage("系统提示: 密码错误, 请重新尝试!");
                }
            }
        }
        if (!loginFlag) {
            tmpDos.sendMessage("系统提示: 账号不存在!");
        }
        return "unknown";
    }

    public void run() {
        try {
            while (true) {
                for (int i = 0; i < doss.size(); i++) {
                    doss.get(i).dos.writeUTF(toSend[i]);
                    toSend[i] = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyDOS {
    DataOutputStream dos;
    String name;
    int port;

    public MyDOS (DataOutputStream dos, String name, int port) {
        this.dos = dos;
        this.name = name;
        this.port = port;
    }

    public void sendMessage(String message){
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
