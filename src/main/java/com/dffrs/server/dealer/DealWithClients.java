package com.dffrs.server.dealer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.channels.ClosedByInterruptException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;

import com.dffrs.com.message.Message;
import com.dffrs.com.message.connectionControl.ConnectionControl;
import com.dffrs.com.message.fileTransferMessage.FileTransferMessage;
import com.dffrs.com.message.fileTransferMessage.put.Put;
import com.dffrs.gui.ErrorReport;
import com.dffrs.gui.Info;
import com.dffrs.gui.MainPage;
import com.dffrs.server.FTPServer;

public class DealWithClients extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private Message message;
	private final Deque<Message> messageBuffer;
	
	public DealWithClients(Socket socket) {
		if (socket == null) {
			throw new NullPointerException("ERROR: Socket is a NULL Reference. Make sure it was inicialized well.\n");
		}
		this.socket = socket;
		this.messageBuffer = new ArrayDeque<Message>(); 
	}

	private void connectToClient() throws IOException, ClassNotFoundException, ClassFormatError, InterruptedException {
		this.in = new ObjectInputStream(this.socket.getInputStream());
		
		this.message = (Message) in.readObject();
		if (this.message instanceof ConnectionControl) {
			if (((ConnectionControl) this.message).getConnectionType().equals(ConnectionControl.Type.Connect)) {
				if ( !Files.exists(Paths.get(FTPServer.dir.toPath().toString().concat("/"+this.message.getFrom()))) ) {
					
					Info.getInstance().setText("Creating client personal repository");
					
					Files.createDirectory(Paths.get(FTPServer.dir.toPath().toString().concat("/"+this.message.getFrom())));
					
					Info.getInstance().setText("Personal Repository created");
					
				}
			}else {
				throw new InterruptedException();
			}
		}else {
			throw new ClassFormatError();
		}
	}
	
	private void receiveDataFromClient() throws ClassNotFoundException, IOException, NullPointerException {
		this.message = (Message) in.readObject();
		
		if (this.message == null) {
			throw new NullPointerException();
		}
		
		try {
			if (this.message instanceof ConnectionControl) {
				if (((ConnectionControl) this.message).getConnectionType().equals(ConnectionControl.Type.Disconnect)) {
					this.messageBuffer.offerLast(this.message);
				}
			}else {
				this.messageBuffer.offerLast(this.message);
			}
		} finally {
			this.message = null;
		}
	}
	
	private void processDateFromClient() throws IllegalStateException, IOException, InterruptedException{
		if (!this.messageBuffer.isEmpty()) {
			this.message = this.messageBuffer.pop();
			
			if (this.message instanceof FileTransferMessage) {
				File temp = null;
				if (this.message instanceof Put) {
					
					System.out.println("Creating file.\n");
					temp = FileTransferMessage.createFile(FTPServer.dir.getAbsolutePath() +"/"+ this.message.getFrom() + "/" + ((File)this.message.getData()).getName());
					
					if (temp.exists()) {
						FileTransferMessage.saveContentToFile(temp, ((Put) this.message).getContent());					
					}else {
						throw new InterruptedException();
					}
				}
			}
			if (this.message instanceof ConnectionControl) {
				throw new ClosedByInterruptException();
			}
		}else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void run() {
		boolean interrupted = false;
		
		try {
			MainPage.getInstance().changePanel(Info.PANEL_ID);
			Info.getInstance().setText("Establishing connection");
			connectToClient();
			Info.getInstance().setText("Connected to sender");
			
		} catch (IOException e) {
			ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to connect to client.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			interrupted = true;
		} catch (ClassNotFoundException e) {
			ErrorReport.getInstance().setTextReport("CRITICAL ERROR: Client is trying to send unsupported data.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			interrupted = true;
		} catch (ClassFormatError e) {
			ErrorReport.getInstance().setTextReport("ERROR: Client not connecting properly.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			interrupted = true;
		} catch (InterruptedException e) {
			ErrorReport.getInstance().setTextReport("ERROR: Client must send a Connection Control CONNECT Type message first.\n");
			MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			interrupted = true;
		}
		
		while(!interrupted) {
			
			try {
				
				Info.getInstance().setText("Received client's data");
				receiveDataFromClient();
				
			} catch (ClassNotFoundException e) {
				ErrorReport.getInstance().setTextReport("CRITICAL ERROR: Client is trying to send unsupported data.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				interrupted = true;
				break;
			} catch (NullPointerException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Message received was a NULL Reference.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				interrupted = true;
				break;
			} catch (IOException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to receive client's data.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				interrupted = true;
				break;
			}
			
			try {
				processDateFromClient();
				Info.getInstance().setText("File received.");
			} catch (IllegalStateException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Message buffer is empty. No messages recorded.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			 
			}
//			catch (ClosedByInterruptException e) {
//				ErrorReport.getInstance().setTextReport("ERROR: File specified was not found. File doesn't exist.\n");
//				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
//				interrupted = true;
//				break;
//			} 
			catch (IOException e) {
//				ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to send data to client.\n");
//				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
				interrupted = true;
				break;
			} catch (InterruptedException e) {
				ErrorReport.getInstance().setTextReport("ERROR: Something went wrong trying to create a File.\n");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			}
		}
		
		if (interrupted) {
			try {
				
				System.out.println("Closing socket.\n");
				this.socket.close();
				System.out.println("Socket closed.\n");
				
			} catch (IOException e) {
				ErrorReport.getInstance().setTextReport("WARNING: Something went wrong trying to close the socket.");
				MainPage.getInstance().changePanel(ErrorReport.PANEL_ID);
			}
			
			System.out.println("Interrupting "+currentThread().toString());
			Thread.currentThread().interrupt();
			System.out.println("Thread interrupted.\n");
			
		}
	}
}