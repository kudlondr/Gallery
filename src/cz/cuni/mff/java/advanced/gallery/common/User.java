package cz.cuni.mff.java.advanced.gallery.common;

import java.util.Set;

import cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException;
import cz.cuni.mff.java.advanced.gallery.security.Encryptor;

public abstract class User extends IdentifiedObject {
	
	protected String username;
	protected String password;
	protected String passwordCheck;

	protected String email;
	protected String name;
	protected String surname;
	protected boolean showemail;
	protected boolean showname;
	protected boolean showsurname;
	
	protected Set<User> watchlist;
	protected Set<Message> messages;
	
	public Set<Message> getMessages() {
		return messages;
	}
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
	public void setWatchlist(Set<User> watchlist) {
		this.watchlist = watchlist;
	}
	public Set<User> getWatchlist() {
		return watchlist;
	}
	public void setUsername(String value) {
		this.username = value;
	}	
	public String getUsername() {
		return this.username;
	}
	public void setPassword(String value) {
		this.password = value;
	}	
	public String getPassword() {
		return this.password;
	}	
	public void setPasswordCheck(String value) {
		try {
			if(value == null || value.isEmpty()) {
				this.passwordCheck = null;
				return;
			} else {
				this.passwordCheck = Encryptor.makeSHA1Hash(value);
			}
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
	public boolean equals(Object u) {
		return u != null && u instanceof cz.cuni.mff.java.advanced.gallery.common.User && getId() == ((cz.cuni.mff.java.advanced.gallery.common.User)u).getId();
	}
}
