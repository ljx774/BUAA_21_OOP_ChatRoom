package Server;

import java.io.*;

public class ServerShell extends Thread{
    private final BufferedReader commandReader = new BufferedReader(
            new InputStreamReader(System.in));
    private ServerWriter serverWriter;

    public ServerShell(ServerWriter serverWriter) {
        this.serverWriter = serverWriter;
        init();
    }

    private void init() {
        System.out.println("欢迎使用ChatRoomLjx 0.1!");
        try {
            FileWriter writer = new FileWriter("src/Login.csv");
            BufferedWriter br = new BufferedWriter(writer);
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            String command;
            ServerCmdExec executor = new ServerCmdExec(serverWriter);
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
