package Server;

import Utils.CommandUtils;

import java.util.ArrayList;

public class ServerCmdExec{
    private final ArrayList<User> users;
    private String command;
    private final ArrayList<String> params = new ArrayList<>();
    private final CommandUtils commandUtils = new CommandUtils();

    public ServerCmdExec(ArrayList<User> users){
        this.users = users;
    }

    public void execute(String command) {
        this.command = command;
        commandUtils.divide(command, params);
        switch (params.get(0)) {
            case "quit" :
                System.out.println("------Bye!------");
                System.exit(0);
                break;
            case "ls" :
                ls();
                break;
            case "call" :
                call();
                break;
            default:
                System.out.println("未知指令, 请重新输入");
                break;
        }
    }

    private void ls() {
        switch (params.get(1)) {
            case "users" :
                System.out.println("共检索到" + users.size() + "条结果");
                System.out.println("编号\t\t账号\t\t密码\t\t昵称");
                for (int i = 0; i < users.size(); i++) {
                    System.out.println(i + 1 + "\t\t" + users.get(i).uid + "\t" +
                            users.get(i).password + "\t" + users.get(i).name);
                }
                break;
            case "login" :

                break;
            default:
                System.out.println("未知指令，请重新输入");
        }
    }

    private void call() {

    }
}
