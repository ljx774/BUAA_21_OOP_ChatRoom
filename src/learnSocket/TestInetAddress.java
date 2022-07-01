package learnSocket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestInetAddress {
    public static void main(String[] args) {
        String url = "www.baidu.com";
        InetAddress ia1 = null;
        try {
            ia1 = InetAddress.getByName(url);
        } catch (UnknownHostException e) {
            System.err.println(e);
        }
        if(ia1 != null) {
            System.out.println("The IP is " + ia1.getHostAddress());
            System.out.println("The host name is " + ia1.getHostName());
        }
        else {
            System.out.println("Can't access " + url);
        }
    }
}
