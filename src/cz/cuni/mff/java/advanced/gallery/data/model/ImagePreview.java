package cz.cuni.mff.java.advanced.gallery.data.model;

public class ImagePreview extends cz.cuni.mff.java.advanced.gallery.common.ImagePreview implements ModelObject {
	
	@SuppressWarnings("unused")
	private void setId(int value) { //used by Hibernate's reflection
		this.id = value;
	}
}
