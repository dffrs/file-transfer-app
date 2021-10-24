package com.dffrs.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.border.LineBorder;

import com.dffrs.server.FTPServer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class MainPage {

	private JFrame frmFileTransferApp;
	private JPanel background;
	public static final Color borderColor = new Color(255,207,167);
	public static final Color backGroundColor = new Color(7,30,34);
	public static final Color fillColor = new Color(26,142,137);
	protected static final String PANEL_ID = "1";
	private static MainPage instance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainPage.getInstance().frmFileTransferApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainPage() {
		initialize();
	}

	public static MainPage getInstance() {
		if (instance == null) {
			instance = new MainPage();
		}
		return instance;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFileTransferApp = new JFrame();
		frmFileTransferApp.setResizable(false);
		frmFileTransferApp.setTitle("File Transfer App");
		frmFileTransferApp.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("arrow-download-icon.png")).getImage());
		frmFileTransferApp.setBounds(100, 100, 1024, 500);
		frmFileTransferApp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frmFileTransferApp.getContentPane().setLayout(new CardLayout(0, 0));
		
		background = new JPanel();
		background.setBorder(new LineBorder(borderColor, 3, true));
		background.setBackground(backGroundColor);
		frmFileTransferApp.getContentPane().add(background, PANEL_ID);
		frmFileTransferApp.getContentPane().add(GetTargetIP.getInstance().getContentPaneFromThisFrame(), GetTargetIP.PANEL_ID);
		frmFileTransferApp.getContentPane().add(SenderDragAndDropArea.getInstance().getContentPaneFromThisFrame(), SenderDragAndDropArea.PANEL_ID);
		frmFileTransferApp.getContentPane().add(ErrorReport.getInstance().getContentPaneFromThisFrame(), ErrorReport.PANEL_ID);
		frmFileTransferApp.getContentPane().add(Info.getInstance().getContentPaneFromThisFrame(), Info.PANEL_ID);
		
		background.setLayout(null);
		
		JLabel sendButton = new JLabel("");
		sendButton.setToolTipText("");
		sendButton.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Send Button.png")).getImage()));
		sendButton.setBounds(95, 326, 308, 88);
		sendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel(GetTargetIP.PANEL_ID);
			}
		});
		background.add(sendButton);
		
		JLabel receiveButton = new JLabel("");
		receiveButton.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Receive Button.png")).getImage()));;
		receiveButton.setToolTipText("");
		receiveButton.setBounds(602, 326, 308, 88);
		receiveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel(Info.PANEL_ID);
				new Thread(new FTPServer()).start();
			}
		});
		background.add(receiveButton);
		
		JLabel icon = new JLabel("");
		icon.setToolTipText("");
		icon.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("arrow-download-icon.png")).getImage()));
		icon.setBounds(420, 11, 179, 179);
		background.add(icon);
		frmFileTransferApp.setLocation(((Toolkit.getDefaultToolkit().getScreenSize().width - frmFileTransferApp.getSize().width) / 2),
				((Toolkit.getDefaultToolkit().getScreenSize().height - frmFileTransferApp.getSize().height)/ 2));
	}
	
	public Container getContentPaneFromThisFrame() {
		return background;
	}
	
	public JFrame getMainFrame() {
		return frmFileTransferApp;
	}
	
	public void changePanel(String panelID) {
	    ((CardLayout) frmFileTransferApp.getContentPane().getLayout()).show(frmFileTransferApp.getContentPane(), panelID);
	}
}