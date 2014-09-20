package cz.cuni.mff.java.advanced.gallery.data.model;

public class Image extends cz.cuni.mff.java.advanced.gallery.beans.Image implements ModelObject {
	
	@SuppressWarnings("unused")
	private void setId(int value) { //used by Hibernate's reflection
		this.id = value;
	}
}
