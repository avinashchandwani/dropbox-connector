package com.file.viewer.ui.entity;
/**
 * A class to hold DropBox Connection information
 * 
 * @author Avinash Chandwani
 *
 */
public class DropBoxConnection {
	private String accessKey;
	private String clientId;

	public DropBoxConnection(){
		super();
	}
	
	public DropBoxConnection(String accessKey, String clientId) {
		this.accessKey = accessKey;
		this.clientId = clientId;
	}

	public String getAccessKey() {
		return this.accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
