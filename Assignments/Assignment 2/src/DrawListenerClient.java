import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawListenerClient extends MouseAdapter implements ActionListener, WindowListener {

    private int x1, y1, x2, y2;
    private String name;
    private Color color = Color.black;
    public static float stroke = 2L;
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
    private BufferedImage img = null;

    public void setGr(Graphics2D g) {
        this.g = g;
    }

    public void setSp(Shape[] shapeArray, Text[] textArray) {
        this.shapeArray = shapeArray;
        this.textArray = textArray;
    }

    public void savefig(){

        try {
            img = ImageIO.read(new File(ClientGUI.imgPath));
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
            ImageIO.write(img, "png", new File(ClientGUI.imgPath));
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
                if (!ClientGUI.textField.getText().equals("")){
                    String text_content = ClientGUI.textField.getText();
                    int size = 15;

                    g.setFont(new Font("", Font.PLAIN, size));
                    g.drawString(text_content, x1, y1);
                    ClientGUI.textField.setText("");

                    Text text = new Text(text_content, x1, y1, name, color, size);
                    textArray[text_index++] = text;

                    savefig();

                    msg_text = ClientGUI.username + " is using textbox.\n";
                    ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);
                }

            }

            if ("Painter".equals(name) && (useEraser || isPrintFirst)){

                msg_text = ClientGUI.username + " is using painter.\n";
                ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = false;
                useEraser = false;
                usePainter = true;
            }

            if ("Eraser".equals(name) && (usePainter || isPrintFirst)){

                msg_text = ClientGUI.username + " is using eraser.\n";
                ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = false;
                usePainter = false;
                useEraser = true;
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

                savefig();

                msg_text = ClientGUI.username + " draws a line.\n";
                ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;

            }
            if ("Rect".equals(name)) {
                g.setStroke(new BasicStroke(stroke));
                g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                Shape rect = new Rect(x1, y1, x2, y2, name, color, stroke);
                shapeArray[shape_index++] = rect;

                savefig();

                msg_text = ClientGUI.username + " draws a rectangle.\n";
                ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;
            }
            if ("Circle".equals(name)) {
                g.setStroke(new BasicStroke(stroke));
                int radius = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
                g.drawOval(Math.min(x1, x2), Math.min(y1, y2), radius, radius);
                Shape circle = new Circle(x1, y1, x2, y2, name, color, stroke);
                shapeArray[shape_index++] = circle;

                savefig();

                msg_text = ClientGUI.username + " draws a circle.\n";
                ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;
            }
            if ("Oval".equals(name)) {
                g.setStroke(new BasicStroke(stroke));
                g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                Shape oval = new Oval(x1, y1, x2, y2, name, color, stroke);
                shapeArray[shape_index++] = oval;

                savefig();

                msg_text = ClientGUI.username + " draws a oval.\n";
                ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;
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
        }
        if ("Oval".equals(name)){
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
            g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        }
        if ("Circle".equals(name)){
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
            int radius = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));
            g.drawOval(Math.min(x1, x2), Math.min(y1, y2), radius, radius);
        }
        if ("Painter".equals(name)) {
            x2 = e.getX();
            y2 = e.getY();

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

                msg_text = ClientGUI.username + " clears the whiteboard.\n";
                ClientGUI.clientsocket.write("c394c3j2cm2390sf9" + msg_text);

                isPrintFirst = true;
                usePainter = false;
                useEraser = false;

                BufferedImage img = new BufferedImage(800, 628, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = (Graphics2D) img.getGraphics();
                graphics2D.fillRect(0, 0, 800, 628);

                try {
                    ImageIO.write(img, "png", new File(ClientGUI.imgPath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            if ("Send".equals(name)){
                if (!ClientGUI.chatbox.getText().equals("")){
                    String chat_text = ClientGUI.username + ": " + ClientGUI.chatbox.getText();
                    ClientGUI.clientsocket.write("m3oimru3289mr2384" + chat_text);
                    ClientGUI.chatbox.setText("");
                }
            }

            if ("Join".equals(name)){
                if (ClientGUI.approval){
                    JOptionPane.showMessageDialog(ClientGUI.jf, "You have already joined the shared whiteboard.");
                }
                else{
                    JOptionPane.showMessageDialog(ClientGUI.jf, "Waiting for manager's approval...");
                    try{
                        ClientGUI.clientsocket.write("jx82cr89sfd4jc392" + ClientGUI.username);
                    }
                    catch (Exception e2){
                        JOptionPane.showMessageDialog(ClientGUI.jf, "Manager is offline, please wait...");
                    }
                }
            }

            if ("Leave".equals(name)){

                int result = JOptionPane.showConfirmDialog(ClientGUI.jf, "Are you sure to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == 0){
                    try {
                        ClientGUI.clientsocket.write("v23t89rvu348908so" + ClientGUI.username);
                        Thread.sleep(100);
                    } catch (InterruptedException | NullPointerException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    finally {
                        System.exit(1);
                    }
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(ClientGUI.jf, "Are you sure to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == 0){
            try {
                ClientGUI.clientsocket.write("v23t89rvu348908so" + ClientGUI.username);
                Thread.sleep(100);
            } catch (InterruptedException | NullPointerException interruptedException) {
                interruptedException.printStackTrace();
            }
            finally {
                System.exit(1);
            }
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}