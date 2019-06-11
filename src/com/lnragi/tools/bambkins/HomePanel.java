package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -445195431776912650L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = new Font("Calibri", Font.BOLD, 24);
	public HomePanel() {
//		JPanel panel = new JPanel();
//		panel.setPreferredSize(screenSize);
//		panel.setLayout(new GridLayout(1, 2));
//		setLayout(new GridLayout(1, 2));
//		JLabel leftLbl = new JLabel();
//		leftLbl.setBorder(BorderFactory.createMatteBorder(30, 8, 6, 8, Color.blue));
//		leftLbl.setFont(font);
//		leftLbl.setBorder(BorderFactory.createTitledBorder("WELCOME TO BAMBKINS"));
//		leftLbl.setBackground(Color.WHITE);
//		leftLbl.setVisible(true);
//		JLabel rightLbl = new JLabel();
//		rightLbl.setBorder(BorderFactory.createMatteBorder(30, 8, 6, 8, Color.blue));
//		rightLbl.setFont(font);
//		rightLbl.setBorder(BorderFactory.createTitledBorder("WELCOME TO BAMBKINS"));
//		rightLbl.setBackground(Color.WHITE);
		//setPreferredSize(screenSize);
		setVisible(true);
		setBackground(new Color(0,0,139));
		//ImageIcon icon = new ImageIcon(HomePanel.class.getResource("/images/2.jpg"));
		//add(leftLbl,BorderLayout.SOUTH);
		//add(rightLbl,BorderLayout.NORTH);
		JLabel label = new JLabel();
		//label.setIcon(icon);
		add(label);
	}
	
}
