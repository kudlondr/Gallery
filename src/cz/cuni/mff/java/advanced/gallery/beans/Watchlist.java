package cz.cuni.mff.java.advanced.gallery.beans;

import java.util.Date;

public class Watchlist {
	private String name;
	private int id;
	private int belongsTo;
	private int watching;
	private Date createDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(int belongsTo) {
		this.belongsTo = belongsTo;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getWatching() {
		return watching;
	}
	public void setWatching(int watching) {
		this.watching = watching;
	}
	public String getName() {
		if(name == null) {
			//TODO sql
		}
		return name;
	}
	public String removeFromWatch() {
//		try {
//			//TODO sql
//			return "reload";
//		} catch(GalleryException e) {
//			e.printStackTrace();
//		}
		
		return "reload";
	}
}
