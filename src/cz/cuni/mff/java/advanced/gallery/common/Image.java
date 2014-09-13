package cz.cuni.mff.java.advanced.gallery.common;

import java.util.Date;

import cz.cuni.mff.java.advanced.gallery.beans.User;

public class Image {
	protected int id;
	protected User owner;
	protected String name;
	protected byte[] data;
	protected Date createdDate;
	protected boolean hidden;
	protected String description;

    public String getName() {
    	return name;
    }    
    public void setName(String name) {
    	this.name = name;
    }
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setUser(User user) {
		this.owner = user;
	}	
	public User getUser() {
		return this.owner;
	}	
	public int getId() {
		return this.id;
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
}
