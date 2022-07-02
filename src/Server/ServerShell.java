package Server;

import Utils.CommandUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ServerShell extends Thread{
    private final CommandUtils commandUtils = new CommandUtils();
    private final BufferedReader commandReader = new BufferedReader(
            new InputStreamReader(System.in));

    public ServerShell() {
        System.out.println("欢迎使用ChatRoomLjx 0.1!");
    }

    public void run(){
        try {
            String command;
            ServerCmdExec executor = new ServerCmdExec();
            while (true) {
                System.out.print("ChatRoomShell @Server > ");
                command = commandReader.readLine();
                executor.execute(command);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
