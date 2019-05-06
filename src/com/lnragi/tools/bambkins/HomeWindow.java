package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class HomeWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2002038219670094171L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public HomeWindow() {
		initializeHomeWindow();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(10, 110, 20, 20);
		tabbedPane.setBorder(BorderFactory.createMatteBorder(24, 6, 12, 6, Color.blue));
		Font myFont1 = new Font("Calibri", Font.TYPE1_FONT, 14);
		tabbedPane.setFont(myFont1);
		tabbedPane.setBorder(BorderFactory.createTitledBorder("Build Ant/Maven Applications"));
		tabbedPane.add("Home", new HomePanel());
		tabbedPane.add("Generate Maven Project", new GenerateMavenProjectPanel());
		tabbedPane.add("Build Exists Maven Project", new BuildMavenProjectPanel());
		tabbedPane.add("Build Ant Project", new BuildAntProjectPanel());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}

	private void initializeHomeWindow() {
		Image icon = Toolkit.getDefaultToolkit().getImage(HomeWindow.class.getResource("/images/favicon.png"));
		setBackground(Color.WHITE);
		setPreferredSize(screenSize);
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BAMBKINS");
		setVisible(true);
		//setLocationRelativeTo(null);
		pack();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
