package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MonitorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5153664124231740024L;
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Font font = new Font("Calibri", Font.BOLD, 14);
	JPanel submitMainPanel;
	JButton submit;

	public MonitorPanel() {
		submitMainPanel = new JPanel();
//		submitMainPanel.setBorder(BorderFactory.createMatteBorder(35, 8, 12, 8, Color.blue));
//		submitMainPanel.setBorder(BorderFactory.createTitledBorder(" Open putty here "));
		submit = new JButton("Open");
		ImageIcon icon = new ImageIcon(PuttyWindow.class.getResource("/images/add_correction.png"));
		submit.setIcon(icon);
		submit.setPreferredSize(new Dimension(180, 40));
		submit.setBackground(Color.BLUE);
		submit.setActionCommand("mvngen");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PuttyWindow();
			}
		});
		
		submitMainPanel.add(submit);
		add(submitMainPanel, BorderLayout.EAST);
	}
}
