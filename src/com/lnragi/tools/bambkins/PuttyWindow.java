package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class PuttyWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -226544733552663675L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = new Font("Calibri", Font.BOLD, 14);
	JTextField ip, username, password, port;
	JPanel topPanel, inputPanel, bottomPanel, centerPanel, textAreaPanel;
	JButton button, monitButton, momlogButton, udsAgentButton, startService, stopService, restartService;
	JLabel userLbl, pwdLbl, ipLbl, portLbl, progressText;
	JTextArea textArea;

	public PuttyWindow() {
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		initializeHomeWindow();
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3, 10));
		topPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		topPanel.setBorder(BorderFactory.createTitledBorder(" MONITOR "));

		textAreaPanel = new JPanel();
		textAreaPanel.setPreferredSize(screenSize);
		textArea = new JTextArea(25, 146);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		textArea.setBorder(null);
		textArea.setToolTipText("You can see console output here...");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setAutoscrolls(true);
		textAreaPanel.setAutoscrolls(true);
		textAreaPanel.add(new JScrollPane(textArea));

		inputPanel = new JPanel();
		// bottomPanel.setLayout(new GridLayout(5, 10));
		ipLbl = new JLabel("Hostname");
		ip = new JTextField(20);
		ipLbl.setLabelFor(ip);
		inputPanel.add(ipLbl);
		inputPanel.add(ip);

		userLbl = new JLabel("Username");
		username = new JTextField(20);
		userLbl.setLabelFor(username);
		inputPanel.add(userLbl);
		inputPanel.add(username);
		pwdLbl = new JLabel("Password");
		password = new JPasswordField(20);
		pwdLbl.setLabelFor(password);
		inputPanel.add(pwdLbl);
		inputPanel.add(password);
		portLbl = new JLabel("Port");
		port = new JTextField(20);
		portLbl.setLabelFor(port);
		inputPanel.add(portLbl);
		inputPanel.add(port);

		button = new JButton("LOGIN");
		button.setBackground(Color.BLUE);
		button.setActionCommand("login");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PuttyLoginThread(ip.getText(), username.getText(), password.getText(),
						Integer.parseInt(port.getText())).start();
			}
		});
		inputPanel.add(button);
		progressText = new JLabel();
		progressText.setText("Ready to connect");

		centerPanel = new JPanel();
		monitButton = new JButton("MONIT SUMMARY");
		monitButton.setBackground(Color.BLUE);
		monitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		startService = new JButton("START SERVICE");
		startService.setBackground(Color.BLUE);
		startService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		stopService = new JButton("STOP SERVICE");
		stopService.setBackground(Color.BLUE);
		stopService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		restartService = new JButton("RESTART SERVICE");
		restartService.setBackground(Color.BLUE);
		restartService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		momlogButton = new JButton("MOM LOGS");
		momlogButton.setBackground(Color.BLUE);
		momlogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		udsAgentButton = new JButton("UDS AGEN LOGS");
		udsAgentButton.setBackground(Color.BLUE);
		udsAgentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		centerPanel.add(monitButton);
		centerPanel.add(startService);
		centerPanel.add(stopService);
		centerPanel.add(restartService);
		centerPanel.add(momlogButton);
		centerPanel.add(udsAgentButton);

		topPanel.add(inputPanel);
		topPanel.add(progressText);
		topPanel.add(centerPanel);
		bottomPanel = new JPanel();
		bottomPanel.add(textAreaPanel);
		getContentPane().add(topPanel, BorderLayout.PAGE_START);
		getContentPane().add(bottomPanel);
	}

	private void initializeHomeWindow() {
		Image icon = Toolkit.getDefaultToolkit().getImage(HomeWindow.class.getResource("/images/favicon.png"));
		setBackground(Color.WHITE);
		setPreferredSize(screenSize);
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Putty");
		setVisible(true);
		pack();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PuttyWindow();
			}
		});

	}

	public class PuttyLoginThread extends Thread {
		private String ip;
		private String username;
		private String password;
		private int port;

		public PuttyLoginThread(String ip, String username, String password, int port) {
			super();
			this.ip = ip;
			this.username = username;
			this.password = password;
			this.port = port;
		}

		public void run() {
			try {
				ImageIcon icon = new ImageIcon(BuildMavenProjectPanel.class.getResource("/images/wait.gif"));
				button.setIcon(icon);
				progressText.setText("Connecting to " + ip + " user with " + username);
				Thread.sleep(3000);
				Properties props = new Properties();
				props.put("StrictHostKeyChecking", "no");
				JSch jsch = new JSch();
				progressText.setText("Authenticating....");
				Thread.sleep(2000);
				Session session = jsch.getSession(username, ip, port);
				progressText.setText("Getting Session.....");
				Thread.sleep(2000);
				session.setTimeout(60000);
				session.setPassword(password);
				session.setConfig(props);
				session.connect();
				progressText.setText("Connected!");
				Thread.sleep(1000);
				button.setIcon(null);
				progressText.setText("Connected [root@" + ip + "]");
			} catch (InterruptedException | JSchException e) {
				progressText.setText(e.getMessage());
				button.setIcon(null);
				e.printStackTrace();
			}
		}
	}
}
