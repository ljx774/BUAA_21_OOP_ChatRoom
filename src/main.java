import Utils.CommandUtils;

public class main {
    static CommandUtils commandUtils = new CommandUtils();
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println(commandUtils.stringToInt("1234"));
    }
}
