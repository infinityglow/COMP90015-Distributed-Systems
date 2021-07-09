import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ClientGUI extends JPanel {
    public static Client clientsocket;
    public static String hostname;
    public static int port;
    public static String username;
    private Shape[] shapeParameter = new Shape[10000];
    private Text[] textParameters = new Text[1000];
    public static JTextField textField;
    public static JEditorPane chatbox;
    public static JEditorPane chatpanel;
    public static JEditorPane msgbox;
    public static JEditorPane userlist;
    public static JFrame jf;
    public static JSlider js;
    private Graphics g;
    public static boolean approval = false;
    private DrawListenerClient dl;
    private SlideListenerClient sdl;
    private static BufferedImage image;
    private static String[] usernameList = {"Alice", "Bob", "Carol", "David", "Edwin", "Frank", "Goe", "Harry", "Ivan",
                                            "Jesse", "Ken", "Lucy", "Mike", "Nick", "Oscar", "Paul", "Quentin", "Ray",
                                            "Steve", "Tim", "Ultraman", "Victor", "William", "Zack"};
    public static String imgPath;

    public void initUI() {
        jf = new JFrame("Client");
        jf.setSize(1100, 740);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);
        jf.setLayout(new BorderLayout());

        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel();
        jf.add(this, BorderLayout.CENTER);
        jf.add(jp2, BorderLayout.WEST);
        jf.add(jp3, BorderLayout.SOUTH);
        jf.add(jp4, BorderLayout.EAST);

        this.setPreferredSize(new Dimension(900, 700));
        this.setBackground(Color.white);

        this.dl = new DrawListenerClient();
        this.addMouseListener(dl);
        this.addMouseMotionListener(dl);

        jp2.setPreferredSize(new Dimension(100, 700));
        jp2.setBackground(Color.LIGHT_GRAY);
        jp3.setPreferredSize(new Dimension(1100, 50));
        jp3.setBackground(Color.LIGHT_GRAY);
        jp4.setPreferredSize(new Dimension(200, 700));
        jp4.setBackground(Color.LIGHT_GRAY);

        jp2.setLayout(null);

        String[] shapeArray = { "Line", "Rect", "Circle", "Oval", "Painter", "Eraser", "Clear", "TextBox" };
        for (int i = 0; i < shapeArray.length; i++) {
            JButton jbu1 = new JButton(shapeArray[i]);
            jbu1.setBounds(0, 10+i*45, 100, 40);
            jp2.add(jbu1);
            jbu1.addActionListener(dl);
        }

        textField = new JTextField();
        textField.setBounds(0, 370, 100, 40);
        jp2.add(textField);

        JLabel lb0 = new JLabel("User List");
        lb0.setBounds(20, 400, 100, 40);
        lb0.setFont(new Font("", Font.PLAIN, 15));
        jp2.add(lb0);

        userlist = new JEditorPane();
        userlist.setEditable(false);
        userlist.setBounds(5, 435, 80, 100);
        JScrollPane jsp0 = new JScrollPane(userlist);
        jsp0.setBounds(5, 435, 90, 100);
        jp2.add(jsp0);

        JButton jbu1 = new JButton("Join");
        jbu1.setBounds(0, 545, 100, 40);
        jp2.add(jbu1);
        jbu1.addActionListener(dl);

        JButton jbu2 = new JButton("Leave");
        jbu2.setBounds(0, 590, 100, 40);
        jp2.add(jbu2);
        jbu2.addActionListener(dl);

        Color[] colorArray = {Color.red, Color.pink, Color.orange, Color.yellow, Color.green, Color.blue,
                new Color(85, 67, 133), Color.cyan, Color.magenta, new Color(205, 127, 50),
                new Color(234, 221, 202), new Color(128,0,0), new Color(204, 156, 20),
                Color.black, Color.gray, Color.white};

        jp3.setLayout(null);
        for (int i = 0; i < colorArray.length; i++) {
            JButton jbu3 = new JButton();
            jbu3.setBackground(colorArray[i]);
            jbu3.setOpaque(true);
            jbu3.setBorderPainted(false);
            jbu3.setBounds(120+i*40, 10, 30, 30);
            jp3.add(jbu3);
            jbu3.addActionListener(dl);
        }

        this.sdl = new SlideListenerClient();
        js = new JSlider(SwingConstants.HORIZONTAL, 10, 100, 20);
        js.setBounds(760, 10, 150, 30);
        js.addChangeListener(sdl);
        jp3.add(js);

        jp4.setLayout(null);
        chatbox = new JEditorPane();
        chatbox.setBounds(15, 460, 160, 120);

        JScrollPane jsp1 = new JScrollPane(chatbox);
        jsp1.setBounds(15, 460, 170, 120);

        chatpanel = new JEditorPane();
        chatpanel.setEditable(false);
        chatpanel.setBounds(15, 330, 160, 120);

        JScrollPane jsp2 = new JScrollPane(chatpanel);
        jsp2.setBounds(15, 330, 170, 120);

        msgbox = new JEditorPane();
        msgbox.setEditable(false);
        msgbox.setBounds(15, 330, 160, 120);

        JScrollPane jsp3 = new JScrollPane(msgbox);
        jsp3.setBounds(15, 40, 170, 260);

        JButton submit = new JButton("Send");
        submit.setBounds(50, 590, 100, 40);
        submit.addActionListener(dl);

        JLabel lb1 = new JLabel("Chat Box");
        lb1.setBounds(15, 300, 100, 30);
        lb1.setFont(new Font("", Font.PLAIN, 20));

        JLabel lb2 = new JLabel("Message Box");
        lb2.setBounds(15, 5, 150, 30);
        lb2.setFont(new Font("", Font.PLAIN, 20));

        jp4.add(jsp1);
        jp4.add(jsp2);
        jp4.add(jsp3);
        jp4.add(submit);
        jp4.add(lb1);
        jp4.add(lb2);

        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jf.addWindowListener(dl);
        jf.setVisible(true);

        Graphics2D g = (Graphics2D) this.getGraphics();
        dl.setGr(g);
        dl.setSp(shapeParameter, textParameters);
        this.g = g;
    }

    private static void handleText(ClientGUI gui){
        approval = false;
        while (true){
            String msg = clientsocket.read();
            if (msg != null) {
                if (msg.startsWith("cnr9433h254kh34k2")){
                    ClientGUI.clientsocket.write(ClientGUI.username);
                }
                if (approval && msg.startsWith("m3oimru3289mr2384")){
                    msg = msg.substring(17);
                    ClientGUI.chatpanel.setText(ClientGUI.chatpanel.getText() + msg + "\n");
                }
                else if (approval && msg.startsWith("c394c3j2cm2390sf9")){
                    msg = msg.substring(17);
                    ClientGUI.msgbox.setText(ClientGUI.msgbox.getText() + msg + "\n");
                }
                else if (msg.startsWith("jx82cr89sfd4jc392")){
                    msg = msg.substring(17);
                    if (msg.startsWith("0")){
                        imgPath = msg.substring(1);
                        JOptionPane.showMessageDialog(jf, "The manager approved your request!");
                        Arrays.fill(gui.shapeParameter, null);
                        Arrays.fill(gui.textParameters, null);

                        for (String user: ManagerServer.getAllUsers()){
                            System.out.println(user);
                        }

                        Thread t2 = new Thread(() -> handleImg(gui));
                        t2.start();
                        jf.setTitle(username);
                        approval = true;
                    }
                    else if (Integer.parseInt(msg) == 1){
                        JOptionPane.showMessageDialog(jf, "The manager denied your request!");
                        System.exit(1);
                    }
                    else if (Integer.parseInt(msg) == 2){
                        Object[] options = {"Generate", "Reinput", "Cancel"};
                        int result = JOptionPane.showOptionDialog(
                                jf, "Your name is a duplicate. Would you like to input a new name or generate a random one?", "Name Duplication", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (result == 0){
                            int index = (int) (Math.random() * usernameList.length);
                            username = usernameList[index];
                            clientsocket.write("jx82cr89sfd4jc392" + username);
                        }
                        if (result == 1){
                            String name = JOptionPane.showInputDialog(jf, "Input your name:", "Message", JOptionPane.QUESTION_MESSAGE);
                            if (name != null){
                                username = name;
                                clientsocket.write("jx82cr89sfd4jc392" + username);
                            }
                        }
                    }
                }
                else if (msg.startsWith("ry839yr38q290yr0q")){
                    JOptionPane.showMessageDialog(ClientGUI.jf, "The manager exits the shared writeboard!");
                    System.exit(1);
                }

                else if (msg.startsWith("qcj0239nru3k3r9ks")){
                    JOptionPane.showMessageDialog(ClientGUI.jf, "You are kicked by the manager!");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(1);
                }

                else if (msg.startsWith("rj82391ne328n2mu8")){
                    Arrays.fill(gui.shapeParameter, null);
                    Arrays.fill(gui.textParameters, null);
                    imgPath = msg.substring(17);
                }
                else if (msg.startsWith("ufu32ry39f3849kd2")){
                    msg = msg.substring(17);
                    String[] user_list = msg.split(" ");
                    String text = "";
                    for (String user: user_list){
                        text += user + "\n";
                    }
                    userlist.setText(text);
                }
            }
        }
    }

    private void draw(){
        g.drawImage(image, 0, 0, this);
    }

    private static void handleImg(ClientGUI gui){
        while (true){
            try {
                image = ImageIO.read(new File(imgPath));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            gui.draw();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        ClientGUI gui = new ClientGUI();
        gui.initUI();

        hostname = args[0];
        port = Integer.parseInt(args[1]);
        username = args[2];
        clientsocket = new Client(hostname, port, username);

        clientsocket.connect();
        Thread t1 = new Thread(() -> handleText(gui));
        t1.start();

    }
}
