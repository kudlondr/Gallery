package cz.cuni.mff.java.advanced.gallery.beans;

import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException;
import cz.cuni.mff.java.advanced.gallery.model.data.DatabaseController;
import cz.cuni.mff.java.advanced.gallery.security.Encryptor;

public class Login
{
    private String username;
    private String password;
    private boolean loggedin = false;


    public String getUsername ()
    {
        return username;
    }


    public void setUsername (final String username)
    {
        this.username = username;
    }


    public String getPassword ()
    {
        return password;
    }


    public void setPassword (final String password)
    {
    	try{
    		this.password = Encryptor.makeSHA1Hash(password);
    	} catch(SecurityException e) {
    		e.printStackTrace();
    	}
    }


	public boolean getLoggedin() {
		return loggedin;
	}


	public void setLoggedin(boolean loggedin) {
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
}
