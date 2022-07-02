package Server;

import Utils.CommandUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ServerWriter extends Thread{
    private ArrayList<MyDos> doss = new ArrayList<>();
    private String[] toSend = new String[50];
    private ArrayList<User> users = new ArrayList<>();
    private final CommandUtils commandUtils = new CommandUtils();

    public ServerWriter() {
        updateUsers();
    }

    public void addDos(DataOutputStream dos, int port) {
        doss.add(new MyDos(dos, "unknown", port));
    }

    public void getNewMessage(String name, String message){
        for (MyDos myDos : doss) {
            if (myDos.name.equals(name)) {
                myDos.sendMessage(message);
                return;
            }
        }
    }

    public void getNewMessage(int port, String message){
        for (MyDos myDos : doss) {
            if (myDos.port == port) {
                myDos.sendMessage(message);
                return;
            }
        }
    }

    public String login(String uid, String passWord, int port){
        MyDos tmpDos = null;
        for (MyDos myDos : doss) {
            if (myDos.port == port) {
                tmpDos = myDos;
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
                    addLogin(user);
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

    public void register(String uid, String password, String name, int port) {
        MyDos tmpDos = null;
        for (MyDos myDos : doss) {
            if (myDos.port == port) {
                tmpDos = myDos;
            }
        }
        assert tmpDos != null;
        updateUsers();
        boolean isExist = false, isSameName = false;
        for (User user : users) {
            if (user.uid.equals(uid)) {
                isExist = true;
            }
            if (user.name.equals(name)) {
                isSameName = true;
            }
        }
        if (isExist) {
            tmpDos.sendMessage("系统提示: 该账号已存在! 请换一个账号注册!");
            return;
        }
        if (isSameName) {
            tmpDos.sendMessage("系统提示: 该昵称已存在! 请换一个昵称注册!");
            return;
        }
        try {
            FileWriter writer = new FileWriter("src/User.csv", true);
            BufferedWriter br = new BufferedWriter(writer);
            br.newLine();
            br.write(uid + ", " + password + ", " + name);
            br.flush();
            updateUsers();
            tmpDos.sendMessage("系统提示: 注册成功, 请登录吧！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(int port) {
        MyDos tmpDos = null;
        for (MyDos myDos : doss) {
            if (myDos.port == port) {
                tmpDos = myDos;
            }
        }
        assert tmpDos != null;
        removeLogin(tmpDos.name);
        tmpDos.name = "unknown";
        tmpDos.sendMessage("系统提示: 退出登录成功!");
    }

    public void updateUsers (){
        users.clear();
        try {
            File userFormat = new File("src/User.csv");
            BufferedReader userReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(userFormat), StandardCharsets.UTF_8));
            String userInfo;
            ArrayList<String> params = new ArrayList<>();
            while ((userInfo = userReader.readLine()) != null) {
                commandUtils.divide(userInfo, params);
                users.add(new User(params.get(0), params.get(1), params.get(2)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addLogin(User user) {
        try {
            FileWriter writer = new FileWriter("src/Login.csv", true);
            BufferedWriter br = new BufferedWriter(writer);
            br.newLine();
            String newLogin = user.uid + ", " + user.password + ", " + user.name;
            br.write(newLogin);
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeLogin(String name) {
        ArrayList<User> tmpLoginList = new ArrayList<>();
        try {
            File loginNow = new File("src/Login.csv");
            BufferedReader userReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(loginNow), StandardCharsets.UTF_8));
            String loginInfo;
            ArrayList<String> params = new ArrayList<>();
            while ((loginInfo = userReader.readLine()) != null) {
                if(loginInfo.length() > 3) {
                    commandUtils.divide(loginInfo, params);
                    if (!params.get(2).equals(name))
                        tmpLoginList.add(new User(params.get(0), params.get(1), params.get(2)));
                }
            }

            FileWriter writer = new FileWriter("src/Login.csv");
            BufferedWriter br = new BufferedWriter(writer);
            for (User user : tmpLoginList) {
                br.newLine();
                br.write(user.uid + ", " + user.password + ", " + user.name);
            }
            br.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
