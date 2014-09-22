package cz.cuni.mff.java.advanced.gallery.common;

public abstract class Message extends IdentifiedObject {
	protected User reciever;
	protected Image image;
	protected boolean read;
	
	public User getReciever() {
		return reciever;
	}
	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	public boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image value) {
		this.image = value;
	}
}
