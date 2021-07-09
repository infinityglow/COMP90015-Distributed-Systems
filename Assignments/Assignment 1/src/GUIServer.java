import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/*
 * Created by JFormDesigner on Fri Apr 16 14:27:31 CST 2021
 */

/**
 * @author unknown
 */
public class GUIServer extends JFrame {

    private static DictionaryServer server;
    private static boolean status;

    public GUIServer(DictionaryServer server) {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIServer.server = server;
        status = false;
    }

    // when click connection button
    private void connectActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (!status){
            server.connect();
            status = true;
        }
        else {
            textarea.setText(textarea.getText() + "Dictionary server waiting for connections...\n");
        }

    }

    // when click exit button
    private void disconnectActionPerformed(ActionEvent e) {
        // TODO add your code here
        if (status){
            server.disconnect();
            textarea.setText("Dictionary server disconnected");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        ResourceBundle bundle = ResourceBundle.getBundle("GUI");
        panel = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        connect = new JButton();
        disconnect = new JButton();
        scrollPane1 = new JScrollPane();
        textarea = new JEditorPane();

        //======== this ========
        setTitle(bundle.getString("GUIServer.this.title"));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel ========
        {
            panel.setBorder(new EmptyBorder(12, 12, 12, 12));
            panel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing.
            border. EmptyBorder( 0, 0, 0, 0) , "", javax. swing. border. TitledBorder. CENTER
            , javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dialo\u0067" ,java .awt .Font
            .BOLD ,12 ), java. awt. Color. red) ,panel. getBorder( )) ); panel. addPropertyChangeListener (
            new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072"
            .equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
            panel.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //---- label1 ----
                label1.setText(bundle.getString("GUIServer.label1.text"));
                label1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 24));
                label1.setHorizontalAlignment(SwingConstants.CENTER);

                //---- connect ----
                connect.setText(bundle.getString("GUIServer.connect.text"));
                connect.addActionListener(e -> connectActionPerformed(e));

                //---- disconnect ----
                disconnect.setText(bundle.getString("GUIServer.disconnect.text"));
                disconnect.addActionListener(e -> disconnectActionPerformed(e));

                //======== scrollPane1 ========
                {

                    //---- textarea ----
                    textarea.setEditable(false);
                    scrollPane1.setViewportView(textarea);
                }

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addGap(61, 61, 61)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.LEADING, contentPanelLayout.createSequentialGroup()
                                    .addGap(41, 41, 41)
                                    .addComponent(scrollPane1))
                                .addGroup(GroupLayout.Alignment.LEADING, contentPanelLayout.createSequentialGroup()
                                    .addGap(71, 71, 71)
                                    .addComponent(connect, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(disconnect, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)))
                            .addContainerGap(51, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addComponent(label1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(connect, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addComponent(disconnect, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                            .addGap(19, 19, 19))
                );
            }
            panel.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel panel;
    private JPanel contentPanel;
    private JLabel label1;
    private JButton connect;
    private JButton disconnect;
    private JScrollPane scrollPane1;
    private static JEditorPane textarea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

//    public static void setTextAreaText(String str){
//        textarea.setText(str);
//    }

    public static void setTextArea(String str){
        textarea.setText(str);
    }

    public static String getTextArea(){
        return textarea.getText();
    }

    public static void main(String[] args){
        try {
            DictionaryServer server = new DictionaryServer(Integer.parseInt(args[0]), args[1]);
            GUIServer gui = new GUIServer(server);
            gui.setVisible(true);
        }
        catch (Exception e){
            try {
                DictionaryServer server = new DictionaryServer(Integer.parseInt(args[0]));
                GUIServer gui = new GUIServer(server);
                gui.setVisible(true);
            }
            catch (Exception e1){
                DictionaryServer server = new DictionaryServer();
                GUIServer gui = new GUIServer(server);
                gui.setVisible(true);
            }
        }

        while(true){
            System.out.print("");
            while (status){
                server.accept();
                status = false;
            }
        }
    }
}
