package com.dffrs.com.message.fileTransferMessage.put;

import java.io.File;
import java.io.IOException;

import com.dffrs.com.message.fileTransferMessage.FileTransferMessage;

public class Put extends FileTransferMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1158936657373016633L;
	private byte[] content;

	public Put(String from, File file) {
		super(from, file);
		constuctContent();
	}

	private void constuctContent() {
		try {
			this.content = getContentFromFile((File)getData());
		} catch (NullPointerException e) {
			System.err.println("ERROR: Source File is a NULL Reference\n");
			return;
		} catch (IOException e) {
			System.err.println("ERROR: Something went wrong trying to read from File\n");
			return;
		}
	}
	
	public byte[] getContent() {
		return this.content;
	}
	
	@Override
	public String toString() {
		return "Message Type: File Transfer Message | From: "+this.getFrom()+" | Type: PUT";
	}

}
