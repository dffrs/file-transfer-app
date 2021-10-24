package com.dffrs.com.message.connectionControl;

import com.dffrs.com.message.Message;

public class ConnectionControl extends Message {

	public enum Type{
		Connect(1),
		Disconnect(0);
		
		private final int idnumber;
		private Type(int idnumber) {
			this.idnumber = idnumber;
		}
		public int getIdnumber() {
			return idnumber;
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8714658331937283108L;
	private final ConnectionControl.Type com;

	public ConnectionControl(String from, ConnectionControl.Type com) {
		super(from, "EMPTY");
		if (com == null) {
			throw new NullPointerException("Error: Connection Control's com field is a NULL Reference.\n");
		}
		this.com = com;
	}

	public ConnectionControl.Type getConnectionType(){
		return com;
	}
	
	@Override
	public String toString() {
		return "Message Type: Connection Control | From: "+this.getFrom()+" | Connection Type: "+this.getConnectionType()+" | ID Number: "+this.getConnectionType().getIdnumber()+"\n";
	}
}