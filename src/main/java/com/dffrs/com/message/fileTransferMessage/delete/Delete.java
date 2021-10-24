package com.dffrs.com.message.fileTransferMessage.delete;

import com.dffrs.com.message.fileTransferMessage.FileTransferMessage;

public class Delete extends FileTransferMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6676867380267740879L;

	public Delete(String from, String fileName) {
		super(from, fileName);
	}

	@Override
	public String toString() {
		return "Message Type: File Transfer Message | From: "+this.getFrom()+" | Type: DELETE";
	}
}