package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PrerequisiteWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5993235691337899828L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JLabel inputPanel;
	JProgressBar progressBar;

	public PrerequisiteWindow() {
		initializeHomeWindow();
		inputPanel = new JLabel();
		inputPanel.setLayout(new GridLayout(1, 1));
		inputPanel.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 3));
		inputPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
		inputPanel.setBorder(BorderFactory.createTitledBorder(" Checking... "));

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(498, 15));
		progressBar.setBackground(Color.WHITE);
		progressBar.setForeground(new Color(0, 100, 0));
		add(inputPanel, BorderLayout.LINE_START);
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		PrerequisitesThread pt = new PrerequisitesThread();
		pt.start();
	}

	private void initializeHomeWindow() {
		Image icon = Toolkit.getDefaultToolkit().getImage(HomeWindow.class.getResource("/images/favicon.png"));
		setBackground(Color.WHITE);
		setBounds(400, 150, 200, 200);
		setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 4));
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Checking for prerequisites...");
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

	class PrerequisitesThread extends Thread {

		public PrerequisitesThread() {
			super();
		}

		public void run() {
			Map<String, String> map = new HashMap<String, String>();
			map.put("CHECKING FOR JAVA", "JAVA_HOME");
			map.put("CHECKING FOR MAVEN", "MAVEN_HOME");
			map.put("CHECKING FOR ANT", "ANT_HOME");

			try {
				Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
				int i = 0;
				List<String> error = new ArrayList<>();
				while (itr.hasNext()) {
					Map.Entry<String, String> entry = itr.next();
					inputPanel.setText(entry.getKey() + ": ");
					progressBar.setValue(i * 35);
					Thread.sleep(1000);
					String str = System.getenv(entry.getValue());
					if (str != null) {
						inputPanel.setText(entry.getKey() + " : SUCCESS ");
						Thread.sleep(1000);
					} else if (str == null) {
						error.add(entry.getValue());
						inputPanel.setText(entry.getKey() + " : FAILED");
						Thread.sleep(1000);

					}
					i++;
					progressBar.setValue(i * 35);
				}
				if (error.size() == 0) {
					inputPanel.setText("Ready to GO....");
					Thread.sleep(3000);
					setVisible(false);
					dispose();
				} else if (error.size() > 0) {
					inputPanel.setText("Install Required Appliactions and come back!!!");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
