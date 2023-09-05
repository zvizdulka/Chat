import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class ClientThread extends Thread {
    Socket socket;
    StringBuffer name;
    PrintWriter out;
    BufferedReader in;

    public ClientThread(Socket s, String name) throws Exception {
        socket = s;
        this.name = new StringBuffer(name);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            String fromServer;
            while ((fromServer = in.readLine()) != null) {
                Scanner m = new Scanner(fromServer);
                String com = m.next();
                if (com.equals("@quit")) {
                    socket.close();
                    break;
                }
                else {
                    if (com.equals("@senduser")) {
                        String user = m.next();
                        String message = new String("@" + name + " " + m.nextLine());
                        Server.send(message, name.toString(), user);
                    }
                    else {
                        if (com.equals("@name")) {
                            String str = new String(m.next());
                            if(!Server.haveName(str)) {
                                name = new StringBuffer(str);
                            }
                            else{
                                Server.send("Name taken.", name.toString(), name.toString());
                            }
                        }
                        else {
                            if(com.equals("@password")){
                                StringBuffer pas = new StringBuffer(m.nextLine());
                                pas.deleteCharAt(0);
                                Server.Admin(pas.toString(), this);
                            }
                            else {
                                if(com.equals("@block")){
                                    StringBuffer str = new StringBuffer(m.nextLine());
                                    str.deleteCharAt(0);
                                    Server.block(str.toString(), name.toString());
                                }
                                else {
                                    Server.sendAll("@" + name + " " + fromServer, name.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendThread(String message){
        out.println(message);
    }

    public String getNameUser(){
        return name.toString();
    }
}