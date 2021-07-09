import org.json.*;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import javax.net.ServerSocketFactory;

public class DictionaryServer {

    private static HashMap<String, String> Dictionary = new HashMap<String, String>();

    // Declare the port number
    private static int port;
    // Identifies the user number connected
    private static int counter;

    public static void main(String[] args)
    {
        DictionaryServer.port = Integer.parseInt(args[0]);

        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try(ServerSocket server = factory.createServerSocket(port))
        {
            System.out.println("Dictionary Server Waiting for Connections");

            BufferedReader reader = new BufferedReader(new FileReader(args[1]));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            String jsonStr = sb.toString();
            JSONObject json = new JSONObject(jsonStr);
            Iterator<String> jsonIter = json.keys();
            while(jsonIter.hasNext()){
                String key = jsonIter.next();
                String value = json.getString(key);
                Dictionary.put(key, value);
            }

            // Wait for connections.
            while(true)
            {
                Socket client = server.accept();
                System.out.println("Client "+(++counter)+": has been connected to server!");

                // Start a new thread for a connection
                Thread t = new Thread(() -> serveClient(client, counter, args[1]));
                t.start();
            }
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

    private static void serveClient(Socket client, int id, String filename)
    {

        try(Socket clientSocket = client)
        {
            while(true){
                try
                {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

                String clientMsg = null;
                String mode = "";

                mode = in.readLine();
                while (in.ready()){
                    in.readLine();
                }
                    while(true)
                    {
                        if (mode.equals("1")){
                            System.out.println("This is query mode");
                            out.write("Please input a word you want to query: \n");
                            out.flush();
                            while(true){
                                String key = "";
                                key = in.readLine();
                                while(in.ready()){
                                    in.readLine();
                                }
                                if (key.equals("1") || key.equals("2") || key.equals("3") || key.equals("4") || key.equals("0")){
                                    mode = key;
                                    break;
                                }
                                if (Dictionary.containsKey(key.toLowerCase())){
                                    String value = "%s\n".formatted(Dictionary.get(key.toLowerCase()));
                                    out.write(value);
                                    out.flush();

                                    synchronized (Dictionary){
                                        JSONObject json = new JSONObject(Dictionary);
                                        String jsonStr = json.toString();
                                        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(filename));
                                        os.write(jsonStr);
                                        os.close();
                                    }
                                }
                                else{
                                    out.write("Whops! Key " + key + " is not in the dictionary.\n");
                                    out.flush();
                                }
                            }

                        }
                        else if (mode.equals("2")){
                            System.out.println("This is add mode");
                            out.write("Please input a word you want to add: \n");
                            out.flush();
                            while(true){
                                String key = "";
                                key = in.readLine();
                                while(in.ready()){
                                    in.readLine();
                                }
                                if (key.equals("1") || key.equals("2") || key.equals("3") || key.equals("4") || key.equals("0")){
                                    mode = key;
                                    break;
                                }
                                if (Dictionary.containsKey(key.toLowerCase())){
                                    out.write("Whops! Key " + key + " is a duplicate word in the dictionary.\n");
                                    out.flush();
                                }
                                else{
                                    out.write("Please input the meaning of " + key + ".\n");
                                    out.flush();

                                    String value = "";
                                    while(true){
                                        value += in.readLine() + "\n";
                                        if (!in.ready()){
                                            break;
                                        }
                                    }

                                    if (value.equals("1\n") || value.equals("2\n") || value.equals("3\n") || value.equals("4\n") || value.equals("0\n")){
                                        mode = value.substring(0, 1);
                                        break;
                                    }
                                    Dictionary.put(key.toLowerCase(), value);
                                    out.write("Key " + key + " has been added in the dictionary.\n");
                                    out.flush();
                                    synchronized (Dictionary){
                                        JSONObject json = new JSONObject(Dictionary);
                                        String jsonStr = json.toString();
                                        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(filename));
                                        os.write(jsonStr);
                                        os.close();
                                    }
                                }
                            }
                        }
                        else if (mode.equals("3")){
                            System.out.println("This is remove mode");
                            out.write("Please input a word you want to remove: \n");
                            out.flush();
                            while(true){
                                String key = "";
                                key = in.readLine();
                                while(in.ready()){
                                    in.readLine();
                                }
                                if (key.equals("1") || key.equals("2") || key.equals("3") || key.equals("4") || key.equals("0")){
                                    mode = key;
                                    break;
                                }
                                if (!Dictionary.containsKey(key.toLowerCase())){
                                    out.write("Whops! Key " + key + " is not in the dictionary.\n");
                                    out.write("Please input a word you want to remove: \n");
                                    out.flush();
                                }
                                else{
                                    Dictionary.remove(key.toLowerCase());
                                    out.write("Key " + key + " has been removed from the dictionary.\n");
                                    out.flush();
                                    synchronized (Dictionary){
                                        JSONObject json = new JSONObject(Dictionary);
                                        String jsonStr = json.toString();
                                        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(filename));
                                        os.write(jsonStr);
                                        os.close();
                                    }
                                }
                            }
                        }
                        else if (mode.equals("4")){
                            System.out.println("This is update mode");
                            out.write("Please input a word you want to update: \n");
                            out.flush();
                            while(true){
                                String key = "";
                                key = in.readLine();
                                while(in.ready()){
                                    in.readLine();
                                }
                                if (key.equals("1") || key.equals("2") || key.equals("3") || key.equals("4") || key.equals("0")){
                                    mode = key;
                                    break;
                                }
                                if (!Dictionary.containsKey(key.toLowerCase())){
                                    out.write("Whops! Key " + key + " is not in the dictionary.\n");
                                    out.write("Please input the word you want to update: \n");
                                    out.flush();
                                }
                                else{
                                    out.write("Please input the new meaning of " + key + ".\n");
                                    out.flush();
                                    String value = "";
                                    while(true){
                                        value += in.readLine() + "\n";
                                        if (!in.ready()){
                                            break;
                                        }
                                    }

                                    if (value.equals("1\n") || value.equals("2\n") || value.equals("3\n") || value.equals("4\n") || value.equals("0\n")){
                                        mode = value.substring(0, 1);
                                        break;
                                    }
                                    Dictionary.put(key.toLowerCase(), value);
                                    out.write("The meaning of " + key + " has been updated.\n");
                                    out.flush();
                                    synchronized (Dictionary){
                                        JSONObject json = new JSONObject(Dictionary);
                                        String jsonStr = json.toString();
                                        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(filename));
                                        os.write(jsonStr);
                                        os.close();
                                    }
                                }
                            }
                        }

                        else{
                            while(true) {
                                out.write("Please choose a mode below!\n");
                                out.flush();
                                String key = "";
                                key = in.readLine();
                                while(in.ready()){
                                    in.readLine();
                                }
                                if (key.equals("1") || key.equals("2") || key.equals("3") || key.equals("4") || key.equals("0")) {
                                    mode = key;
                                    break;
                                }
                            }
                        }
                    }
                }
                catch (NullPointerException e){
                    System.out.printf("Client %d closed connection\n".formatted(id));
                    return;
                }
                catch(SocketException e)
                {
                    System.out.printf("Client %d closed connection\n".formatted(id));
                    return;
                }
            }
        }
        catch (NullPointerException e){
            System.out.printf("Client %d closed connection\n".formatted(id));
        }
        catch(UnknownHostException e)
        {
            System.out.printf("Client %d closed connection\n".formatted(id));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
