package cz.cuni.mff.java.advanced.gallery.common;

public abstract class Comment extends IdentifiedObject {
	protected User sender;
	protected String text;
	protected boolean hidden;
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean getHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
