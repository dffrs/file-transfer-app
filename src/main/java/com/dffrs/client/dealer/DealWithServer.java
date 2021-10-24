package com.dffrs.client.dealer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.dffrs.com.message.Message;
import com.dffrs.com.message.connectionControl.ConnectionControl;
import com.dffrs.com.message.connectionControl.ConnectionControl.Type;
import com.dffrs.com.message.fileTransferMessage.put.Put;
import com.dffrs.gui.ErrorReport;
import com.dffrs.gui.Info;
import com.dffrs.gui.MainPage;

public class DealWithServer extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private Message message;
	private File file;
	private final String username;
	
	public DealWithServer(File fileToSend, Socket socket, ConnectionControl con) {
		if (socket == null) {
			ErrorReport.getInstance().setTextReport("ERROR: Socket is a NULL Reference.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
		}
		if (con == null) {
			ErrorReport.getInstance().setTextReport("ERROR: ConnectionControl argument is a NULL Reference\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
		}
		this.socket = socket;
		this.message = con;
		this.file = fileToSend;
		this.username = this.message.getFrom();
	}
	
	
	private void connectToServer() throws IOException, ClassFormatError, InterruptedException {
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		
		if (this.message instanceof ConnectionControl) {
			if (((ConnectionControl) this.message).getConnectionType().equals(ConnectionControl.Type.Connect)) {
				this.out.writeObject(this.message);
			}else {
				throw new InterruptedException();
			}
		}else {
			throw new ClassFormatError();
		}
	}
	
	private void sendToServer(Message message) throws NullPointerException, IOException{
		if (message == null) {
			throw new NullPointerException();
		}
		if (message instanceof ConnectionControl) {
			if (!((ConnectionControl) message).getConnectionType().equals(ConnectionControl.Type.Connect)) {
				this.out.writeObject(message);
			}
		}else {
			
			this.out.writeObject(message);
		}
	}

	@Override
	public void run() {
		
		try {
			try {
				
				Info.getInstance().setText("Connecting to receiver");
				connectToServer();
				Info.getInstance().setText("Connection done");
				
			} catch (ClassFormatError e) {
				ErrorReport.getInstance().setTextReport("ERROR: Message needs to be a ConnectionControl type.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			} catch (IOException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to send data to receiver.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			} catch (InterruptedException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Message should be a ConnectionControl CONNECT Type message first.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			}
						
			this.message = new Put(this.username, file);
			
			
			try {
				
				Info.getInstance().setText("Sending data to receiver");
				sendToServer(message);
				Info.getInstance().setText("Data sent.");
				
			} catch (NullPointerException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Message trying to send is a NULL Reference.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			} catch (IOException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to send data to receiver.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			}
			
			this.message = new ConnectionControl(getName(), Type.Disconnect);
			
			try {
				
//				Info.getInstance().setText("Sending data to receiver");
				sendToServer(message);
//				Info.getInstance().setText("Data sent.");
				
			} catch (NullPointerException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Message trying to send is a NULL Reference.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			} catch (IOException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to send data to receiver.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				return;
			}
		} finally {
			try {
				System.out.println("Closing socket.\n");
				this.socket.close();
				System.out.println("Socket closed.\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread.currentThread().interrupt();
		}
	}
}