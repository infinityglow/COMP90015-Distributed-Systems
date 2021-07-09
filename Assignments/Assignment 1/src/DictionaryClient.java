import java.io.*;
import java.net.*;


public class DictionaryClient {

    // IP and port
    private String hostname;
    private int port;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public DictionaryClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String read(){
        String msg = "";
        try {
            msg = in.readLine();
            while (in.ready()){
                in.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return msg;
    }

    public String readAll(){
        String msg = "";
        try {
            while(true){
                msg += in.readLine() + "\n";
                if (!in.ready()){
                    break;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return msg;
    }

    public void write(String msg){
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean status(){
        try {
            System.out.print("");
            socket.sendUrgentData(0xFF);
            return !socket.isClosed();
        } catch (Exception e){
            e.printStackTrace();
            socket = null;
            return false;
        }
    }

    public void disconnect(){
        try {
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean connect(){
        try {
            this.socket = new Socket(hostname, port);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
            this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), "UTF-8"));
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
