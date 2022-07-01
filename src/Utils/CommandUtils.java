package Utils;

import java.util.ArrayList;

public class CommandUtils {
    public void divide(String command, ArrayList<String> params) {
        params.clear();
        String param = null;
        for (int i = 0; i < command.length(); i++) {
            if(command.charAt(i) != ',' && command.charAt(i) != ' ') {
                if(param == null)
                    param = "" + command.charAt(i);
                else param += command.charAt(i);
            }
            else {
                if (param != null) {
                    params.add(param);
                    param = null;
                }
            }
        }
        if(param != null) {
            params.add(param);
        }
    }

    public int stringToInt(String str) {
        int ret = 0;
        for (int i = 0; i < str.length(); i++) {
            ret *= 10;
            ret += str.charAt(i) - '0';
        }
        return ret;
    }
}
