package com.dffrs.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GetTargetIP {

	protected static final String PANEL_ID = "2";
	private JFrame frmWhatsTheIp;
	private JTextField textField;
	private JPanel panel;
	private static GetTargetIP instance;
	private String targetIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GetTargetIP.getInstance().frmWhatsTheIp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private GetTargetIP() {
		initialize();
	}
	
	public static GetTargetIP getInstance() {
		if (instance==null) {
			instance = new GetTargetIP();
		}
		return instance;
	}

	public String getTargetIP() {
		return targetIP;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWhatsTheIp = new JFrame();
		frmWhatsTheIp.setTitle("What's the IP ?");
		frmWhatsTheIp.setBounds(100, 100, 1024, 500);
		frmWhatsTheIp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frmWhatsTheIp.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(MainPage.backGroundColor);
		panel.setBorder(new LineBorder(MainPage.borderColor, 4, true));
		panel.setBounds(0, 0, 1008, 461);
		panel.setLayout(null);
		frmWhatsTheIp.getContentPane().add(panel);
		
		JLabel pcIcon = new JLabel("");
		pcIcon.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Target's IP.png")).getImage()));
		pcIcon.setBounds(420, 11, 179, 179);
		panel.add(pcIcon);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					MainPage.getInstance().changePanel(SenderDragAndDropArea.PANEL_ID);
					String temp = textField.getText();
					if (temp != null) {
						targetIP = textField.getText();
					}
				}
			}
		});
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(MainPage.borderColor);
		textField.setBackground(MainPage.fillColor);
		textField.setFont(new Font("Lucida Console", Font.PLAIN, 28));
		textField.setBorder((new LineBorder(MainPage.borderColor, 4, false)));
		textField.setBounds(47, 296, 916, 49);
		panel.add(textField);
		textField.setColumns(10);
	}
	
	public JPanel getContentPaneFromThisFrame() {
		return panel;
	}
}