package Server;

import Utils.CommandUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ServerReader extends Thread{
    private final DataInputStream dis;
    private CommandUtils commandUtils = new CommandUtils();
    private ServerWriter writer;
    private int port;
    private String name;

    public ServerReader(DataInputStream dis, int port, ServerWriter writer) {
        this.dis = dis;
        this.port = port;
        this.writer = writer;
        this.name = "unknown";
    }

    public void run() {
        String info;
        try {
            while (true) {
                info = dis.readUTF();
                execute(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute(String info) {
        ArrayList<String> params = new ArrayList<>();
        String send = null;
        String message = name + "说:";
        commandUtils.divide(info, params);
        // 客户端尝试向用户发私信
        if (params.get(0).startsWith("@")) {
            if(this.name.equals("unknown")) {
                message = "您还没有登录，请先登录！";
                writer.getNewMessage(port, message);
                //writer.getNewMessage(port, "ChatRoomShell @" + name + " > ");
                return;
            }
            for (int i = 1; i < params.get(0).length(); i++) {
                if(send == null) {
                    send = "" + params.get(0).charAt(i);
                }
                else send += params.get(0).charAt(i);
            }
            for (int i = 1; i < params.size(); i++) {
                message += " " + params.get(i);
            }
            System.out.println("端口号为" + port + "的用户尝试向用户" + send + "发送信息：" + message);
            System.out.print("ChatRoomShell @Server > ");
            writer.getNewMessage(send, message);
        }
        // 客户端尝试登录
        else if (params.get(0).equals("login")) {
            if (params.size() != 3) {
                message = "参数数量错误！请按照“login [uid] [password]”的格式输入";
                writer.getNewMessage(port, message);
                //writer.getNewMessage(port, "ChatRoomShell @" + name + " > ");
                return;
            }
            this.name = writer.login(params.get(1), params.get(2), port);
        }
        // 客户端尝试注册
        else if (params.get(0).equals("register")) {
            if (params.size() != 4) {
                message = "参数数量错误！请按照“register [uid] [password] [name]”的格式输入";
                writer.getNewMessage(port, message);
                //writer.getNewMessage(port, "ChatRoomShell @" + name + " > ");
                return;
            }
            writer.register(params.get(1), params.get(2), params.get(3), port);
        }
        //客户端尝试登出
        else if (params.get(0).equals("logout") && params.size() == 1) {
            if (this.name.equals("unknown")){
                message = "您还没有登录，请先登录！";
                writer.getNewMessage(port, message);
                return;
            }
            this.name = "unknown";
            writer.logout(port);
        }
        else {
            message = "未知指令, 请重新输入";
            writer.getNewMessage(port, message);
            //writer.getNewMessage(port, "ChatRoomShell @" + name + " > ");
        }
    }
}
