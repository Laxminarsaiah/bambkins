package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class GenerateMavenProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2349027912597242316L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = new Font("Calibri", Font.BOLD, 14);
	JPanel grpPanel, artifactPanel, templatePanel, targetPanel, buttonPanel, textAreaPanel, statusBarPanel,
			submitMainPanel;
	JTextField groupId, artifactId, target;
	JComboBox<String> artifactTemplate;
	JButton submit;
	JFileChooser chooser;
	String choosertitle;
	String line;
	JTextArea textArea;
	JProgressBar progressBar;
	JLabel loadingLabel;

	public GenerateMavenProjectPanel() {
		JPanel inputPanel = new JPanel();

		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout(1, 100));
		outputPanel.setBorder(BorderFactory.createMatteBorder(45, 8, 12, 8, Color.blue));
		outputPanel.setBorder(BorderFactory.createTitledBorder("      [ OUTPUT ] "));
		grpPanel = new JPanel();
		grpPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		grpPanel.setBorder(BorderFactory.createTitledBorder(" ENTER GROUP ID "));
		groupId = new JTextField(28);
		groupId.setToolTipText("Group id is mandatory!!!");
		grpPanel.add(groupId);

		artifactPanel = new JPanel();
		artifactPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		artifactPanel.setBorder(BorderFactory.createTitledBorder(" ENTER ARTIFACT ID "));
		artifactId = new JTextField(28);
		artifactId.setToolTipText("Artfact id is mandatory!!!");
		artifactPanel.add(artifactId);

		templatePanel = new JPanel();
		templatePanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		templatePanel.setBorder(BorderFactory.createTitledBorder(" SELECT TEMPLATE "));
		String[] templates = new String[] { "-----Select-----", "maven-archetype-archetype",
				"maven-archetype-j2ee-simple", "maven-archetype-plugin", "maven-archetype-plugin-site",
				"maven-archetype-portlet", "maven-archetype-quickstart", "maven-archetype-simple",
				"maven-archetype-site", "maven-archetype-site-simple", "maven-archetype-site-skin",
				"maven-archetype-webapp" };

		artifactTemplate = new JComboBox<String>(templates);
		artifactTemplate.setToolTipText("Artifact template is mandatory!!!");
		templatePanel.add(artifactTemplate);

		targetPanel = new JPanel();
		targetPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		targetPanel.setBorder(BorderFactory.createTitledBorder(" ENTER DESTINATION "));
		target = new JTextField(28);
		target.setToolTipText("Target directory is mandatory!!!");
		targetPanel.add(target);

		submitMainPanel = new JPanel();
		submitMainPanel.setLayout(new GridLayout(1, 3));
		// submitMainPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8,
		// Color.blue));
		// submitMainPanel.setBorder(BorderFactory.createTitledBorder("CLICK ME"));

		statusBarPanel = new JPanel();
		statusBarPanel.setLayout(new GridLayout(2, 100));
		statusBarPanel.setPreferredSize(new Dimension(100, 20));

		submit = new JButton("SUBMIT");
		submit.setPreferredSize(new Dimension(180, 40));
		submit.setBackground(Color.BLUE);
		submit.setToolTipText("Starts Maven Project generation ");
		submit.setActionCommand("mvngen");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateMavenProject();
			}
		});

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(100, 25));
		statusBarPanel.add(progressBar);

		submitMainPanel.add(submit);
		// submitMainPanel.add(statusBarPanel);

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

		inputPanel.add(grpPanel);
		inputPanel.add(artifactPanel);
		inputPanel.add(templatePanel);
		inputPanel.add(targetPanel);
		inputPanel.add(submitMainPanel,BorderLayout.EAST);
		outputPanel.add(textAreaPanel);

		setEnabled(true);
		setPreferredSize(screenSize);
		add(inputPanel);
		add(outputPanel);
	}

	protected void generateMavenProject() {
		String grpId = groupId.getText();
		if (grpId.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter group id", "Error:", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String afactId = artifactId.getText();
		if (afactId.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter artifact id", "Error:", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String template = (String) artifactTemplate.getSelectedItem();
		if (template.equals("-----Select-----")) {
			JOptionPane.showMessageDialog(null, "Please select a template", "Error:", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String dest = target.getText();
		if (dest.equals("")) {
			JOptionPane.showMessageDialog(null, "Please enter destination directory to be generate", "Error:",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		createDirectory(dest);
		String[] command = { "CMD", "/C", "mvn archetype:generate -DgroupId=" + grpId + " -DartifactId=" + afactId
				+ " -DarchetypeArtifactId=" + template + " -DinteractiveMode=false" };
		TextAreaThread th = new TextAreaThread(grpId, afactId, template, dest, command);
		th.start();
	}

	private void createDirectory(String dest) {
		File files = new File(dest);
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("Multiple directories are created!");
			} else {
				System.out.println("Failed to create multiple directories!");
			}
		}

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
//			loadingLabel = new JLabel();
//			loadingLabel.setIcon(new ImageIcon(GenerateMavenProjectPanel.class.getResource("/images/loading-new.gif")));
//			submitMainPanel.add(loadingLabel,BorderLayout.EAST);
			textArea.setText("");
			submit.setIcon(new ImageIcon(GenerateMavenProjectPanel.class.getResource("/images/loading-new.gif")));
			submit.setText("Please wait...");
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
					submit.setIcon(null);
					submit.setText("COMPLETED!!!");
					Thread.sleep(1000);
					submit.setText("SUBMIT");
					ImageIcon icon = new ImageIcon(GenerateMavenProjectPanel.class.getResource("/images/ok1.png"));
					JOptionPane.showMessageDialog(null, "BUILD PROCESS COMPLETED!!!", "SUCCESS:",
							JOptionPane.PLAIN_MESSAGE, icon);
				}
			} catch (IOException e) {
				progressBar.setIndeterminate(false);
				submit.setIcon(null);
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
