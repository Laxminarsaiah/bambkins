package com.test;

import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class RPiClient {

private final JButton sendButton = new JButton("Send CMD");
private JTextField sendField;

public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
        public void run() {
            try {
                new RPiClient();
            } catch (Exception e) {
                System.out.print("failed to run");
                e.printStackTrace();
            }
        }
    });
}

public RPiClient() {
    buildGUI(); 
    connectSSH();
    actionListeners();
}

public void buildGUI(){

    JFrame mainFrame = new JFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setBounds(100, 100, 900, 900);
    mainFrame.getContentPane().setLayout(null);
    JPanel panel = new JPanel();
    panel.setBounds(10, 11, 864, 699);
    mainFrame.getContentPane().add(panel);
    mainFrame.setVisible(true);
    panel.setLayout(null);
    sendField = new JTextField();
    sendField.setText("Enter Command Then Click Send");
    sendField.setBounds(565, 261, 299, 169);
    panel.add(sendField);
    sendField.setColumns(10);
    panel.add(sendField);
    panel.add(sendButton);
    sendButton.setBounds(565, 441, 81, 23);
    JTextArea ta = new JTextArea(50,50);
    TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );

    JScrollPane scroll = new JScrollPane(ta);
    panel.add(scroll);
    scroll.setBounds(132, 5, 423, 906);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

//    PrintStream ps = new PrintStream( taos );
//    System.setOut( ps );
//    System.setErr( ps );

}

public void connectSSH(){
    try{

        JSch jsch=new JSch();  

        String host="192.168.0.x";
        String user="root";
        String passText = "top secret";

        Session session=jsch.getSession(user, host, 22);
        session.setPassword(passText);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        Channel channel=session.openChannel("shell");
        channel.setInputStream(System.in);
        channel.setOutputStream(System.out);

        channel.connect();
        }
        catch(Exception e){
          System.out.println(e);
        }
      }

      public void actionListeners()
        {
            sendButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent connectn2ServerE)
                    {

                    }
                }); 
      }
}