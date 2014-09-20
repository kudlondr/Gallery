package cz.cuni.mff.java.advanced.gallery.data;

import cz.cuni.mff.java.advanced.gallery.data.model.Message;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;

public class MessageDataManager extends DataManager {
	
	public static void sendMessage(cz.cuni.mff.java.advanced.gallery.common.Message message) throws DatabaseException {
		convertAndStore(message, Message.class);
	}
	
	public static void markAsReadMessage(Message message) throws DatabaseException {
		message.setRead(true);
		convertAndStore(message, Message.class);
	}
	
	public static void markAsUnreadMessage(Message message) throws DatabaseException {
		message.setRead(false);
		convertAndStore(message, Message.class);
	}
}
