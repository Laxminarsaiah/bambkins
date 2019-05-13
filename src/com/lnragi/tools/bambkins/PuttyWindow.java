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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultCaret;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
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
	JTextField ip, username, password, port, serviceName;
	JPanel topPanel, inputPanel, bottomPanel, centerPanel, textAreaPanel;
	JButton button, monitButton, momlogButton, udsAgentButton, startService, stopService, restartService;
	JLabel userLbl, pwdLbl, ipLbl, portLbl, progressText, serviceLabel;
	JTextArea textArea;
	public static Session session;
	String line;
	JPanel monitSummaryTopPanel, monitTextAreaPanel, monitSummaryBottomPanel, monitSummaryButtonPanel,
			serviceInputPanel;
	JButton cancel, refresh;
	JTextArea monitTextArea;

	public PuttyWindow() {
		progressText = new JLabel();
		new DaemonThread().start();
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
				new DaemonThread().start();
				new PuttyLoginThread(ip.getText(), username.getText(), password.getText(),
						Integer.parseInt(port.getText())).start();
			}
		});
		inputPanel.add(button);

		centerPanel = new JPanel();
		monitButton = new JButton("MONIT SUMMARY");
		monitButton.setBackground(Color.BLUE);
		monitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DaemonThread().start();
				String command = "monit summary";
				if (session == null) {
					JOptionPane.showMessageDialog(null, "No putty session available", "Error:",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				new MonitSummaryFrame();
				new MonitSummaryThread(command).start();
			}
		});

		startService = new JButton("START SERVICE");
		startService.setBackground(Color.BLUE);
		startService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DaemonThread().start();
				serviceInputPanel = new JPanel();
				serviceLabel = new JLabel("Specify service name to start:");
				serviceName = new JTextField(20);
				serviceInputPanel.setLayout(new GridLayout(2, 1));
				serviceInputPanel.add(serviceLabel, BorderLayout.PAGE_START);
				serviceInputPanel.add(serviceName, BorderLayout.PAGE_END);
				int input = JOptionPane.showConfirmDialog(null, serviceInputPanel, "Service",
						JOptionPane.OK_CANCEL_OPTION);
				if (input == 0) {
					String sname = serviceName.getText();
					String command = "monit start " + sname;
					new ServiceThread(command).start();
				}

			}
		});

		stopService = new JButton("STOP SERVICE");
		stopService.setBackground(Color.BLUE);
		stopService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DaemonThread().start();
				serviceInputPanel = new JPanel();
				serviceLabel = new JLabel("Specify service name to stop:");
				serviceName = new JTextField(20);
				serviceInputPanel.setLayout(new GridLayout(2, 1));
				serviceInputPanel.add(serviceLabel, BorderLayout.PAGE_START);
				serviceInputPanel.add(serviceName, BorderLayout.PAGE_END);
				int input = JOptionPane.showConfirmDialog(null, serviceInputPanel, "Service",
						JOptionPane.OK_CANCEL_OPTION);
				if (input == 0) {
					String sname = serviceName.getText();
					String command = "monit stop " + sname;
					new ServiceThread(command).start();
				}
			}
		});

		restartService = new JButton("RESTART SERVICE");
		restartService.setBackground(Color.BLUE);
		restartService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DaemonThread().start();
				serviceInputPanel = new JPanel();
				serviceLabel = new JLabel("Specify service name to restart:");
				serviceName = new JTextField(20);
				serviceInputPanel.setLayout(new GridLayout(2, 1));
				serviceInputPanel.add(serviceLabel, BorderLayout.PAGE_START);
				serviceInputPanel.add(serviceName, BorderLayout.PAGE_END);
				int input = JOptionPane.showConfirmDialog(null, serviceInputPanel, "Service",
						JOptionPane.OK_CANCEL_OPTION);
				if (input == 0) {
					String sname = serviceName.getText();
					String command = "monit restart " + sname;
					new ServiceThread(command).start();
				}
			}
		});
		momlogButton = new JButton("MOM LOGS");
		momlogButton.setBackground(Color.BLUE);
		momlogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DaemonThread().start();
			}
		});

		udsAgentButton = new JButton("UDS AGEN LOGS");
		udsAgentButton.setBackground(Color.BLUE);
		udsAgentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DaemonThread().start();
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
				ImageIcon icon = new ImageIcon(PuttyWindow.class.getResource("/images/wait.gif"));
				// button.setIcon(icon);
				button.setText("Please wait...");
				progressText.setIcon(icon);
				progressText.setText("Connecting to " + ip + " user with " + username);
				Thread.sleep(2000);
				Properties props = new Properties();
				props.put("StrictHostKeyChecking", "no");
				JSch jsch = new JSch();
				progressText.setText("Authenticating....");
				Thread.sleep(1000);
				session = jsch.getSession(username, ip, port);
				progressText.setText("Getting Session.....");
				Thread.sleep(2000);
				session.setTimeout(60000);
				session.setPassword(password);
				session.setConfig(props);
				session.connect();
				progressText.setText("Connected!");
				Thread.sleep(1000);
				progressText.setText("Connected!!! [" + username + "@" + ip + "]");
				button.setText("LOGIN");
				progressText.setIcon(new ImageIcon(PuttyWindow.class.getResource("/images/conn.png")));
			} catch (InterruptedException | JSchException e) {
				progressText.setIcon(new ImageIcon(PuttyWindow.class.getResource("/images/disconn.png")));
				progressText.setText(e.getMessage());
				button.setText("LOGIN");
				e.printStackTrace();
			}
		}
	}

	public class MonitSummaryThread extends Thread {
		private String command;

		public MonitSummaryThread(String command) {
			super();
			this.command = command;
		}

		public void run() {
			monitTextArea.setText("");
			try {
				if (session == null) {
					monitTextArea.append("Please login to putty first.");
				}
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				InputStream in = channel.getInputStream();
				((ChannelExec) channel).setErrStream(System.err);
				channel.connect();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				while ((line = br.readLine()) != null) {
					monitTextArea.append(String.valueOf((line)));
					monitTextArea.append("\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class ServiceThread extends Thread {
		private String command;

		public ServiceThread(String command) {
			super();
			this.command = command;
		}

		public void run() {
			try {
				if (session == null) {
					JOptionPane.showMessageDialog(null, "No putty session available", "Error:",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Channel channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(command);
				InputStream in = channel.getInputStream();
				((ChannelExec) channel).setErrStream(System.err);
				channel.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class DaemonThread extends Thread {

		public DaemonThread() {
			setDaemon(true);
		}

		public void run() {
			try {
				if (session == null) {
					progressText.setIcon(new ImageIcon(PuttyWindow.class.getResource("/images/disconn.png")));
					progressText.setText("Not connected yet!!!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class MonitSummaryFrame extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4894497157528988462L;

		public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		public MonitSummaryFrame() {
			initializeHomeWindow();
			monitSummaryTopPanel = new JPanel();
			monitTextAreaPanel = new JPanel();
			monitTextAreaPanel.setPreferredSize(screenSize);
			monitTextArea = new JTextArea(25, 80);
			DefaultCaret caret = (DefaultCaret) textArea.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			monitTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
			monitTextArea.setBorder(null);
			monitTextArea.setToolTipText("You can see console output here...");
			monitTextArea.setEditable(false);
			monitTextArea.setLineWrap(true);
			monitTextArea.setWrapStyleWord(true);
			monitTextArea.setAutoscrolls(true);
			monitTextAreaPanel.setAutoscrolls(true);
			monitTextAreaPanel.add(new JScrollPane(monitTextArea));
			monitSummaryTopPanel.add(monitTextAreaPanel);
			monitSummaryBottomPanel = new JPanel();
			cancel = new JButton("CANCEL");
			cancel.setBackground(Color.BLUE);
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			refresh = new JButton("REFRESH");
			refresh.setBackground(Color.BLUE);
			refresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String command = "monit summary";
					new MonitSummaryThread(command).start();
				}
			});
			monitSummaryButtonPanel = new JPanel();
			monitSummaryButtonPanel.add(cancel);
			monitSummaryButtonPanel.add(refresh);
			monitSummaryBottomPanel.add(monitSummaryButtonPanel);
			getContentPane().add(monitSummaryTopPanel);
			getContentPane().add(monitSummaryBottomPanel, BorderLayout.SOUTH);
		}

		private void initializeHomeWindow() {
			Image icon = Toolkit.getDefaultToolkit().getImage(HomeWindow.class.getResource("/images/favicon.png"));
			setBackground(Color.WHITE);
			setBounds(400, 150, 200, 200);
			setPreferredSize(new Dimension(750, 400));
			setIconImage(icon);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			if (!ip.getText().equalsIgnoreCase("") && !username.getText().equalsIgnoreCase("")) {
				setTitle("MONIT SUMMARY");
			} else {
				setTitle(username.getText() + "@" + ip.getText());
			}

			setVisible(true);
			setAlwaysOnTop(true);
			pack();
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
