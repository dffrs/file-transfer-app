package com.dffrs.gui;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.dffrs.client.Client;

import java.io.File;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class SenderDragAndDropArea {

	protected static final String PANEL_ID = "3";
	private JFrame frmSendAFile;
	private JPanel panel;
	private static SenderDragAndDropArea instance;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SenderDragAndDropArea.getInstance().frmSendAFile.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private SenderDragAndDropArea() {
		initialize();
	}
	
	public static SenderDragAndDropArea getInstance() {
		if (instance == null) {
			instance = new SenderDragAndDropArea();
		}
		return instance;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSendAFile = new JFrame();
		frmSendAFile.setTitle("Send a file");
		frmSendAFile.setBounds(100, 100, 1024, 500);
		frmSendAFile.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frmSendAFile.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(MainPage.backGroundColor);
		panel.setBorder(new LineBorder(MainPage.borderColor, 4, true));
		panel.setBounds(0, 0, 1008, 461);
		frmSendAFile.getContentPane().add(panel);
		panel.setLayout(null);
		panel.setDropTarget(new DropTarget() {
			 /**
			 * 
			 */
			private static final long serialVersionUID = 2570933354269705853L;

			@Override
			public synchronized void drop(DropTargetDropEvent evt) {
			        try {
			            evt.acceptDrop(DnDConstants.ACTION_COPY);
			            @SuppressWarnings("unchecked")
						List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
			            for (File file : droppedFiles) {
			               	new Thread(new Client(GetTargetIP.getInstance().getTargetIP(), file)).start();
			            }
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
			        MainPage.getInstance().changePanel(Info.PANEL_ID);
			 }
		});
		
		JLabel cancelButton = new JLabel("New label");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainPage.getInstance().changePanel(GetTargetIP.PANEL_ID);
			}
		});
		cancelButton.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Button.png")).getImage()));
		cancelButton.setBounds(30, 341, 309, 88);
		panel.add(cancelButton);
		
		JLabel dropIcon = new JLabel("New label");
		dropIcon.setIcon(new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("Drop.png")).getImage()));
		dropIcon.setBounds(420, 11, 179, 179);
		panel.add(dropIcon);
	}
	
	protected void setVisible(boolean b) {
		this.frmSendAFile.setVisible(b);
	}
	
	public Container getContentPaneFromThisFrame() {
		return panel;
	}
}