package cz.cuni.mff.java.advanced.gallery.common;

import cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException;
import cz.cuni.mff.java.advanced.gallery.security.Encryptor;

public class User {
	protected int id;
	
	protected String username;
	protected String password;
	protected String passwordCheck;

	protected String email;
	protected String name;
	protected String surname;
	protected boolean showemail;
	protected boolean showname;
	protected boolean showsurname;
	
	public void setUsername(String value) {
		this.username = value;
	}	
	public String getUsername() {
		return this.username;
	}	
	public void setPassword(String value) {
		try{
			this.password = Encryptor.makeSHA1Hash(value);
		} catch(SecurityException e) {
			e.printStackTrace();
		}
	}	
	public String getPassword() {
		return this.password;
	}	
	public void setPasswordCheck(String value) {
		try {
			this.passwordCheck = Encryptor.makeSHA1Hash(value);
		} catch(SecurityException e) {
			e.printStackTrace();
		}
	}	
	public String getPasswordCheck() {
		return this.passwordCheck;
	}	
	public void setEmail(String value) {
		this.email = value;
	}	
	public String getEmail() {
		return this.email;
	}	
	public void setName(String value) {
		this.name = value;
	}	
	public String getName() {
		return this.name;
	}	
	public void setSurname(String value) {
		this.surname = value;
	}	
	public String getSurname() {
		return this.surname;
	}	
	public void setShowemail(boolean value) {
		this.showemail = value;
	}	
	public void setShowemail(Boolean value) {
		this.showemail = value;
	}	
	public boolean getShowemail() {
		return this.showemail;
	}	
	public void setShowname(boolean value) {
		this.showname = value;
	}	
	public void setShowname(Boolean value) {
		this.showname = value;
	}	
	public boolean getShowname() {
		return this.showname;
	}	
	public void setShowsurname(boolean value) {
		this.showsurname = value;
	}	
	public void setShowsurname(Boolean value) {
		this.showsurname = value;
	}	
	public boolean getShowsurname() {
		return this.showsurname;
	}
	public int getId() {
		return id;
	}
}
