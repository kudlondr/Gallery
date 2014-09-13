package cz.cuni.mff.java.advanced.gallery.model.data.model;

public class Comment extends cz.cuni.mff.java.advanced.gallery.common.Comment {
	
	@SuppressWarnings("unused")
	private void setId(int value) { //used by Hibernate's reflection
		this.id = value;
	}
}
