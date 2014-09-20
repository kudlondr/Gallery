package cz.cuni.mff.java.advanced.gallery.common;

public abstract class Message extends IdentifiedObject {
	protected User sender;
	protected User reciever;
	protected String text;
	protected Message response;
	protected boolean read;
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Message getResponse() {
		return response;
	}
	public void setResponse(Message response) {
		this.response = response;
	}
	public boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
}
