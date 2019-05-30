package com.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lnragi.tools.bambkins.RunPreRequisites;

public class TestProgress {
	JProgressBar progressBar;
	JPanel textAreaPanel;
	JLabel textAreaLabel;

	public static void main(String[] args) {
		new TestProgress();
	}

	public TestProgress() {
		textAreaLabel = new JLabel();
		textAreaLabel.setAutoscrolls(true);
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(498, 15));
		progressBar.setBackground(Color.WHITE);
		progressBar.setForeground(new Color(0, 100, 0));
		textAreaLabel.add(progressBar, BorderLayout.SOUTH);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("CHECKING FOR JAVA", "JAVA_HOME");
				map.put("CHECKING FOR MAVEN", "MAVEN_HOME");
				map.put("CHECKING FOR ANT", "ANT_HOME");
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
						textAreaLabel.setText("Install Required Appliactions.");
						Thread.sleep(1000);
					}
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException | InterruptedException ex) {
				}
				new BackgroundWorker().execute();
			}

		});
	}

	public class BackgroundWorker extends SwingWorker<Void, Void> {

		private ProgressMonitor monitor;

		public BackgroundWorker() {
			addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if ("progress".equalsIgnoreCase(evt.getPropertyName())) {
						if (monitor == null) {
							monitor = new ProgressMonitor(null, "Processing", null, 0, 99);
						}
						monitor.setProgress(getProgress());
					}
				}

			});
		}

		@Override
		protected void done() {
			if (monitor != null) {
				monitor.close();
			}
		}

		@Override
		protected Void doInBackground() throws Exception {
			for (int index = 0; index < 100; index++) {
				setProgress(index);
				Thread.sleep(125);
			}
			return null;
		}
	}
}