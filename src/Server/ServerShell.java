package Server;

import Utils.CommandUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ServerShell extends Thread{
    protected ArrayList<User> users = new ArrayList<>();
    private final CommandUtils commandUtils = new CommandUtils();
    private final BufferedReader commandReader = new BufferedReader(
            new InputStreamReader(System.in));

    public ServerShell() {
        System.out.println("欢迎使用ChatRoomLjx 0.1!");
    }

    public void run(){
        try {
            String command;
            ServerCmdExec executor = new ServerCmdExec(users);
            while (true) {
                System.out.print("ChatRoomShell @Server > ");
                getUsers();
                command = commandReader.readLine();
                executor.execute(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUsers () throws IOException {
        users.clear();
        File userFormat = new File("src/User.csv");
        BufferedReader userReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(userFormat), StandardCharsets.UTF_8));
        String userInfo;
        ArrayList<String> params = new ArrayList<>();
        while ((userInfo = userReader.readLine()) != null) {
            commandUtils.divide(userInfo, params);
            users.add(new User(params.get(0), params.get(1), params.get(2)));
        }
    }
}
