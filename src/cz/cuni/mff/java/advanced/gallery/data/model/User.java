package cz.cuni.mff.java.advanced.gallery.data.model;

public class User extends cz.cuni.mff.java.advanced.gallery.common.User implements ModelObject {
	
	@SuppressWarnings("unused")
	private void setId(int value) { //used by Hibernate's reflection
		this.id = value;
	}
}
