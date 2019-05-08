package com.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Main {
	  public static void main(String args[]) {
	    final Object rows[][] = {
	      {"one",   "1"},
	      {"two",   "2"},
	      {"three", "3"},
	      {"four",  "4"},
	      {"one",   "1"},
	      {"two",   "2"},
	      {"three", "3"},
	      {"four",  "4"},
	      {"one",   "1"},
	      {"two",   "2"},
	      {"three", "3"},
	      {"four",  "4"},
	      {"one",   "1"},
	      {"two",   "2"},
	      {"three", "3"},
	      {"four",  "4"},

	    };
	    final Object headers[] = {"English", "#"};

	    JFrame frame = new JFrame("Table Printing");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    final JTable table = new JTable(rows, headers);
	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    JButton button = new JButton("Print");
	    ActionListener printAction = new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        try {
	          MessageFormat headerFormat = new MessageFormat("Page {0}");
	          MessageFormat footerFormat = new MessageFormat("- {0} -");
	          table.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
	        } catch (PrinterException pe) {
	          System.err.println("Error printing: " + pe.getMessage());
	        }
	      }
	    };
	    button.addActionListener(printAction);
	    frame.add(button, BorderLayout.SOUTH);
	    frame.setSize(300, 150);
	    frame.setVisible(true);
	  }
	}