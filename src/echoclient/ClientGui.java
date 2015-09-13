/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoclient;

import echoserver.ProtocolStrings;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import javax.swing.DefaultListModel;
import javax.swing.text.AttributeSet;
import java.util.Observer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionListener;
import utils.Utils;



enum Window {

    LOGIN(true),
    CHAT(true);

    private final boolean value;

    // Constructor
    Window(boolean b) {
        value = b;
    }

    // Methods ->
    boolean open() {
        return value;
    }

} // End of enum window

/**
 *
 * @author bo
 */
public class ClientGui extends javax.swing.JFrame implements Observer {

    // Constants from Enum class (see above)
    // used for selecting between panels (windows)
    private Window window;

    private String userName = "";

    // Save this :: 
    private ArrayList<String> msgArr = new ArrayList<>();
    private ArrayList<String> nameArr = new ArrayList<>();
    private List<ArrayList<String>> textArea = new ArrayList<>();

    private class Area {

        public static final int MSG = 0;
        public static final int NAMES = 1;
    }

    // Set up list model :: for the lists in the GUI
    DefaultListModel<String> listModelReceivers = new DefaultListModel<>();
    DefaultListModel<String> listModelOnlineUsers = new DefaultListModel<>();

    private EchoClient eClient = new EchoClient(this);
    
    //private static final Properties properties = Utils.initProperties("server.properties");
    
     String portNumber; // = properties.getProperty("port");
     String serverIp; // = properties.getProperty("serverIp");
    
     Thread thread;
     
    /**
     * Creates new form NewJFrame
     */
    // Constructor
    public ClientGui() throws IOException {
        initComponents();

        textArea.add(msgArr);
        textArea.add(nameArr);

        jListOnlineUseres.setModel(listModelOnlineUsers);
        jListRecivers.setModel(listModelReceivers);

        portNumber = jTextField_PortNumber.getText();
        serverIp = jTextField_Address.getText();
        
        jTextField_PortNumber.setText(portNumber);
        jTextField_Address.setText(serverIp);
        
        // ---------------------------
//        eClient.connect(serverIp, Integer.parseInt(portNumber));
//        // eClient.connect("athinodoros.cloudapp.net", 9090);
//        new Thread(eClient).start();
        //-----------------------------------------
        jLabel_PortNumber.setText(portNumber);
        jLabel_Address.setText(serverIp);
        
        selectWindow(window.LOGIN);
    }

    private void selectWindow(Window window) {

        // Tab1 :: panel visibilit
        jPanel1_Login.setVisible(false);
        jPanel2_Chat.setVisible(false);
        
         

        switch (window) {

            case LOGIN:
                jPanel1_Login.setVisible(true);
                break;

            case CHAT:
                jPanel2_Chat.setVisible(true);
                break;

        } // End of switch()
    } // End of window()

    @Override
    public void update(Observable o, Object arg) {

       
        echoclient.Rx rx = (echoclient.Rx) arg;
        
        switch(rx.getCommand()) {
            
            case ProtocolStrings.USERLIST :
                updateOnlineUserList(rx.getOnlineUsers());
                break;
            case ProtocolStrings.MSG :
                updateTextArea(rx.getMessage(), rx.getSender());
                break;
            default :
                System.err.println("something wrong in gui switch case");
            
            
        } // End of swhitch
        

    } // End of update

    // ------------------------------- send ----------------------------
    
    private void sendMSG(String msg) {
        
        if(! listModelReceivers.isEmpty()) {
            
            echoclient.Tx tx = new echoclient.Tx(ProtocolStrings.MSG);

            ArrayList<String> reseivers = new ArrayList<>();
            
            if( (listModelOnlineUsers.size() == listModelReceivers.size()) ) {
                
                reseivers.add(ProtocolStrings.STAR);
            
            } else {
            
                for (int i = 0; i < listModelReceivers.getSize(); i++) {
                    reseivers.add(listModelReceivers.get(i));
                    System.out.println(reseivers.toString());
                }
            }
            tx.setReceivers(reseivers);
           
            tx.setMessage(msg);

            eClient.createMessageString(tx);
            
            updateTextArea(msg, userName);
        }
    } // End of sendMSG
    
    
    private void sendUserName(){
        
        echoclient.Tx tx = new echoclient.Tx(ProtocolStrings.USER);

        tx.setName(userName);
        
       eClient.createMessageString(tx);
    }
    
    private void sendStop() {
        
        echoclient.Tx tx = new echoclient.Tx(ProtocolStrings.STOP);
        
        eClient.createMessageString(tx);
    }

    
    // ----------------------------------------------------------------
    
    
    private void updateOnlineUserList(List<String> users) {

        DefaultListModel<String> temp = new DefaultListModel();
        temp = listModelReceivers;
        
          // delete any content form lists
        listModelOnlineUsers.clear();
        listModelReceivers.clear();
        
        for (String user : users) {

            listModelOnlineUsers.addElement(user);
            
        }
        
        for (int i = 0; i < temp.getSize(); i++) {
            
            if( ! listModelOnlineUsers.contains(temp.elementAt(i))) {
                
                temp.removeElementAt(i);
            }
        }
       
        listModelReceivers = temp;
    }

    
    private /* synchronized */ void updateTextArea(String msg, String uName) {

        final String UNAME = "[" + uName + " says:]";

        // take a copy :: can be saved later 
        textArea.get(Area.MSG).add(msg);
        textArea.get(Area.NAMES).add(UNAME);

        // update text Area
        jTextAreaChatLog.append(UNAME + "\n");
        jTextAreaChatLog.append(msg + "\n");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane = new javax.swing.JLayeredPane();
        jPanel1_Login = new javax.swing.JPanel();
        jButton1_Login = new javax.swing.JButton();
        jTextField1_UserName = new javax.swing.JTextField();
        jLabel1_UserName = new javax.swing.JLabel();
        jPanel1_portAndAdress = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_PortNumber = new javax.swing.JTextField();
        jTextField_Address = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_PortNumber = new javax.swing.JLabel();
        jLabel_Address = new javax.swing.JLabel();
        jButton_SetPortAndAddress = new javax.swing.JButton();
        jPanel2_Chat = new javax.swing.JPanel();
        jButton2_LogOut = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaChatLog = new javax.swing.JTextArea();
        jTextFieldMessage = new javax.swing.JTextField();
        jButtonSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListRecivers = new javax.swing.JList();
        jButtonSelect = new javax.swing.JButton();
        jButtonDeselect = new javax.swing.JButton();
        jButtonDeselectAll = new javax.swing.JButton();
        jButtonSelectAll = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListOnlineUseres = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4_Login_as = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1_Login.setText("LogIn");
        jButton1_Login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1_LoginMousePressed(evt);
            }
        });
        jButton1_Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_LoginActionPerformed(evt);
            }
        });

        jLabel1_UserName.setText("Enter your user name here:");

        jPanel1_portAndAdress.setBorder(javax.swing.BorderFactory.createTitledBorder("Enter port and addess"));
        jPanel1_portAndAdress.setToolTipText("Enter port and adress");

        jLabel4.setText("Enter a new port number");

        jLabel5.setText("Enter a new address");

        jTextField_PortNumber.setText("jTextField1");

        jTextField_Address.setText("jTextField2");

        jLabel6.setText("Port: ");

        jLabel7.setText("Address: ");

        jLabel_PortNumber.setText("jLabel8");

        jLabel_Address.setText("jLabel9");

        jButton_SetPortAndAddress.setText("Set");
        jButton_SetPortAndAddress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_SetPortAndAddressMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1_portAndAdressLayout = new javax.swing.GroupLayout(jPanel1_portAndAdress);
        jPanel1_portAndAdress.setLayout(jPanel1_portAndAdressLayout);
        jPanel1_portAndAdressLayout.setHorizontalGroup(
            jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_portAndAdressLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1_portAndAdressLayout.createSequentialGroup()
                        .addGroup(jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_PortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_Address, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1_portAndAdressLayout.createSequentialGroup()
                                .addGroup(jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_PortNumber)
                                    .addComponent(jLabel_Address))))
                        .addGap(29, 76, Short.MAX_VALUE))
                    .addGroup(jPanel1_portAndAdressLayout.createSequentialGroup()
                        .addGroup(jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_SetPortAndAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1_portAndAdressLayout.setVerticalGroup(
            jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_portAndAdressLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel_PortNumber))
                .addGap(18, 18, 18)
                .addGroup(jPanel1_portAndAdressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel_Address))
                .addGap(76, 76, 76)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_PortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jButton_SetPortAndAddress)
                .addGap(30, 30, 30))
        );

        jLabel_PortNumber.getAccessibleContext().setAccessibleName("jLabel_PortNumber");
        jLabel_Address.getAccessibleContext().setAccessibleName("jLabel_Address");

        javax.swing.GroupLayout jPanel1_LoginLayout = new javax.swing.GroupLayout(jPanel1_Login);
        jPanel1_Login.setLayout(jPanel1_LoginLayout);
        jPanel1_LoginLayout.setHorizontalGroup(
            jPanel1_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1_LoginLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(jPanel1_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1_UserName)
                    .addComponent(jButton1_Login, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(jPanel1_portAndAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1_LoginLayout.setVerticalGroup(
            jPanel1_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1_LoginLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel1_LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1_portAndAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1_LoginLayout.createSequentialGroup()
                        .addComponent(jLabel1_UserName)
                        .addGap(38, 38, 38)
                        .addComponent(jTextField1_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton1_Login)
                        .addGap(12, 12, 12)))
                .addGap(38, 38, 38))
        );

        jPanel1_portAndAdress.getAccessibleContext().setAccessibleName("Enter port and adress");
        jPanel1_portAndAdress.getAccessibleContext().setAccessibleDescription("");

        jButton2_LogOut.setText("Log out");
        jButton2_LogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2_LogOutMousePressed(evt);
            }
        });
        jButton2_LogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_LogOutActionPerformed(evt);
            }
        });

        jTextAreaChatLog.setColumns(20);
        jTextAreaChatLog.setRows(5);
        jTextAreaChatLog.setFocusable(false);
        jScrollPane1.setViewportView(jTextAreaChatLog);

        jTextFieldMessage.setText("jTextField1");

        jButtonSend.setText("Send");
        jButtonSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonSendMousePressed(evt);
            }
        });

        jListRecivers.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListRecivers);

        jButtonSelect.setText("<- Select");
        jButtonSelect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonSelectMousePressed(evt);
            }
        });

        jButtonDeselect.setText(" Deselect ->");
        jButtonDeselect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonDeselectMousePressed(evt);
            }
        });

        jButtonDeselectAll.setText("Deselect All ->");
        jButtonDeselectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonDeselectAllMousePressed(evt);
            }
        });

        jButtonSelectAll.setText("<- Select All");
        jButtonSelectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonSelectAllMousePressed(evt);
            }
        });

        jScrollPane3.setViewportView(jListOnlineUseres);

        jLabel1.setText("Chat messages:");

        jLabel2.setText("Receivers");

        jLabel3.setText("Online useres:");

        jLabel4_Login_as.setText(" ");

        javax.swing.GroupLayout jPanel2_ChatLayout = new javax.swing.GroupLayout(jPanel2_Chat);
        jPanel2_Chat.setLayout(jPanel2_ChatLayout);
        jPanel2_ChatLayout.setHorizontalGroup(
            jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2_ChatLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2_ChatLayout.createSequentialGroup()
                        .addComponent(jButtonSend, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(234, 234, 234)
                        .addComponent(jLabel4_Login_as, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(229, 229, 229)
                        .addComponent(jButton2_LogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2_ChatLayout.createSequentialGroup()
                        .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2_ChatLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel1)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2_ChatLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(279, 279, 279))
                            .addGroup(jPanel2_ChatLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonDeselectAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonDeselect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonSelectAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonSelect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2_ChatLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(87, 87, 87)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel2_ChatLayout.setVerticalGroup(
            jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2_ChatLayout.createSequentialGroup()
                .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2_ChatLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jButtonSelect)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDeselect)
                        .addGap(115, 115, 115)
                        .addComponent(jButtonSelectAll)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDeselectAll))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2_ChatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addComponent(jLabel1))
                        .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2_ChatLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2_ChatLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSend, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2_LogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4_Login_as, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jLayeredPaneLayout = new javax.swing.GroupLayout(jLayeredPane);
        jLayeredPane.setLayout(jLayeredPaneLayout);
        jLayeredPaneLayout.setHorizontalGroup(
            jLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1_Login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2_Chat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPaneLayout.setVerticalGroup(
            jLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1_Login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2_Chat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jLayeredPane.setLayer(jPanel1_Login, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.setLayer(jPanel2_Chat, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1_LoginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1_LoginMousePressed

        try {
            // Login :: After this putton is pressed go to CHAT panel
            userName = jTextField1_UserName.getText();
            jLabel4_Login_as.setText("You are logged in as " + userName);
            
            
            
            EchoClient newClient = new EchoClient(this);
            eClient = newClient;
            
            newClient.connect(serverIp, Integer.parseInt(portNumber));
            
            thread = new Thread(newClient);
            thread.start();
            
            
            // send userName to Client
            sendUserName(); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            
            jTextFieldMessage.setText("");
            selectWindow(window.CHAT);
        } catch (IOException ex) {
            Logger.getLogger(ClientGui.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1_LoginMousePressed

    private void jButton2_LogOutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2_LogOutMousePressed

        // LoginOut :: After this putton is pressed go to LOGIN panel
        // Send stop Message to Client
        sendStop();
        
        selectWindow(window.LOGIN);

    }//GEN-LAST:event_jButton2_LogOutMousePressed

    private void jButton2_LogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_LogOutActionPerformed

    }//GEN-LAST:event_jButton2_LogOutActionPerformed

    private void jButton1_LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_LoginActionPerformed

    }//GEN-LAST:event_jButton1_LoginActionPerformed

    private void jButtonSendMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSendMousePressed
        // Send Button pressed :: send message

        String msg = jTextFieldMessage.getText();

        jTextFieldMessage.setText("");
        sendMSG(msg);

        // ToDo :: send msg to Client

    }//GEN-LAST:event_jButtonSendMousePressed

    private void jButtonSelectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelectMousePressed

        // Select an online user to Receivers
        
        // Find the index the user has choosen 
        int onlineUserIndex = jListOnlineUseres.getSelectedIndex();

        // find the onlineUser in the listModel
        String onlineUser = listModelOnlineUsers.elementAt(onlineUserIndex);

        // remove onlineUser from listModel
        //listModelOnlineUsers.remove(onlineUserIndex);

        // Add useronline to Receivers
        
        if(! listModelReceivers.contains(onlineUser)) {
        
            listModelReceivers.addElement(onlineUser);
        }

    }//GEN-LAST:event_jButtonSelectMousePressed

    private void jButtonDeselectMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDeselectMousePressed
       
        // Receiver :: remove a Receiver and put the Receiver in OnlineUsers
        
        // Find the index the user has choosen 
        int onlineUserIndex = jListRecivers.getSelectedIndex();

        // find the onlineUser in the listModel
        String receiver = listModelReceivers.elementAt(onlineUserIndex);

        // remove onlineUser from listModel
        listModelReceivers.remove(onlineUserIndex);

        // Add useronline to Receivers
        //listModelOnlineUsers.addElement(receiver);
        
      
        
        
    }//GEN-LAST:event_jButtonDeselectMousePressed

    private void jButtonSelectAllMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSelectAllMousePressed
        
        // SelectAll (onlineUsers) :: remove a All OnlineUsers and put the OnlineUsers in Receicers
        
        listModelReceivers.clear();
        for (int i = 0; i < listModelOnlineUsers.getSize(); i++) {
            
            listModelReceivers.addElement(listModelOnlineUsers.getElementAt(i));
        }
        
        
        
    }//GEN-LAST:event_jButtonSelectAllMousePressed

    private void jButtonDeselectAllMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDeselectAllMousePressed
        
        // SelectAll (receivers) :: remove a All OnlineUsers and put the OnlineUsers in Receicers
        
        listModelReceivers.clear();
        
    }//GEN-LAST:event_jButtonDeselectAllMousePressed

    private void jButton_SetPortAndAddressMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SetPortAndAddressMousePressed
        
//        try {
            // Set port Number and Address
            
            portNumber  = jTextField_PortNumber.getText();
            jLabel_PortNumber.setText(portNumber);
            
            serverIp = jTextField_Address.getText();
            jLabel_Address.setText(serverIp);
            
//            EchoClient newClient = new EchoClient(this);
//            eClient = newClient;
//            newClient.connect(serverIp, Integer.parseInt(portNumber));
//        
//            new Thread(newClient).start();
           
        
//        } catch (IOException ex) {
//            Logger.getLogger(ClientGui.class.getName()).log(Level.SEVERE, null, ex);
//        }
         
        
    }//GEN-LAST:event_jButton_SetPortAndAddressMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ClientGui().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(ClientGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_Login;
    private javax.swing.JButton jButton2_LogOut;
    private javax.swing.JButton jButtonDeselect;
    private javax.swing.JButton jButtonDeselectAll;
    private javax.swing.JButton jButtonSelect;
    private javax.swing.JButton jButtonSelectAll;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JButton jButton_SetPortAndAddress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel1_UserName;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel4_Login_as;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_Address;
    private javax.swing.JLabel jLabel_PortNumber;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JList jListOnlineUseres;
    private javax.swing.JList jListRecivers;
    private javax.swing.JPanel jPanel1_Login;
    private javax.swing.JPanel jPanel1_portAndAdress;
    private javax.swing.JPanel jPanel2_Chat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaChatLog;
    private javax.swing.JTextField jTextField1_UserName;
    private javax.swing.JTextField jTextFieldMessage;
    private javax.swing.JTextField jTextField_Address;
    private javax.swing.JTextField jTextField_PortNumber;
    // End of variables declaration//GEN-END:variables
}
