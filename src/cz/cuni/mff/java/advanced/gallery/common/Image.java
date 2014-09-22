package cz.cuni.mff.java.advanced.gallery.common;

import java.util.Set;

public abstract class Image extends IdentifiedObject {
	protected User owner;
	protected String name;
	protected byte[] data;
	protected boolean hidden;
	protected String description;
	protected Set<Comment> comments;
	protected ImagePreview preview;
	
    public ImagePreview getPreview() {
		return preview;
	}
	public void setPreview(ImagePreview preview) {
		this.preview = preview;
	}
	public String getName() {
    	return name;
    }    
    public void setName(String name) {
    	this.name = name;
    }
	public void setOwner(User user) {
		this.owner = user;
	}	
	public User getOwner() {
		return this.owner;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Comment> getComments() { 
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}