import java.io.*;
import java.net.*;
import java.util.HashMap;
import javax.net.ServerSocketFactory;

public class DictionaryServer {

    private static HashMap<String, String> Dictionary = new HashMap<String, String>();

    // Declare the port number
    private static int port = 0;

    // Identifies the user number connected
    private static int counter = 0;

    public static void main(String[] args)
    {
        DictionaryServer.port = Integer.parseInt(args[0]);

        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try(ServerSocket server = factory.createServerSocket(port))
        {
            System.out.println("Dictionary Server Waiting for Connections");
            Dictionary.put("abc", "bcd");
            // Wait for connections.
            while(true)
            {
                Socket client = server.accept();
                counter++;
                System.out.println("Client "+counter+": has been connected to server!");

                // Start a new thread for a connection
                Thread t = new Thread(() -> serveClient(client, counter));
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

    private static void serveClient(Socket client, int i)
    {
        try(Socket clientSocket = client)
        {
            while(true){
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

                String clientMsg = null;
                out.write("Choose mode: " +
                        "Q for querying a word, " +
                        "A for adding a new word, " +
                        "R for removing a word, " +
                        "U for updating a word meaning, " +
                        "E for exiting\n");
                out.flush();
                try
                {
                    while((clientMsg = in.readLine()) != null)
                    {
                        String mode = clientMsg;
                        if (mode.equals("Q")){
                            System.out.println("This is query mode");
                            out.write("Please input the word you want to query: \n");
                            out.flush();
                            String key = null;
                            while((key = in.readLine()) != null){
                                if (Dictionary.containsKey(key)){
                                    String value = "The meaning of key %s is %s\n".formatted(key, Dictionary.get(key));
                                    out.write(value);
                                    out.flush();
                                    break;
                                }
                                else{
                                    out.write("Key " + key + " is not in the dictionary, please input again:\n");
                                    out.flush();
                                }
                            }
                        }
                        else if (mode.equals("A")){
                            System.out.println("This is add mode");
                            out.write("Please input a word you want to add: \n");
                            out.flush();
                            while(true){
                                String key = in.readLine();
                                if (Dictionary.containsKey(key)){
                                    out.write("Key " + key + " is already in the dictionary.\n");
                                    out.write("Please input the word you want to add: \n");
                                    out.flush();
                                }
                                else{
                                    out.write("Please input the meaning of " + key + ".\n");
                                    out.flush();
                                    String value = in.readLine();
                                    Dictionary.put(key, value);
                                    out.write("Key " + key + " has been added in the dictionary.\n");
                                    out.flush();
                                    break;
                                }
                            }
                        }
                        else if (mode.equals("R")){
                            System.out.println("This is remove mode");
                            out.write("Please input a word you want to remove: \n");
                            out.flush();
                            while(true){
                                String key = in.readLine();
                                if (!Dictionary.containsKey(key)){
                                    out.write("Key " + key + " is not in the dictionary.\n");
                                    out.write("Please input a word you want to remove: \n");
                                    out.flush();
                                }
                                else{
                                    Dictionary.remove(key);
                                    out.write("Key " + key + " has been removed from the dictionary.\n");
                                    out.flush();
                                    break;
                                }
                            }
                        }
                        else if (mode.equals("U")){
                            System.out.println("This is update mode");
                            out.write("Please input a word you want to update: \n");
                            out.flush();
                            while(true){
                                String key = in.readLine();
                                if (!Dictionary.containsKey(key)){
                                    out.write("Key " + key + " is not in the dictionary.\n");
                                    out.write("Please input the word you want to update: \n");
                                    out.flush();
                                }
                                else{
                                    out.write("Please input the new meaning of " + key + ".\n");
                                    out.flush();
                                    String value = in.readLine();
                                    Dictionary.put(key, value);
                                    out.write("The meaning of " + key + " has been updated.\n");
                                    out.flush();
                                    break;
                                }
                            }
                        }
                        else if (mode.equals("E")){
                            break;
                        }
                        else{
                            System.out.println("Please choose the correct mode from Q, A, R, U, E");
                        }
                        out.write("Choose mode: " +
                                "Q for querying a word, " +
                                "A for adding a new word, " +
                                "R for removing a word, " +
                                "U for updating a word meaning, " +
                                "E for exiting\n");
                        out.flush();
                    }
                }

                catch(SocketException e)
                {
                    System.out.println("closed...");
                    return;
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
