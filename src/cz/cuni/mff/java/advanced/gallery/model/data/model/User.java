package cz.cuni.mff.java.advanced.gallery.model.data.model;

public class User extends cz.cuni.mff.java.advanced.gallery.common.User {
	
	@SuppressWarnings("unused")
	private void setId(int value) { //used by Hibernate's reflection
		this.id = value;
	}
}
