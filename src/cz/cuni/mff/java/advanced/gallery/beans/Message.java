package cz.cuni.mff.java.advanced.gallery.beans;

import cz.cuni.mff.java.advanced.gallery.common.User;
import cz.cuni.mff.java.advanced.gallery.data.MessageDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;

public class Message extends cz.cuni.mff.java.advanced.gallery.common.Message {
	
	public String remove(User user, cz.cuni.mff.java.advanced.gallery.common.Message toRemove) {
		try {
			MessageDataManager.removeMessage(user, toRemove);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		
		return "refresh";
	}
}