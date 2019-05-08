package com.lnragi.tools.bambkins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class RunPreRequisites {

	JProgressBar progressBar;
	JPanel textAreaPanel;
	JLabel textAreaLabel;

	public RunPreRequisites() {
		textAreaLabel = new JLabel();
		textAreaLabel.setAutoscrolls(true);
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(498, 15));
		progressBar.setBackground(Color.WHITE);
		progressBar.setForeground(new Color(0, 100, 0));
		textAreaLabel.add(progressBar, BorderLayout.SOUTH);
		new TextAreaThread().start();
//		ImageIcon icon = new ImageIcon(BuildMavenProjectPanel.class.getResource("/images/wait.gif"));
		JOptionPane.showMessageDialog(null, textAreaLabel, "Running Pre requisites:", JOptionPane.INFORMATION_MESSAGE);
	}

	class TextAreaThread extends Thread {

		public TextAreaThread() {

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
					// textArea.append(entry.getKey() + ": ");
					textAreaLabel.setText(entry.getKey() + ":");
					progressBar.setValue(i * 35);
					Thread.sleep(1000);
					String str = System.getenv(entry.getValue());
					if (str != null) {
						textAreaLabel.setText(entry.getKey() + ": ");
						// textArea.append(entry.getKey() + " : SUCCESS ");
						textAreaLabel.setText(entry.getKey() + ":SUCCESS");
						Thread.sleep(1000);
					} else if (str == null) {
						error.add(entry.getValue());
						// textArea.append(entry.getKey() + " : FAILED");
						textAreaLabel.setText(entry.getKey() + ":FAILED ");
						Thread.sleep(1000);
					}
					i++;
					progressBar.setValue(i * 35);
				}
				if (error.size() == 0) {
					// textArea.append("Ready to GO....");
					textAreaLabel.setText("Ready to GO....");
					Thread.sleep(3000);
				} else if (error.size() > 0) {
					textAreaLabel.setText("Install Required Appliactions and come back!!!");
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
