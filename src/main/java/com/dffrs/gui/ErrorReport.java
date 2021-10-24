package com.dffrs.gui;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.Font;

public final class ErrorReport {

	private JFrame frame;
	private JPanel panel;
	private JTextField textReport;
	private static ErrorReport instance;
	public static final String PANEL_ID = "0";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErrorReport.getInstance().frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private ErrorReport() {
		initialize();
	}
	
	public static ErrorReport getInstance() {
		if (instance == null) {
			instance = new ErrorReport();
		}
		return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame = new JFrame();
		frame.setTitle("Send a file");
		frame.setBounds(100, 100, 1024, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(MainPage.backGroundColor);
		panel.setBorder(new LineBorder(MainPage.borderColor, 4, true));
		panel.setBounds(0, 0, 1008, 461);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel okButton = new JLabel("New label");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		okButton.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Ok.png")).getImage()));
		okButton.setBounds(30, 341, 309, 88);
		panel.add(okButton);
		
		textReport = new JTextField();
		textReport.setFont(new Font("Lucida Console", Font.PLAIN, 28));
		textReport.setForeground(MainPage.borderColor);
		textReport.setBackground(MainPage.backGroundColor);
		textReport.setBorder(new LineBorder(MainPage.backGroundColor, 4, true));
		textReport.setEditable(false);
		textReport.setBounds(68, 51, 883, 257);
		panel.add(textReport);
		textReport.setColumns(10);
	}
	
	public JTextField getTextReport() {
		return textReport;
	}
	
	public void setTextReport(String errorLine) {
		if (errorLine.length() == 0) {
			throw new IllegalArgumentException("ERROR: ErrorLine is a empty.\n");
		}
		this.textReport.setText(errorLine);
	}

	public Container getContentPaneFromThisFrame() {
		return panel;
	}
}