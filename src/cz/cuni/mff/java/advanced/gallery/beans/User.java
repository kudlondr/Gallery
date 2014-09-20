package cz.cuni.mff.java.advanced.gallery.beans;

import cz.cuni.mff.java.advanced.gallery.data.UserDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.exceptions.RecordExistsException;

public class User extends cz.cuni.mff.java.advanced.gallery.common.User {
	
	private boolean loggedin = false;
	protected boolean emailExists = false;
	
	public String create() {
		emailExists = false;
		if(getUsername() == null || getUsername().isEmpty()
				|| getPassword() == null || getPassword().isEmpty()
				|| getPasswordCheck() == null || getPasswordCheck().isEmpty()
				|| !getPassword().equals(getPasswordCheck())
				|| getEmail() == null || getEmail().isEmpty()) {
			
			return "repeat";
		} else {
			return save();
		}
	}
	
	public String save() {
		try {
			UserDataManager.saveUser(this);
			if(getLoggedin()) {
				return "repeat";
			}
		} catch(RecordExistsException e) {
			emailExists = true;
			return "repeat";
		} catch(GalleryException e) {
			e.printStackTrace();
			return "repeat";
		}
		return "login";
	}
	
	public String getCreateButtonLabel() {
		if(getLoggedin()) {
			return "Save";
		} else {
			return "Create";
		}
	}
    
    public String login() {
    	try {
    		cz.cuni.mff.java.advanced.gallery.common.User foundUser = UserDataManager.isLoginCorrect(this);
	    	if(foundUser != null) {
	    		this.createdDate = foundUser.getCreatedDate();
	    		this.email = foundUser.getEmail();
	    		this.id = foundUser.getId();
	    		this.name = foundUser.getName();
	    		//this.password = foundUser.password;
	    		//this.passwordCheck = foundUser.passwordCheck;
	    		this.showemail = foundUser.getShowemail();
	    		this.showname = foundUser.getShowname();
	    		this.showsurname = foundUser.getShowsurname();
	    		this.username = foundUser.getUsername();
	    		this.watchlist = foundUser.getWatchlist();
	    		
	    		setLoggedin(true);
	    		return "loginSuccess";
	    	}
    	} catch(GalleryException e) {
    		e.printStackTrace();
    	}
    	return "loginFailure";
    }


	public boolean getLoggedin() {
		return loggedin;
	}

	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}
	
	public void setLoggedin(Boolean loggedin) {
		this.loggedin = loggedin;
	}
	
	public String logout() {
		setLoggedin(false);
		clear();
		return "logout";
	}
	
	private void clear() {
		this.username = "";
	}
	
	public boolean getEmailExists() {
		return emailExists;
	}
	
	public void setEmailExists(boolean emailExists) {
		this.emailExists = emailExists;
	}
	
	public void addToWatch() {
		//TODO
	}
	
	public void removeFromWatch() {
		//TODO
	}
	
	public Message[] getMessages() {
		//try{
			//TODO SQL
			return null;
/*		} catch(GalleryException e) {
			e.printStackTrace();
		}
		return null;*/
	}
	
	public String showDetail() {
		//TODO
		return "showUser";
	}
}
