package com.lnragi.tools.bambkins;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class BuildMavenProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5750960361473796433L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = new Font("Calibri", Font.BOLD, 14);
	JPanel targetPanel, buttonPanel, textAreaPanel, statusBarPanel;
	JTextField target;
	JButton submit, browse;
	JFileChooser chooser;
	String choosertitle;
	String line;
	JTextArea textArea;
	JProgressBar progressBar;

	public BuildMavenProjectPanel() {
		JPanel inputPanel = new JPanel();
		// inputPanel.setBackground(Color.white);
//		inputPanel.setLayout(new GridLayout(1, 7));
//		inputPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
//		inputPanel.setBorder(BorderFactory.createTitledBorder(" Enter Details "));

		JPanel outputPanel = new JPanel();
		// outputPanel.setBackground(Color.white);
		outputPanel.setLayout(new GridLayout(1, 100));
		outputPanel.setBorder(BorderFactory.createMatteBorder(45, 8, 12, 8, Color.blue));
		outputPanel.setBorder(BorderFactory.createTitledBorder("      [ OUTPUT ] "));

		targetPanel = new JPanel();
		// targetPanel.setBackground(Color.white);
		targetPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		targetPanel.setBorder(BorderFactory.createTitledBorder(" ENTER DESTINATION "));
		target = new JTextField(100);
		target.setToolTipText("Target directory is mandatory!!!");
		targetPanel.add(target);
		browse = new JButton("BROWSE");
		browse.setBackground(Color.BLUE);
		browse.setActionCommand("maven");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				int option = chooser.showOpenDialog(BuildMavenProjectPanel.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					File sf = chooser.getSelectedFile();
					String filelist = sf.getParent();
					target.setText(filelist);
				}
			}

		});
		targetPanel.add(browse);

		submit = new JButton("SUBMIT");
		submit.setToolTipText("Starts Maven Project generation ");
		submit.setBackground(Color.BLUE);
		submit.setActionCommand("mvngen");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildMavenProject();
			}
		});
		targetPanel.add(submit);
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(100, 24));
		targetPanel.add(progressBar);
		textAreaPanel = new JPanel();
		textAreaPanel.setPreferredSize(screenSize);
		textArea = new JTextArea(25, 146);
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//textArea.setBackground(new Color(128, 0, 128));
		//textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		textArea.setBorder(null);
		textArea.setToolTipText("You can see console output here...");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setAutoscrolls(true);
		textAreaPanel.setAutoscrolls(true);
		textAreaPanel.add(new JScrollPane(textArea));

		inputPanel.add(targetPanel);
		outputPanel.add(textAreaPanel);
		setEnabled(true);
		setPreferredSize(screenSize);
		add(inputPanel);
		add(outputPanel);
	}

	protected void buildMavenProject() {
		String dest = target.getText();
		if (dest.equals("")) {
			JOptionPane.showMessageDialog(null, "Please select POM location", "Error:", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String[] command = { "CMD", "/C", "mvn clean install" };
		TextAreaThread th = new TextAreaThread(null, null, null, dest, command);
		th.start();
	}

	class TextAreaThread extends Thread {
		private String dest;
		private String[] command;
		int completedStatus;
		int size;

		public TextAreaThread(String grpId, String afactId, String template, String dest, String[] command) {
			super();
			this.dest = dest;
			this.command = command;
		}

		public void run() {
			textArea.setText("");
			submit.setEnabled(false);
			submit.setText("HANG TIGHT");
			textArea.append("BREATHE IN....., BREATHE OUT......\n");
			progressBar.setIndeterminate(true);
			ProcessBuilder probuilder = new ProcessBuilder(command);
			probuilder.directory(new File(dest));
			Process process;
			try {
				process = probuilder.start();
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				boolean finishFlag = false;
				while ((line = br.readLine()) != null) {
					finishFlag = false;
					textArea.append(String.valueOf((line)));
					textArea.append("\n");
					finishFlag = true;
				}
				if (finishFlag == true) {
					progressBar.setIndeterminate(false);
					submit.setEnabled(true);
					submit.setText("COMPLETED");
					Thread.sleep(3000);
					submit.setText("SUBMIT");
					ImageIcon icon = new ImageIcon(BuildMavenProjectPanel.class.getResource("/images/ok1.png"));
					JOptionPane.showMessageDialog(null, "BUILD PROCESS COMPLETED", "SUCCESS",
							JOptionPane.PLAIN_MESSAGE, icon);
				}
			} catch (IOException e) {
				progressBar.setIndeterminate(false);
				submit.setEnabled(true);
				try {
					submit.setText("FAILED!!!");
					Thread.sleep(3000);
					submit.setText("SUBMIT");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
