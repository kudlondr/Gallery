package cz.cuni.mff.java.advanced.gallery.model.data.model;

public class Message extends cz.cuni.mff.java.advanced.gallery.common.Message {
	
	@SuppressWarnings("unused")
	private void setId(int value) { //used by Hibernate's reflection
		this.id = value;
	}
}
