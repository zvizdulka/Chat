import java.net.*;
import java.util.*;
import java.lang.*;

public class Server {
    private static Vector<ClientThread> list;
    private static Vector<ClientThread> blackList;
    private ServerSocket serverSocket;
    private static Vector<ClientThread> admin;
    private static String password = new String("1111");
    public Server(){
        try {
            list = new Vector<ClientThread>();
            serverSocket = new ServerSocket(53433);
            blackList = new Vector<ClientThread>();
            admin = new Vector<ClientThread>();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sendAll(String message, String sender){
        if(!isBlock(sender)) {
            for (int i = 0; i < list.size(); i++) {
                if (!(list.elementAt(i).getNameUser().equals(sender))) {
                    list.elementAt(i).sendThread(message);
                }
            }
        }
        else{
            getClientThread(sender).sendThread("you are not a Jedi. You are on the dark side.");
        }
    }

    public static void send(String message, String sender, String name){
        if(!isBlock(sender)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.elementAt(i).getNameUser().equals(name)) {
                    list.elementAt(i).sendThread(message);
                }
            }
        }
        else{
            getClientThread(sender).sendThread("you are not a Jedi. You are on the dark side.");
        }
    }

    public void run() {
        try {
            int i = 0;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientThread cliThread = new ClientThread(clientSocket, "noname" + i);
                cliThread.start();
                list.add(cliThread);
                i++;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ClientThread getClientThread(String name){
        for (int i = 0; i < list.size(); i++) {
            if (list.elementAt(i).getNameUser().equals(name)) {
                return list.elementAt(i);
            }
        }
        return null;
    }

    static public void Admin(String pas, ClientThread s){
        if(password.equals(pas) && !isBlock(s.getNameUser())){
            admin.add(s);
            s.sendThread("you  are YODA");
        }
    }

    static public void block(String nameBlock, String sender){
        if(isAdmin(sender) && !isAdmin(nameBlock) && !isBlock(nameBlock)){
            blackList.add(getClientThread(nameBlock));
        }
    }

    public static boolean isBlock(String name){
        for(int i = 0; i < blackList.size(); i++) {
            if (blackList.elementAt(i).getNameUser().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAdmin(String name){
        for(int i = 0; i < admin.size(); i++) {
            if (admin.elementAt(i).getNameUser().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static boolean haveName(String name){
        for(int i = 0; i < list.size(); i++) {
            if (list.elementAt(i).getNameUser().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}