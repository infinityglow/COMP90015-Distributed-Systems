import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ManagerGUI extends JPanel {
    public static ManagerServer serversocket;
    public static String ip;
    public static int port;
    public static String name;
    public Shape[] shapeParameters = new Shape[1000];
    public Text[] textParameters = new Text[100];
    private DrawListenerServer dl;
    private SlideListenerServer sdl;
    public static JTextField textField;
    public static JEditorPane chatbox;
    public static JEditorPane chatpanel;
    public static JEditorPane msgbox;
    public static JEditorPane userlist;
    public static JSlider js;
    public static JFrame jf;
    private Graphics g;
    private static BufferedImage image;
    public static String imagePath;

    public void initUI() {
        jf = new JFrame();
        jf.setSize(1100, 750);
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

        this.dl = new DrawListenerServer();
        this.addMouseListener(dl);
        this.addMouseMotionListener(dl);

        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("File");

        String[] menuItems = {"New", "Open", "Save", "Save As", "Close"};
        for (String menuItem: menuItems){
            JMenuItem jmi = new JMenuItem(menuItem);
            jm.add(jmi);
            jmi.addActionListener(dl);
        }

        jmb.add(jm);
        jf.setJMenuBar(jmb);
        jf.validate();
        jf.repaint();

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

        JLabel lb1 = new JLabel("User List");
        lb1.setBounds(20, 400, 100, 40);
        lb1.setFont(new Font("", Font.PLAIN, 15));
        jp2.add(lb1);

        userlist = new JEditorPane();
        userlist.setEditable(false);
        userlist.setBounds(5, 435, 80, 100);
        JScrollPane jsp0 = new JScrollPane(userlist);
        jsp0.setBounds(5, 435, 90, 100);
        jp2.add(jsp0);

        JButton jbu0 = new JButton("Kick");
        jbu0.setBounds(0, 545, 100, 40);
        jp2.add(jbu0);
        jbu0.addActionListener(dl);

        JButton jbu1 = new JButton("Leave");
        jbu1.setBounds(0, 590, 100, 40);
        jp2.add(jbu1);
        jbu1.addActionListener(dl);

        Color[] colorArray = {Color.red, Color.pink, Color.orange, Color.yellow, Color.green, Color.blue,
                new Color(85, 67, 133), Color.cyan, Color.magenta, new Color(205, 127, 50),
                new Color(234, 221, 202), new Color(128,0,0), new Color(204, 156, 20),
                Color.black, Color.gray, Color.white};

        jp3.setLayout(null);
        for (int i = 0; i < colorArray.length; i++) {
            JButton jbu2 = new JButton();
            jbu2.setBackground(colorArray[i]);
            jbu2.setOpaque(true);
            jbu2.setBorderPainted(false);
            jbu2.setBounds(120+i*40, 10, 30, 30);
            jp3.add(jbu2);
            jbu2.addActionListener(dl);
        }

        this.sdl = new SlideListenerServer();
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
        chatpanel.setAutoscrolls(true);
        chatpanel.setBounds(15, 330, 160, 120);

        JScrollPane jsp2 = new JScrollPane(chatpanel);
        jsp2.setBounds(15, 330, 170, 120);
        jsp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        msgbox = new JEditorPane();
        msgbox.setEditable(false);
        msgbox.setBounds(15, 330, 160, 120);

        JScrollPane jsp3 = new JScrollPane(msgbox);
        jsp3.setBounds(15, 40, 170, 260);

        JButton submit = new JButton("Send");
        submit.setBounds(50, 590, 100, 40);
        submit.addActionListener(dl);

        JLabel lb2 = new JLabel("Chat Box");
        lb2.setBounds(15, 300, 100, 30);
        lb2.setFont(new Font("", Font.PLAIN, 20));

        JLabel lb3 = new JLabel("Message Box");
        lb3.setBounds(15, 5, 150, 30);
        lb3.setFont(new Font("", Font.PLAIN, 20));

        jp4.add(jsp1);
        jp4.add(jsp2);
        jp4.add(jsp3);
        jp4.add(submit);
        jp4.add(lb2);
        jp4.add(lb3);

        jf.addWindowListener(dl);
        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jf.setVisible(true);

        Graphics2D g = (Graphics2D) this.getGraphics();
        dl.setGr(g);
        dl.setSp(shapeParameters, textParameters);
        this.g = g;
    }

    public void paint(Graphics2D g) {
        super.paint(g);
        for (int i = 0; i < shapeParameters.length; i++) {
            Shape shape = shapeParameters[i];
            if (shapeParameters[i] != null) {
                shape.drawShape(g);
            }
        }
    }

    private void draw(){
        g.drawImage(image, 0, 0, this);
    }

    private static void accept(){
        serversocket.accept();
    }

    private static void handleImg(ManagerGUI gui){
        while (true){
            try {
                image = ImageIO.read(new File(imagePath));
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
        ManagerGUI gui = new ManagerGUI();

        gui.initUI();

        ip = args[0];
        port = Integer.parseInt(args[1]);
        name = args[2];

        serversocket = new ManagerServer(port);

        jf.setTitle(name);

        serversocket.connect();
        Thread t1 = new Thread(() -> accept());
        t1.start();

        BufferedImage img = new BufferedImage(gui.getWidth(), gui.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) img.getGraphics();
        graphics2D.fillRect(0, 0, gui.getWidth(), gui.getHeight());

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        imagePath = "../temp/" + name + ": " + formatter.format(date) + ".png";

        try {
            ImageIO.write(img, "png", new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(() -> handleImg(gui));
        t2.start();

    }
}
