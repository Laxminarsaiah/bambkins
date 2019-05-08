package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class PuttyLoginPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 480056186066167456L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = new Font("Calibri", Font.BOLD, 14);
	JLabel output;
	JTextField ip, username, password, port;
	JPanel topPanel, userPanel, pwdPanel, ipPanel, bottomPanel, buttonPanel, portPanel;
	JProgressBar progressBar;
	JButton button, cancel;
	JLabel userLbl, pwdLbl, ipLbl, portLbl, progressText;

	public PuttyLoginPanel() {
		initializeHomeWindow();
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3,10));
		//topPanel.setPreferredSize(new Dimension(screenSize.width, screenSize.height/15));
		topPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		topPanel.setBorder(BorderFactory.createTitledBorder(" MONITOR "));

		button = new JButton("LOGIN");
		button.setBackground(Color.BLUE);
		button.setActionCommand("login");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jOptionPaneMultiInput();
			}
		});
		progressText = new JLabel();
		bottomPanel = new JPanel();
		bottomPanel.add(progressText);
		topPanel.add(button);
		topPanel.add(bottomPanel, BorderLayout.SOUTH);
		add(button,BorderLayout.EAST);
		add(progressText,BorderLayout.LINE_END);
	}

	protected void jOptionPaneMultiInput() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(5, 10));
		ipLbl = new JLabel("Hostname");
		ip = new JTextField(20);
		ipLbl.setLabelFor(ip);
		bottomPanel.add(ipLbl);
		bottomPanel.add(ip);
		userLbl = new JLabel("Username");
		username = new JTextField(20);
		userLbl.setLabelFor(username);
		bottomPanel.add(userLbl);
		bottomPanel.add(username);
		pwdLbl = new JLabel("Password");
		password = new JPasswordField(20);
		pwdLbl.setLabelFor(password);
		bottomPanel.add(pwdLbl);
		bottomPanel.add(password);
		portLbl = new JLabel("Port");
		port = new JTextField(20);
		portLbl.setLabelFor(port);
		bottomPanel.add(portLbl);
		bottomPanel.add(port);
		int result = JOptionPane.showConfirmDialog(null, bottomPanel, "Enter Details:", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String ipa = ip.getText();
			String user = username.getText();
			String pwd = password.getText();
			int prt = Integer.parseInt(port.getText());
			loginPutty(ipa, user, pwd, prt);
		}

	}

	private void loginPutty(String ipa, String user, String pwd, int prt) {
		MonitorThread t = new MonitorThread(ipa, user, pwd, prt);
		t.start();
	}

	private void initializeHomeWindow() {
		setBackground(Color.WHITE);
		setBounds(400, 150, 200, 200);
		setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 3));
		setVisible(true);
		setEnabled(true);
		setPreferredSize(screenSize);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public class MonitorThread extends Thread {
		private String ip;
		private String username;
		private String password;
		private int port;

		public MonitorThread(String ip, String username, String password, int port) {
			super();
			this.ip = ip;
			this.username = username;
			this.password = password;
			this.port = port;
		}

		public void run() {
			try {
				progressText.setText("Connecting to " + ip + " user with " + username);
				Thread.sleep(2000);
				Properties props = new Properties();
				props.put("StrictHostKeyChecking", "no");
				JSch jsch = new JSch();
				progressText.setText("Athentication....");
				Thread.sleep(2000);
				Session session = jsch.getSession(username, ip, port);
				progressText.setText("Getting Session");
				Thread.sleep(2000);
				session.setTimeout(60000);
				session.setPassword(password);
				session.setConfig(props);
				session.connect();
				progressText.setText("Connected!");
				Thread.sleep(2000);
			} catch (InterruptedException | JSchException e) {
				e.printStackTrace();
			}
		}
	}
}
