package cz.cuni.mff.java.advanced.gallery.beans;

import java.util.Collection;
import java.util.Date;

import cz.cuni.mff.java.advanced.gallery.common.Comment;
import cz.cuni.mff.java.advanced.gallery.common.Image;
import cz.cuni.mff.java.advanced.gallery.data.ImageDataManager;
import cz.cuni.mff.java.advanced.gallery.data.UserDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.exceptions.RecordExistsException;
import cz.cuni.mff.java.advanced.gallery.exceptions.SecurityException;
import cz.cuni.mff.java.advanced.gallery.security.Encryptor;

public class User extends cz.cuni.mff.java.advanced.gallery.common.User {
	
	private boolean loggedin = false;
	protected boolean emailExists = false;
	private int showImage; //image that the user is currently looking at; used when going from main page to imageDetail to determine which image should be shown
	private int showUser; //user detail that the user is currently looking at
	private boolean inited = false;
	private cz.cuni.mff.java.advanced.gallery.common.Comment[] comments;
	
	public User() {
		setCreatedDate(new Date());
	}
	
	public void initUserDetail(int userDetailId) {
		if(inited)
			return;
		
    	try {
    		cz.cuni.mff.java.advanced.gallery.common.User foundUser = UserDataManager.getUser(userDetailId);
    		loadUser(foundUser);
    		
    		Collection<Comment> commentsDB = UserDataManager.getComments(userDetailId);
    		comments = new Comment[commentsDB.size()];
    		comments = commentsDB.toArray(comments);
    		
    		inited = true;
    	} catch(DatabaseException e) {
    		e.printStackTrace();
    	}
	}
	
	public int getShowImage() {
		return showImage;
	}
	public void setShowImage(int shownImage) {
		this.showImage = shownImage;
	}
	public int getShowUser() {
		return showUser;
	}
	public void setShowUser(int userDetailId) {
		this.showUser = userDetailId;
	}
	
	public String goToDetail(int userDetailId) {
		setShowUser(userDetailId);
		return "showUserDetail";
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
	    		loadUser(foundUser);
	    		
	    		setLoggedin(true);
	    		return "loginSuccess";
	    	}
    	} catch(GalleryException e) {
    		e.printStackTrace();
    	}
    	return "loginFailure";
    }
    
    private void loadUser(cz.cuni.mff.java.advanced.gallery.common.User user) {
		this.createdDate = user.getCreatedDate();
		this.email = user.getEmail();
		this.id = user.getId();
		this.name = user.getName();
		//this.password = foundUser.getPassword();
		//this.passwordCheck = foundUser.getPasswordCheck();
		this.showemail = user.getShowemail();
		this.showname = user.getShowname();
		this.showsurname = user.getShowsurname();
		this.username = user.getUsername();
		this.watchlist = user.getWatchlist();
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
	
	public boolean isNotWatching(User userDetail) {
		return !isWatching(userDetail);
	}
	public boolean isWatching(User userDetail) {
		for(cz.cuni.mff.java.advanced.gallery.common.User watchedUser : getWatchlist()) {
			if(watchedUser.getId() == userDetail.getId()) {
				return true;
			}
		}
		return false;
	}
	public String addToWatch(User toWatch) {
		try {
			UserDataManager.addToWatchList(this, toWatch);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "repeat";
	}
	public String removeFromWatch(User watchedUser) {
		try {
			UserDataManager.removeFromWatchList(this, watchedUser);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "repeat";
	}
	
	public int getMessagesCount() {
		return getMessages() == null ? 0 : getMessages().size();
	}
	
	public Image[] getImages(int userDetailId) {
		initUserDetail(userDetailId);
		Collection<Image> imagesDB = null;
		try{
			imagesDB = ImageDataManager.getImagesByUser(userDetailId);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		Image[] imagesArr = new Image[imagesDB == null ? 0 : imagesDB.size()];
		return imagesDB.toArray(imagesArr);
	}
	
	public Comment[] getCommentsArray(int userDetailId) {
		initUserDetail(userDetailId);
		return comments;
	}
	public boolean equals(Object u) {
		return u != null && u instanceof cz.cuni.mff.java.advanced.gallery.common.User && getId() == ((cz.cuni.mff.java.advanced.gallery.common.User)u).getId();
	}
}
