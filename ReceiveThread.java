import java.io. *;
import java.net.*;
import java.lang.*;

public class ReceiveThread extends Thread {
    private Socket s;
    BufferedReader in;

    public ReceiveThread(Socket socket) throws IOException {
        s = socket;
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public void run() {
        try {
                String fromServer;
                while ((fromServer = in.readLine()) != null) {
                    System.out.println(fromServer);
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}