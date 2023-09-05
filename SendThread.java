import java.io. *;
import java.net.*;
import java.util.*;
import java.lang.*;

public class SendThread extends Thread {
    private Socket s;
    private BufferedReader inFromUser;
    private PrintWriter out;

    public SendThread(Socket socket) throws IOException {
        s = socket;
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(s.getOutputStream(), true);
    }

    public void run() {
        try {
            while (true) {
                String sentence = inFromUser.readLine();
                Scanner in = new Scanner(sentence);
                String com = in.next();
                if (com.equals("@quit")) {
                    out.println("@quit");
                    break;
                }
                else{
                    out.println(sentence);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}