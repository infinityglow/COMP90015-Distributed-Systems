import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawListenerServer extends MouseAdapter implements ActionListener, WindowListener {

    private int x1, y1, x2, y2;
    private String name;
    private Color color = Color.BLACK;
    public static float stroke = 2;
    private Graphics2D g;
    private Shape[] shapeArray;
    private Text[] textArray;
    private int shape_index = 0;
    private int text_index = 0;
    private String chat_text = "";
    private String msg_text = "";
    private boolean isPrintFirst = true;
    private boolean usePainterEraser = false;
    private boolean useEraser = false;
    private boolean usePainter = false;
    private boolean saved = false;
    public BufferedImage img = null;

    public void setGr(Graphics2D g) {
        this.g = g;
    }

    public void setSp(Shape[] shapeArray, Text[] textArray) {
        this.shapeArray = shapeArray;
        this.textArray = textArray;
    }

    public void savefig(){

        try {
            img = ImageIO.read(new File(ManagerGUI.imagePath));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Graphics2D cg = (Graphics2D) img.getGraphics();

        for (int i = 0; i < shapeArray.length; i++) {
            Shape shape = shapeArray[i];
            if (shapeArray[i] != null) {
                shape.drawShape(cg);
            }
        }

        for (int i = 0; i < textArray.length; i++) {
            Text text = textArray[i];
            if (textArray[i] != null) {
                text.drawText(cg);
            }
        }

        try {
            ImageIO.write(img, "png", new File(ManagerGUI.imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
    }
    public void mousePressed(java.awt.event.MouseEvent e) {
        {
            x1 = e.getX();
            y1 = e.getY();
            if ("TextBox".equals(name)){
                if (!ManagerGUI.textField.getText().equals("")){
                    String text_content = ManagerGUI.textField.getText();

                    int size = 15;
                    g.setFont(new Font("", Font.PLAIN, size));
                    g.drawString(text_content, x1, y1);
                    ManagerGUI.textField.setText("");

                    Text text = new Text(text_content, x1, y1, name, color, size);
                    textArray[text_index++] = text;

                    msg_text = ManagerGUI.name + " is using textbox.\n";
                    ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                    ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);

                    savefig();
                }

            }

            if ("Painter".equals(name) && (useEraser || isPrintFirst)){

                msg_text = ManagerGUI.name + " is using painter.\n";
                ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = false;
                useEraser = false;
                usePainter = true;

                savefig();
            }

            if ("Eraser".equals(name) && (usePainter || isPrintFirst)){

                msg_text = ManagerGUI.name + " is using eraser.\n";
                ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = false;
                usePainter = false;
                useEraser = true;

                savefig();
            }
        }
    }

    public void mouseReleased(java.awt.event.MouseEvent e) {

        {
            x2 = e.getX();
            y2 = e.getY();
            if ("Line".equals(name)) {
                g.setStroke(new BasicStroke(stroke));
                g.drawLine(x1, y1, x2, y2);
                Shape line = new Line(x1, y1, x2, y2, name, color, stroke);
                shapeArray[shape_index++] = line;

                msg_text = ManagerGUI.name + " draws a line.\n";
                ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;

                savefig();
            }
            if ("Rect".equals(name)) {
                g.setStroke(new BasicStroke(stroke));
                g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                Shape rect = new Rect(x1, y1, x2, y2, name, color, stroke);
                shapeArray[shape_index++] = rect;

                msg_text = ManagerGUI.name + " draws a rectangle.\n";
                ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);
                isPrintFirst = true;
                usePainter = false;
                useEraser = false;

                savefig();
            }
            if ("Circle".equals(name)) {
                g.setStroke(new BasicStroke(stroke));
                int radius = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
                g.drawOval(Math.min(x1, x2), Math.min(y1, y2), radius, radius);
                Shape circle = new Circle(x1, y1, x2, y2, name, color, stroke);
                shapeArray[shape_index++] = circle;

                msg_text = ManagerGUI.name + " draws a circle.\n";
                ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;

                savefig();
            }
            if ("Oval".equals(name)){
                g.setStroke(new BasicStroke(stroke));
                g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                Shape oval = new Oval(x1, y1, x2, y2, name, color, stroke);
                shapeArray[shape_index++] = oval;


                msg_text = ManagerGUI.name + " draws a oval.\n";
                ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;

                savefig();
            }


        }

    }

    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    public void mouseExited(java.awt.event.MouseEvent e) {
    }
    public void mouseDragged(java.awt.event.MouseEvent e) {
        if ("Line".equals(name)){
            x2 = e.getX();
            y2 = e.getY();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 900, 700);

            for (Shape shape : shapeArray) {
                if (shape != null) {
                    shape.drawShape(g);
                }
            }

            g.setColor(color);
            g.setStroke(new BasicStroke(stroke));
            g.drawLine(x1, y1, x2, y2);


        }
        if ("Rect".equals(name)){
            x2 = e.getX();
            y2 = e.getY();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 900, 700);

            for (Shape shape : shapeArray) {
                if (shape != null) {
                    shape.drawShape(g);
                }
            }
            g.setColor(color);
            g.setStroke(new BasicStroke(stroke));
            g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

            savefig();
        }
        if ("Oval".equals(name)){
            x2 = e.getX();
            y2 = e.getY();

            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(stroke));
            g.fillRect(0, 0, 900, 700);

            for (Shape shape : shapeArray) {
                if (shape != null) {
                    shape.drawShape(g);
                }
            }
            g.setColor(color);
            g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

            savefig();
        }
        if ("Circle".equals(name)){
            x2 = e.getX();
            y2 = e.getY();

            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(stroke));
            g.fillRect(0, 0, 900, 700);

            for (Shape shape : shapeArray) {
                if (shape != null) {
                    shape.drawShape(g);
                }
            }
            g.setColor(color);
            int radius = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
            g.drawOval(Math.min(x1, x2), Math.min(y1, y2), radius, radius);

            savefig();
        }
        if ("Painter".equals(name)) {
            x2 = e.getX();
            y2 = e.getY();
            g.setColor(color);
            g.setStroke(new BasicStroke(stroke));
            g.drawLine(x1, y1, x2, y2);
            Shape line = new Line(x1, y1, x2, y2, name, color, stroke);
            shapeArray[shape_index++] = line;
            x1 = x2;
            y1 = y2;

            savefig();
        }
        if ("Eraser".equals(name)) {
            color = Color.white;
            g.setColor(color);
            ((Graphics2D) g).setStroke(new BasicStroke(20));
            x2 = e.getX();
            y2 = e.getY();
            g.drawLine(x1, y1, x2, y2);
            Shape eraser = new Eraser(x1, y1, x2, y2, name, color, stroke);
            shapeArray[shape_index++] = eraser;
            x1 = x2;
            y1 = y2;
            color = Color.black;
            g.setColor(color);
            ((Graphics2D) g).setStroke(new BasicStroke(1));

            savefig();
        }
    }

    public void mouseMoved(java.awt.event.MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {

        if ("".equals(e.getActionCommand())) {
            JButton jb = (JButton) e.getSource();
            color = jb.getBackground();
            g.setColor(color);
        } else {
            name = e.getActionCommand();

            if ("Clear".equals(name)) {
                color = Color.white;
                g.setColor(color);
                x1 = 0;
                y1 = 0;
                x2 = 900;
                y2 = 700;
                g.fillRect(x1, y1, x2, y2);

                Arrays.fill(this.shapeArray, null);
                Arrays.fill(this.textArray, null);

                color = Color.black;
                g.setColor(color);

                msg_text = ManagerGUI.name + " clears the whiteboard.\n";
                ManagerGUI.msgbox.setText(ManagerGUI.msgbox.getText() + msg_text);
                ManagerGUI.serversocket.broadcast("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;

                BufferedImage img = new BufferedImage(800, 628, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = (Graphics2D) img.getGraphics();
                graphics2D.fillRect(0, 0, 800, 628);

                try {
                    ImageIO.write(img, "png", new File(ManagerGUI.imagePath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

            if ("Send".equals(name)){
                if (!ManagerGUI.chatbox.getText().equals("")){
                    chat_text =  ManagerGUI.name + ": " +ManagerGUI.chatbox.getText() + "\n";
                    ManagerGUI.chatpanel.setText(ManagerGUI.chatpanel.getText() + chat_text);
                    ManagerGUI.serversocket.broadcast("m3oimru3289mr2384" + chat_text);
                    ManagerGUI.chatbox.setText("");
                }
            }

            if ("Kick".equals(name)){

                String[] userList = ManagerGUI.serversocket.getAllUsers();
                Object[] options = {"Kick", "Kick All", "Cancel"};

                JList list = new JList(userList);
                JScrollPane jsp = new JScrollPane(list);
                jsp.setPreferredSize(new Dimension(75, 150));

                int result = JOptionPane.showOptionDialog(
                        ManagerGUI.jf, jsp, "Kick User", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (result == 0){
                    int userIdx = list.getSelectedIndices()[0];
                    String kicked = userList[userIdx];

                    int iskicked = JOptionPane.showConfirmDialog(ManagerGUI.jf, "Are you sure to kick %s?".formatted(kicked), "Kick User", JOptionPane.YES_NO_OPTION);
                    if (iskicked == 0){
                        ManagerGUI.serversocket.unicast(kicked, "qcj0239nru3k3r9ks");
                        JOptionPane.showMessageDialog(ManagerGUI.jf, "User %s has been kicked.".formatted(kicked));
                    }
                }

                if (result == 1){
                    int iskicked = JOptionPane.showConfirmDialog(ManagerGUI.jf, "Are you sure to kick all users?", "Kick All Users", JOptionPane.YES_NO_OPTION);
                    if (iskicked == 0){
                        ManagerGUI.serversocket.broadcast("qcj0239nru3k3r9ks");
                        JOptionPane.showMessageDialog(ManagerGUI.jf, "All user have been kicked.");
                    }
                }

            }

            if ("Leave".equals(name) || "Close".equals(name)){

                int result = JOptionPane.showConfirmDialog(ManagerGUI.jf, "Are you sure to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == 0){
                    ManagerGUI.serversocket.broadcast("ry839yr38q290yr0q");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    System.exit(1);
                }
            }

            if ("New".equals(name)){
                String cmd = "";
                String port = JOptionPane.showInputDialog(ManagerGUI.jf, "Input port number: ");
                if (port != null){
                    String name = JOptionPane.showInputDialog(ManagerGUI.jf, "Input manager name: ");
                    if (name != null){
                        cmd = "java -jar CreateWhiteBoard.jar %s %s %s".formatted(ManagerGUI.ip, port, name);
                    }
                    else {
                        return;
                    }
                }
                else {
                    return;
                }

                Runtime runtime = Runtime.getRuntime();
                try {
                    Process process = runtime.exec(cmd);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            if ("Open".equals(name)){
                FileDialog fileDialog = new FileDialog(ManagerGUI.jf, "Open", FileDialog.LOAD);
                FilenameFilter fileFilter = new FilenameFilter(){
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")){
                            return true;
                        }
                        return false;
                    }
                };

                fileDialog.setFilenameFilter(fileFilter);
                fileDialog.setVisible(true);

                String dir = fileDialog.getDirectory();
                String filename = fileDialog.getFile();

                File file = new File(dir + filename);

                ManagerGUI.imagePath = file.getAbsolutePath();
                Arrays.fill(this.shapeArray, null);
                Arrays.fill(this.textArray, null);
                saved = true;
                ManagerGUI.serversocket.broadcast("rj82391ne328n2mu8" + ManagerGUI.imagePath);
                savefig();
            }

            if ("Save".equals(name)){
                if (saved){
                    try {
                        ImageIO.write(img, "png", new File(ManagerGUI.imagePath));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(ManagerGUI.jf, "Image has been saved!");
                }
                else{
                    FileDialog saveDialog = new FileDialog(ManagerGUI.jf, "Save As", FileDialog.SAVE);
                    saveDialog.setVisible(true);

                    String dir = saveDialog.getDirectory();
                    String filename = saveDialog.getFile();

                    if (dir != null && filename != null){
                        ManagerGUI.imagePath = dir + filename + ".png";
                        try {
                            ImageIO.write(img, "png", new File(ManagerGUI.imagePath));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(ManagerGUI.jf, "Image has been saved!");
                        saved = true;
                    }
                }
            }

            if ("Save As".equals(name)){

                FileDialog saveDialog = new FileDialog(ManagerGUI.jf, "Save As", FileDialog.SAVE);
                saveDialog.setVisible(true);

                String dir = saveDialog.getDirectory();
                String filename = saveDialog.getFile();

                if (dir != null && filename != null){
                    ManagerGUI.imagePath = dir + filename + ".png";
                    try {
                        ImageIO.write(img, "png", new File(ManagerGUI.imagePath));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(ManagerGUI.jf, "Image has been saved!");
                    saved = true;
                }
            }
        }
    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(ManagerGUI.jf, "Are you sure to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == 0){
            ManagerGUI.serversocket.broadcast("ry839yr38q290yr0q");
            try {
                Thread.sleep(200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.exit(1);
        }
    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }
}