package com.dffrs.com.message.fileTransferMessage.list;

import com.dffrs.com.message.fileTransferMessage.FileTransferMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Listing extends FileTransferMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178257835492336329L;
	private List<String> allFiles;

	public Listing(String from) {
		super(from, "LIST Operation: No need to specify details");
		this.allFiles = new ArrayList<String>();
	}

	public List<String> getAllFiles() {
		return allFiles;
	}
	
	public void setAllFiles(List<File> listWithFiles) throws NullPointerException {
		if (listWithFiles == null) {
			throw new NullPointerException();
		}
		listWithFiles.forEach(f -> this.allFiles.add(f.getName()));
	}
	
	public static List<File> getAllFilesInside(File dir) throws NullPointerException, FileNotFoundException, IllegalArgumentException{
		List<File> list = new ArrayList<File>();
		if (dir == null) {
			throw new NullPointerException();
		}
		if (dir.exists()) {
			if (dir.isDirectory()) {
				list = Arrays.asList(dir.listFiles());
			}else {
				throw new IllegalArgumentException();
			}
		}else {
			throw new FileNotFoundException();
		}
		return list;
	}
	
	@Override
	public String toString() {
		return "Message Type: File Transfer Message | From: "+this.getFrom()+" | Type: LIST";
	}
}