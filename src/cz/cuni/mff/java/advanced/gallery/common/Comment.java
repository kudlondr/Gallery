package cz.cuni.mff.java.advanced.gallery.common;

import java.util.Date;

import cz.cuni.mff.java.advanced.gallery.beans.User;

public class Comment {
	protected int id;
	protected User sender;
	protected User reciever;
	protected String type;
	protected String text;
	protected Date createdDate;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public boolean getHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
