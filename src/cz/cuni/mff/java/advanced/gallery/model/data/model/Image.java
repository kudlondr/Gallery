package cz.cuni.mff.java.advanced.gallery.model.data.model;

public class Image extends cz.cuni.mff.java.advanced.gallery.common.Image {
	
	@SuppressWarnings("unused")
	private void setId(int value) { //used by Hibernate's reflection
		this.id = value;
	}
}
