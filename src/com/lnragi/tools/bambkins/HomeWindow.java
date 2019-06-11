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
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(10, 110, 20, 20);
		tabbedPane.setBorder(BorderFactory.createMatteBorder(24, 6, 12, 6, Color.blue));
		Font myFont1 = new Font("Calibri", Font.TYPE1_FONT, 14);
		tabbedPane.setFont(myFont1);
		tabbedPane.setBorder(BorderFactory.createTitledBorder("BUILD MAVEN/ANT APPLICATIONS"));
		ImageIcon home = new ImageIcon(HomeWindow.class.getResource("/images/home_16.png"));
		ImageIcon genmvn = new ImageIcon(HomeWindow.class.getResource("/images/newprj_wiz.png"));
		ImageIcon buildmvn = new ImageIcon(HomeWindow.class.getResource("/images/newjprj_wiz.png"));
		ImageIcon buildant = new ImageIcon(HomeWindow.class.getResource("/images/ant_buildfile.png"));
		ImageIcon monit = new ImageIcon(HomeWindow.class.getResource("/images/console_view.png"));
		
		tabbedPane.addTab("Home", home,new HomePanel());
		tabbedPane.addTab("Generate Maven Project", genmvn,new GenerateMavenProjectPanel());
		tabbedPane.addTab("Build Exists Maven Project", buildmvn,new BuildMavenProjectPanel());
		tabbedPane.addTab("Build Ant Project", buildant,new BuildAntProjectPanel());
		tabbedPane.addTab("Monitor", monit,new MonitorPanel());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}

	private void initializeHomeWindow() {
		Image icon = Toolkit.getDefaultToolkit().getImage(HomeWindow.class.getResource("/images/icon.png"));
		setPreferredSize(screenSize);
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BAMBKINS");
		setVisible(true);
		// setLocationRelativeTo(null);
		pack();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
