package com.dffrs.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

import com.dffrs.gui.ErrorReport;
import com.dffrs.gui.Info;
import com.dffrs.gui.MainPage;
import com.dffrs.server.dealer.DealWithClients;

public final class FTPServer extends Thread{

	private static final int PORT = 8080;
	private static final String dirPath = "Directory/";
	public static final File dir = new File(dirPath);
	private ServerSocket server;
	private Socket socket;
	private DealWithClients dealer;

	private void makeDir() throws IOException {
		Files.createDirectory(FTPServer.dir.toPath());
	}
	
	@Override
	public void run() {
		
		try {
			
			System.out.println("Creating directory.\n");
			makeDir();
			System.out.println("Directory created.\n");
			
		} catch (IOException e1) {
			System.err.println("Warning: Something went wrong trying to create a storying folder. It already exists.\n");
		}
	
		try {
			System.out.println("Inicializing receiver's socket.\n");
			server = new ServerSocket(PORT);
			System.out.println("Socket created.\n");
			
			while (true) {
			
				Info.getInstance().setText("Accepting client's requests");
				socket = server.accept();
//				Info.getInstance().setText("Client connected -> "+socket);
				
				dealer = new DealWithClients(socket);
				dealer.start();

			}
			
		}catch (IOException e) {
			ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to inicialize Receiver Socket.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			try {
				
				closeServerSocket();
				
			} catch (IOException o) {
				ErrorReport.getInstance().setTextReport("WARNING: Something went wrong trying to close server's socket.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			}
		}
	}
	
	public void closeServerSocket() throws IOException {
		System.out.println("Closing server socket.\n");
		server.close();
		System.out.println("Server socket closed.\n");
	}
}