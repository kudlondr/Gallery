package cz.cuni.mff.java.advanced.gallery.beans;

import java.util.Date;

import cz.cuni.mff.java.advanced.gallery.data.UserDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.exceptions.RecordExistsException;
import cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException;
import cz.cuni.mff.java.advanced.gallery.security.Encryptor;

public class User extends cz.cuni.mff.java.advanced.gallery.common.User {
	
	private boolean loggedin = false;
	protected boolean emailExists = false;
	protected int showImage;
	
	public User() {
		setCreatedDate(new Date());
	}
	
	public int getShowImage() {
		return showImage;
	}
	public void setShowImage(int shownImage) {
		this.showImage = shownImage;
	}
	
	public void setLoginPassword(String value) {
		try {
			if(value == null || value.isEmpty()) {
				this.password = null;
				return;
			} else {
				this.password = Encryptor.makeSHA1Hash(value);
			}
		} catch(SecurityException e) {
			e.printStackTrace();
		}
	}
	public String getLoginPassword() {
		return password;
	}
	
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
			if(getPassword() != null && !getPassword().isEmpty()) {
				if(getPassword().equals(getPasswordCheck())) {
					UserDataManager.saveUser(this);
				}
				return "repeat";
			} else {
				UserDataManager.saveUser(this);
				if(getLoggedin()) {
					return "repeat";
				}
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
	    		//this.password = foundUser.getPassword();
	    		//this.passwordCheck = foundUser.getPasswordCheck();
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
		this.createdDate = null;
		this.email = "";
		this.id = -1;
		this.name = "";
		this.password = null;
		this.passwordCheck = null;
		this.showemail = true;
		this.showname = true;
		this.showsurname = true;
		this.username = "";
		this.watchlist = null;
	}
	
	public boolean getEmailExists() {
		return emailExists;
	}
	
	public void setEmailExists(boolean emailExists) {
		this.emailExists = emailExists;
	}
	
	public String addToWatch() {
		return "";
		//TODO
	}
	public String removeFromWatch(Object watchedUser) {
		return removeFromWatch((cz.cuni.mff.java.advanced.gallery.common.User) watchedUser);
	}
	public String removeFromWatch(cz.cuni.mff.java.advanced.gallery.common.User watchedUser) {
		try {
			UserDataManager.removeFromWatchList(this, watchedUser);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "repeat";
	}
	
	public Message[] getMessages() {
		//try{
			//TODO SQL
			return new Message[0];
/*		} catch(GalleryException e) {
			e.printStackTrace();
		}
		return null;*/
	}
	
	public int getMessagesCount() {
		return getMessages() == null ? 0 : getMessages().length;
	}
	
	public String showDetail() {
		//TODO
		return "showUser";
	}
}
