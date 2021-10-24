package com.dffrs.com.message.fileTransferMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import com.dffrs.com.message.Message;

public abstract class FileTransferMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3418349351891462263L;
//	private final File file;

	public FileTransferMessage(String from, Object data) {
		super(from, data);
//		this.file = file;
	}

//	public File getFile() {
//		return this.file;
//	}
	
	public static final File createFile(String path) throws IOException, FileNotFoundException{
		File file = new File(path);
		System.out.println("Creating a file.\n");
		Files.createFile(file.toPath());
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		System.out.println("File created.\n");
		return file;
	}
	
	public static final byte[] getContentFromFile(File file) throws NullPointerException, IOException {
		byte[] content = null;
		if (file == null) {
			throw new NullPointerException();
		}
		System.out.println("Getting content from File.\n");
		content = Files.readAllBytes(file.toPath());
		System.out.println("Done.\n");
		
		return content;
	}
	
	public static final void saveContentToFile(File placeToSaveFile, byte[] content) throws NullPointerException, IOException, FileNotFoundException {
		if (content == null) {
			throw new NullPointerException();
		}
		if (placeToSaveFile.exists() && placeToSaveFile.isFile()) {
			System.out.println("Saving content to File\n");
			Files.write(placeToSaveFile.toPath(), content);
			System.out.println("Done\n");
		}else {
			throw new FileNotFoundException();
		}
	}
}