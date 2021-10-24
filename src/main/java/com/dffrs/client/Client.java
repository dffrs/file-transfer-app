package com.dffrs.client;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.dffrs.client.dealer.DealWithServer;
import com.dffrs.com.message.connectionControl.ConnectionControl;
import com.dffrs.com.message.connectionControl.ConnectionControl.Type;
import com.dffrs.gui.ErrorReport;
import com.dffrs.gui.MainPage;

public final class Client extends Thread{

	private static final int PORT = 8080;
	private Socket socket;
	private String username;
	private final String IPtoServer;
	private final File file;
	
	
	public Client(String IP, File file) {
		super();
		if (IP == null) {
			ErrorReport.getInstance().setTextReport("ERROR: Target's IP is a NULL Reference.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
		}
		if (file == null) {
			ErrorReport.getInstance().setTextReport("ERROR: File is a NULL Reference.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
		}
		this.IPtoServer = IP;
		this.file = file;
	}
	
	private void establishConnection() throws IOException {
		this.username = InetAddress.getLocalHost().getHostAddress();
		InetAddress add = InetAddress.getByName(IPtoServer);
		this.socket = new Socket(add, PORT);
	}
	
	@Override
	public void run() {
		DealWithServer dealer;
	
		try {
			
			System.out.println("Connecting ...\n");
			establishConnection();
			System.out.println("Connection done "+this.socket);
			
			dealer = new DealWithServer(this.file, this.socket, new ConnectionControl(username, Type.Connect));
			dealer.start();
			
		} catch (IOException e) {
			ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to connect to receiver.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
		}
	}
}