package com.dffrs.com.message.fileTransferMessage.get;

//import java.io.File;
//import java.io.IOException;

import com.dffrs.com.message.fileTransferMessage.FileTransferMessage;

public class Get extends FileTransferMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8205315337238721818L;
//	private byte[] content;
	
	public Get(String from, String fileName) {
		super(from, fileName);
//		constuctContent(this.content);
	}

//	private void constuctContent(byte[] content) {
//		try {
//			content = getContentFromFile(getFile());
//		} catch (NullPointerException e) {
//			System.err.println("ERROR: Source File is a NULL Reference/n");
//			return;
//		} catch (IOException e) {
//			System.err.println("ERROR: Something went wrong trying to read from File/n");
//			return;
//		}
//	}
	
//	public byte[] getFileContent() {
//		return this.content;
//	}

	@Override
	public String toString() {
		return "Message Type: File Transfer Message | From: "+this.getFrom()+" | Type: GET";
	}
}