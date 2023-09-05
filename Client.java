import java.io. *;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Client{
    public static void main(String[] args) throws Exception {
            Socket s = new Socket("127.0.0.1", 53433);
            SendThread send = new SendThread(s);
            ReceiveThread rec = new ReceiveThread(s);
            send.start();
            rec.start();
            send.join();
            s.close();
            System.exit(0);
    }
}