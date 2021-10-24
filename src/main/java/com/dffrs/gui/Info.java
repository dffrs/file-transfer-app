package com.dffrs.gui;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class Info {

	private JFrame frame;
	private JPanel panel;
	private JTextArea infoArea;
	private static Info instance;
	private JLabel okButton;
	public static final String PANEL_ID = "5";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Info.getInstance().frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private Info() {
		initialize();
	}

	public static Info getInstance() {
		if (instance == null) {
			instance = new Info();
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
		
		infoArea = new JTextArea();
		infoArea.setWrapStyleWord(true);
		infoArea.setLineWrap(true);
		infoArea.setEditable(false);
		infoArea.setBounds(36, 32, 929, 304);
		infoArea.setFont(new Font("Lucida Console", Font.PLAIN, 28));
		infoArea.setForeground(MainPage.borderColor);
		infoArea.setBackground(MainPage.backGroundColor);
		infoArea.setBorder(new LineBorder(MainPage.backGroundColor, 4, true));
		infoArea.setEditable(false);
		panel.add(infoArea);
		
		okButton = new JLabel();
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				infoArea.setText("");
				MainPage.getInstance().changePanel(MainPage.PANEL_ID);
			}
		});
		okButton.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Ok.png")).getImage()));
		okButton.setBounds(30, 341, 309, 88);
		okButton.setVisible(false);
		panel.add(okButton);
	}

	public Container getContentPaneFromThisFrame() {
		return panel;
	}

	public JTextArea getInfoArea() {
		return infoArea;
	}

	public void setText(String text) {
		if (text.contains(".")) {
			text = text.substring(0, text.length()-1);
			showButton();
		}
		infoArea.setText(infoArea.getText()+"> "+text+".\n");
	}

	private void showButton() {
		okButton.setVisible(true);
	}
}