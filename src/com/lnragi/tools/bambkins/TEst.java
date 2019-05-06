package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TEst extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JLabel submitMainPanel;
	JLabel buttonPanel;
	JLabel progressPanel;
	
	JProgressBar progressBar;
	JButton button;
	

	public TEst() {
		initializeHomeWindow();
		submitMainPanel = new JLabel();
		submitMainPanel.setLayout(new GridLayout(2, 1));
		submitMainPanel.setPreferredSize(new Dimension(2,2));
		submitMainPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 8, Color.blue));
		submitMainPanel.setBorder(BorderFactory.createTitledBorder(" MainPanel... "));
		
		buttonPanel = new JLabel();
		buttonPanel.setLayout(new GridLayout(1, 100));
		buttonPanel.setPreferredSize(new Dimension(2,2));
		buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 8, Color.blue));
		buttonPanel.setBorder(BorderFactory.createTitledBorder(" ButtonPanel... "));
		button = new JButton("Test");
		buttonPanel.add(button);
		
		progressPanel = new JLabel();
		progressPanel.setLayout(new GridLayout(4, 1));
		progressPanel.setPreferredSize(new Dimension(100,20));
		progressPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 8, Color.blue));
		progressPanel.setBorder(BorderFactory.createTitledBorder(" ProgressPanel... "));
		progressBar = new JProgressBar();
		
		progressPanel.add(progressBar,BorderLayout.SOUTH);
		
		
		submitMainPanel.add(buttonPanel);
		submitMainPanel.add(progressPanel);
		progressBar.setIndeterminate(true);
		
		add(submitMainPanel);
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TEst();
			}
		});
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
}
