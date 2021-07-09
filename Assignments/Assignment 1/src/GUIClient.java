import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Wed Mar 31 17:29:04 CST 2021
 */

/**
 * @author unknown
 */
public class GUIClient extends JFrame {

    private static DictionaryClient client;
    private static String mode = "";
    private static Date startTime;

    public GUIClient(DictionaryClient client) {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIClient.client = client;
    }

    // when click submit button
    private void submitActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        String text = textbox.getText();

        if (!text.equals("")){
            client.write(text);
            String msg = "";
            if (mode.equals("Q")){
                msg = client.readAll();
                if (!msg.equals("Whops! Key %s is not in the dictionary.\n".formatted(text))){
                    textarea.setText(msg);
                    guidelabel.setText("Query success!");
                }
                else{
                    guidelabel.setText(msg);
                    textarea.setText("");
                }
            }
            else{
                msg = client.read();
                guidelabel.setText(msg);
                textarea.setText("");
            }
        }
        textbox.setText("");
        GUIClient.startTime = new Date();
    }

    // when click clear button
    private void clearActionPerformed(ActionEvent e) {
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        // TODO add your code here
        textbox.setText("");
        textarea.setText("");
        GUIClient.startTime = new Date();
    }

    // when click cancel button
    private void cancelActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        textbox.setText("");
        textarea.setText("");

        client.write("0");
        mode = "C";
        String msg = client.read();
        guidelabel.setText(msg);
        GUIClient.startTime = new Date();
    }

    // when click query button
    private void queryActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        client.write("1");;
        String msg = client.read();
        mode = "Q";
        guidelabel.setText(msg);
        textarea.setText("");
        GUIClient.startTime = new Date();
    }

    // when click add button
    private void addActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        guidelabel.setText("Please input a word you want to add");
        client.write("2");
        String msg = client.read();
        mode = "A";
        guidelabel.setText(msg);
        textarea.setText("");
        GUIClient.startTime = new Date();
    }

    // when click remove button
    private void removeActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        guidelabel.setText("Please input a word you want to remove");
        client.write("3");
        String msg = client.read();
        mode = "R";
        guidelabel.setText(msg);
        textarea.setText("");
        GUIClient.startTime = new Date();
    }

    // when click update button
    private void updateActionPerformed(ActionEvent e){
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        client.write("4");
        String msg = client.read();
        mode = "U";
        guidelabel.setText(msg);
        textarea.setText("");
        GUIClient.startTime = new Date();
    }

    // when click connect button
    private void connectActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("Server unreachable! Please try again later.");
        }
        if (client.connect()){
            guidelabel.setText("Connected! Please choose a mode below!");
        }
        else{
            guidelabel.setText("Server unreachable! Please try again later.");
        }
        GUIClient.startTime = new Date();
    }

    // when click disconnect button
    private void disconnectActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!client.status()){
            guidelabel.setText("You are offline! Please click Connect button below!");
            return;
        }
        client.disconnect();
        guidelabel.setText("Disconnected!");

    }

    private void DisconnectActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Hongzhi Fu
        ResourceBundle bundle = ResourceBundle.getBundle("GUI");
        dialogPane = new JPanel();
        panel = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        textarea = new JEditorPane();
        guidelabel = new JLabel();
        scrollPane2 = new JScrollPane();
        textbox = new JEditorPane();
        label2 = new JLabel();
        add = new JButton();
        query = new JButton();
        connect = new JButton();
        remove = new JButton();
        update = new JButton();
        disconnect = new JButton();
        submit = new JButton();
        clear = new JButton();
        cancel = new JButton();

        //======== this ========
        setTitle(bundle.getString("GUIClient.this.title"));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax
            .swing.border.EmptyBorder(0,0,0,0), "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e",javax.swing
            .border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.
            Font("D\u0069al\u006fg",java.awt.Font.BOLD,12),java.awt.Color.red
            ),dialogPane. getBorder()));dialogPane. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override
            public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062or\u0064er".equals(e.getPropertyName(
            )))throw new RuntimeException();}});
            dialogPane.setLayout(new BorderLayout());

            //======== panel ========
            {

                //---- label1 ----
                label1.setText(bundle.getString("GUIClient.label1.text"));
                label1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 24));
                label1.setHorizontalAlignment(SwingConstants.CENTER);

                //======== scrollPane1 ========
                {

                    //---- textarea ----
                    textarea.setEditable(false);
                    scrollPane1.setViewportView(textarea);
                }

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(textbox);
                }

                //---- label2 ----
                label2.setText(bundle.getString("GUIClient.label2.text"));
                label2.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 18));

                //---- add ----
                add.setText(bundle.getString("GUIClient.add.text"));
                add.addActionListener(e -> {
			submitActionPerformed(e);
			addActionPerformed(e);
		});

                //---- query ----
                query.setText(bundle.getString("GUIClient.query.text"));
                query.addActionListener(e -> queryActionPerformed(e));

                //---- connect ----
                connect.setText(bundle.getString("GUIClient.connect.text"));
                connect.addActionListener(e -> connectActionPerformed(e));

                //---- remove ----
                remove.setText(bundle.getString("GUIClient.remove.text"));
                remove.addActionListener(e -> removeActionPerformed(e));

                //---- update ----
                update.setText(bundle.getString("GUIClient.update.text"));
                update.addActionListener(e -> updateActionPerformed(e));

                //---- disconnect ----
                disconnect.setText(bundle.getString("GUIClient.disconnect.text"));
                disconnect.addActionListener(e -> {
			DisconnectActionPerformed(e);
			disconnectActionPerformed(e);
		});

                //---- submit ----
                submit.setText(bundle.getString("GUIClient.submit.text"));
                submit.addActionListener(e -> submitActionPerformed(e));

                //---- clear ----
                clear.setText(bundle.getString("GUIClient.clear.text"));
                clear.addActionListener(e -> clearActionPerformed(e));

                //---- cancel ----
                cancel.setText(bundle.getString("GUIClient.cancel.text"));
                cancel.addActionListener(e -> cancelActionPerformed(e));

                GroupLayout panelLayout = new GroupLayout(panel);
                panel.setLayout(panelLayout);
                panelLayout.setHorizontalGroup(
                    panelLayout.createParallelGroup()
                        .addGroup(panelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panelLayout.createParallelGroup()
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addGap(55, 55, 55)
                                    .addComponent(guidelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(panelLayout.createParallelGroup()
                                        .addGroup(panelLayout.createSequentialGroup()
                                            .addComponent(remove, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(update, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(disconnect, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelLayout.createSequentialGroup()
                                            .addComponent(query, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(add, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(connect, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelLayout.createSequentialGroup()
                                            .addComponent(submit, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(clear, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(cancel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))))
                                .addComponent(label2, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addComponent(label1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panelLayout.setVerticalGroup(
                    panelLayout.createParallelGroup()
                        .addGroup(panelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(label1)
                            .addGap(26, 26, 26)
                            .addGroup(panelLayout.createParallelGroup()
                                .addGroup(panelLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(guidelabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                    .addGap(12, 12, 12)
                                    .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(submit, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clear, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cancel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(query, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(add, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(connect, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(disconnect, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(update, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(remove, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                            .addGap(14, 14, 14))
                );
            }
            dialogPane.add(panel, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Hongzhi Fu
    private JPanel dialogPane;
    private JPanel panel;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JEditorPane textarea;
    private JLabel guidelabel;
    private JScrollPane scrollPane2;
    private JEditorPane textbox;
    private JLabel label2;
    private JButton add;
    private JButton query;
    private JButton connect;
    private JButton remove;
    private JButton update;
    private JButton disconnect;
    private JButton submit;
    private JButton clear;
    private JButton cancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    // timer for disconnection automatically
    private static void setTimer(){
        GUIClient.startTime = new Date();
        while (true){
            Date now = new Date();
            long duration = (now.getTime() - GUIClient.startTime.getTime()) / 1000;
            System.out.print("");
            if (duration > 60){
                client.disconnect();
                break;
            }
        }
    }

    public static void main(String[] args){
        DictionaryClient client = new DictionaryClient(args[0], Integer.parseInt(args[1]));
        GUIClient gui = new GUIClient(client);
        gui.setVisible(true);
        boolean status = client.connect();
        if (!status){
            gui.guidelabel.setText("Please connect to the server!");
        }
        else{
            gui.guidelabel.setText("Please choose a mode below!");
        }
        while (true){
            setTimer();
        }
    }
}
