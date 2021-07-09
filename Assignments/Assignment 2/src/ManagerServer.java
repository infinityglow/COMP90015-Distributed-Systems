import javax.net.ServerSocketFactory;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class ManagerServer {

    // Declare the port number
    private int port;
    // server socket
    private ServerSocket server;
    private static HashMap<String, PrintWriter> all_writers = new HashMap<>();

    // two overloaded constructors

    public ManagerServer(int port) {
        this.port = port;
    }

    public ManagerServer() {
        this.port = 1234;
    }

    public static String[] getAllUsers(){
        String[] allUsers = new String[all_writers.size()];
        int i = 0;
        for (String user: all_writers.keySet()){
            allUsers[i++] = user;
        }
        return allUsers;
    }

    public void accept(){
        try {
            // Wait for connections.
            while(true)
            {
                Socket client = server.accept();

                // Start a new thread for a connection
                Thread t = new Thread(() -> handleText(client));
                t.start();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void connect(){
        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try
        {
            this.server = factory.createServerSocket(port);
        }
        catch (SocketException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        System.exit(1);
    }

    public void unicast(String user, String msg){
        if (!all_writers.containsKey(user)){
            return;
        }
        PrintWriter writer = all_writers.get(user);
        writer.println(msg);

        if (msg.equals("qcj0239nru3k3r9ks")){
            all_writers.remove(user);
            show_userlist();
            broadcast("ufu32ry39f3849kd2");
        }
    }

    public void broadcast(String msg){
        if (msg.startsWith("ufu32ry39f3849kd2")){
            String userlist = "";
            for (String user: getAllUsers()){
                userlist += user + " ";
            }

            for (PrintWriter writer: all_writers.values()){
                writer.println(msg + userlist);
            }
        }

        else {
            for (PrintWriter writer: all_writers.values()){
                writer.println(msg);
            }

            if (msg.equals("qcj0239nru3k3r9ks")){
                all_writers.clear();
                show_userlist();
            }
        }
    }

    private void show_userlist(){
        String userlist = "";
        for (String username: all_writers.keySet()){
            userlist += username + "\n";
        }
        ManagerGUI.userlist.setText(userlist);
    }

    private void handleText(Socket socketclient){

        try(Socket client = socketclient){
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("cnr9433h254kh34k2");

            String name = "";
            while (true){
                name = in.readLine();
                if (name != null) {
                    break;
                }
            }

            while (true){
                String msg = in.readLine();

                if (msg != null){
                    // chat window message
                    if (msg.startsWith("m3oimru3289mr2384")){
                        String processed_msg = msg.substring(17);
                        ManagerGUI.chatpanel.setText(ManagerGUI.chatpanel.getText() + processed_msg + "\n");
                        broadcast(msg);
                    }
                    // message box message
                    else if (msg.startsWith("c394c3j2cm2390sf9")){
                        String processed_msg = msg.substring(17);
                        ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + processed_msg + "\n");
                        broadcast(msg);
                    }
                    // client joining message
                    else if (msg.startsWith("jx82cr89sfd4jc392")){
                        String identity = msg.substring(17);
                        if (all_writers.containsKey(identity)){
                            int result = 2;
                            out.println("jx82cr89sfd4jc392" + result);
                            continue;
                        }

                        int result = JOptionPane.showConfirmDialog(ManagerGUI.jf, identity + " asks your approval to join in.", "Identity Checking", JOptionPane.YES_NO_OPTION);

                        if (result == 0){
                            all_writers.put(identity, out);
                            PrintWriter writer = all_writers.get(identity);
                            writer.println("jx82cr89sfd4jc392" + result + ManagerGUI.imagePath);
                            show_userlist();
                            broadcast("ufu32ry39f3849kd2");
                        }
                        else if (result == 1){
                            out.println("jx82cr89sfd4jc392" + result);
                        }
                    }
                    // client leaving message
                    else if (msg.startsWith("v23t89rvu348908so")){
                        String identity = msg.substring(17);
                        all_writers.remove(identity);
                        show_userlist();
                        broadcast("ufu32ry39f3849kd2");
                        return;
                    }
                }
            }
        }
         catch (NullPointerException | SocketException e) {
            e.printStackTrace();
             System.out.println("disconnect");
        }
        catch (IOException e){
            System.out.println("disconnect");
        }
    }
}
