package cz.cuni.mff.java.advanced.gallery.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cz.cuni.mff.java.advanced.gallery.data.model.Message;
import cz.cuni.mff.java.advanced.gallery.data.model.User;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;

public class MessageDataManager extends DataManager {
	
	public static boolean sendMessages(cz.cuni.mff.java.advanced.gallery.common.User imageOwner, cz.cuni.mff.java.advanced.gallery.common.Image image) throws DatabaseException {
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        //get users, that are watching imageOwner
	        SQLQuery query = session.createSQLQuery("select * from USERS where ID in (select BELONGSTO from WATCHLISTS where WATCHING=:imageowner)");
	        query.setParameter("imageowner", imageOwner.getId());
	        query.addEntity(User.class);
	        
	        List<?> wacthingUsersList = query.list();
	        for(Object watchingUserObj : wacthingUsersList) {
	        	//send message for each watching user
	        	User watchingUser = (User) watchingUserObj;
	        			
	        	Message message = new Message();
	        	message.setImage(image);
	        	message.setReciever(watchingUser);
	        	session.saveOrUpdate(message);
	        	
	        	watchingUser.getMessages().add(message);
	        	
	        	session.saveOrUpdate(watchingUserObj);
	        }
	        tr.commit();
	        return true;
		} catch(HibernateException e) {
			if(tr != null)
				tr.rollback();
			throw new DatabaseException(e);
		}
	}
	
	public static void sendMessage(cz.cuni.mff.java.advanced.gallery.common.Message message) throws DatabaseException {
		convertAndStore(message, Message.class);
	}
	
	public static void removeMessage(cz.cuni.mff.java.advanced.gallery.common.User user, cz.cuni.mff.java.advanced.gallery.common.Message message) throws DatabaseException {
		delete(message);
		user.getMessages().remove(message);
	}
}
