package de.htw.vs.chat;

import java.io.Serializable;

public class Message implements Serializable {

	static final long serialVersionUID = 4242L;

	protected String username;
	protected String text;

	public Message(String username, String text) {
		this.username = username;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return String.format("%s : %s", username, text);
	}
}
