package com.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lnragi.tools.bambkins.HomeWindow;

public class FramOuter extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7420172286795573780L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JButton button;
	JPanel panel;

	public FramOuter() {
		initializeHomeWindow();
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		button = new JButton("SUBMIT");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FrameInner();
			}
		});
		panel = new JPanel();
		panel.add(button);
		add(panel);
	}

	private void initializeHomeWindow() {
		Image icon = Toolkit.getDefaultToolkit().getImage(HomeWindow.class.getResource("/images/favicon.png"));
		setBackground(Color.WHITE);
		setPreferredSize(screenSize);
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BAMBKINS");
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
				new FramOuter();
			}
		});
	}
}
