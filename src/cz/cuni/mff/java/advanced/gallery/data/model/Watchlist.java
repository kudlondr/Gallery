package cz.cuni.mff.java.advanced.gallery.data.model;

public class Watchlist extends cz.cuni.mff.java.advanced.gallery.beans.Watchlist implements ModelObject {
	
	@SuppressWarnings("unused")
	private void setId(int id) { //used by Hibernate's reflection
		this.id = id;
	}
}
