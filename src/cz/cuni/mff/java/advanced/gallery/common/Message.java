package cz.cuni.mff.java.advanced.gallery.common;

import java.util.Date;

public abstract class Message extends IdentifiedObject {
	protected User reciever;
	protected Image image;
	
	public Message() {
		setCreatedDate(new Date());
	}
	
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image value) {
		this.image = value;
	}
}
