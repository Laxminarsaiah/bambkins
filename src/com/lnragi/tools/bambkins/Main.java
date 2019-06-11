package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Main {

	/**
	 * 
	 */

	public static void main(String[] args) {
		JWindow window = new JWindow();
		JProgressBar progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(498, 12));
		progressBar.setBackground(Color.WHITE);
		progressBar.setForeground(new Color(34,139,34));
		JLabel jl = new JLabel();
		ImageIcon icon = new ImageIcon(Main.class.getResource("/images/home.jpg"));
		jl.setIcon(icon);
		window.getContentPane().add(jl, BorderLayout.CENTER);
		window.getContentPane().add(progressBar, BorderLayout.SOUTH);
		window.setBounds(400, 150, 500, 300);
		window.setVisible(true);
		try {
			String[] task = { "Loading application....", "Finishing...",""};
			for (int i = 0; i < task.length; i++) {
				progressBar.setValue(i*35);
				progressBar.setString(task[i]);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		window.setVisible(false);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new HomeWindow();
				new RunPreRequisites();
			}
		});
		window.dispose();
	}
}
