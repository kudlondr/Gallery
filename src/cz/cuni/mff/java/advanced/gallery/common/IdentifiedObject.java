package cz.cuni.mff.java.advanced.gallery.common;

import java.util.Date;

public abstract class IdentifiedObject {
	protected int id;
	protected Date createdDate;
	
	public IdentifiedObject() {}
	
	public IdentifiedObject(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(int id) { //used by ModelMapper to set id by reflection
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
