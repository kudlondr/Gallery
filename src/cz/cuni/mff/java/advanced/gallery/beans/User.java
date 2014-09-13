package cz.cuni.mff.java.advanced.gallery.beans;

import java.util.Date;

import cz.cuni.mff.java.advanced.gallery.exceptions.RecordExistsException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException;
import cz.cuni.mff.java.advanced.gallery.model.data.DataManager;
import cz.cuni.mff.java.advanced.gallery.model.data.HibernateUtil;
import cz.cuni.mff.java.advanced.gallery.security.Encryptor;

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
			try{
				if(getLoggedin()) {
					DatabaseController.updateUser(this);
					return "repeat";
				} else {
					DatabaseController.createUser(this);
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
	}
	
	public String getCreateButtonLabel() {
		if(getLoggedin()) {
			return "Save";
		} else {
			return "Create";
		}
	}
    
    public String login() {
    	
    	try{
	    	if(DatabaseController.isLoginCorrect(this)) {
	    		setLoggedin(true);
	    		return "loginSuccess";
	    	}
    	} catch(GalleryException e) {
    		e.printStackTrace();
    	}
    	return "loginFailure";
    }


	public boolean getLoggedin() {
		
		testHibernate();
		
		return loggedin;
	}
	
	private void testHibernate() {
		try {
			Image image = new Image();
			image.setName("hibernateTest "+(new Date()).toString());
			image.setCreatedDate(new Date());
			image.setUserId(0);	
			DataManager.store(image);
		} catch(Throwable e) {
			e.printStackTrace();
		}
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
	
	public Watchlist[] getWatchlists() {
		try{
			return DatabaseController.getWatching(this);
		} catch(GalleryException e) {
			e.printStackTrace();
		}
		
		return null;
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
