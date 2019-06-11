package com.lnragi.tools.bj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JPanel topRow, rowOne, rowTwo, rowThree;
	JLabel labelOne, labelTwo, labelThree;

	public MainFrame() {
		initialize();
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		topRow = new JPanel();
		topRow.setBackground(Color.WHITE);

		topRow.setLayout(new GridLayout(15, 100));
		rowOne = new JPanel();
		rowOne.setLayout(new GridLayout(2, 100));
		rowOne.setBorder(BorderFactory.createTitledBorder(" ROW 1 "));
		labelOne = new JLabel("This is row one");
		rowOne.setBackground(Color.green);
		rowOne.add(labelOne);
		// -------------------------
		rowTwo = new JPanel();
		rowTwo.setLayout(new GridLayout(2, 100));
		rowTwo.setBorder(BorderFactory.createTitledBorder(" ROW 2 "));
		rowTwo.setBackground(Color.LIGHT_GRAY);
		labelTwo = new JLabel("This is row two");
		rowTwo.add(labelTwo);

		topRow.add(rowOne);
		topRow.add(rowTwo);

		add(topRow);
	}

	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Image icon = Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/images/about.png"));
			setPreferredSize(screenSize);
			setIconImage(icon);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("BAMBKINS");
			setVisible(true);
			pack();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}

}
