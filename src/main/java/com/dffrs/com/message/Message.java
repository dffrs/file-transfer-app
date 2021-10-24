package com.dffrs.com.message;

import java.io.Serializable;

public abstract class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7325920576779490379L;

	private final String from;
	private final Object data;
	
	public Message(String from, Object data) {
		if (from == null) {
			throw new NullPointerException("Error: Message' from field is a NULL Reference.\n");
		}
		if (data == null) {
			throw new NullPointerException("Error: Message's data field is a NULL Reference.\n");
		}
		this.from = from;
		this.data = data;
	}

	public String getFrom() {
		return from;
	}

	public Object getData() {
		return data;
	}
	
	@Override
	public abstract String toString();
}