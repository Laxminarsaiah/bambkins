package com.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lnragi.tools.bambkins.HomeWindow;

public class FrameInner extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7420172286795573780L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	JButton button;

	public FrameInner() {
		initializeHomeWindow();
	}

	private void initializeHomeWindow() {
		Image icon = Toolkit.getDefaultToolkit().getImage(HomeWindow.class.getResource("/images/favicon.png"));
		setBackground(Color.WHITE);
		setPreferredSize(screenSize);
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FramOuter();
			}
		});
	}
}
