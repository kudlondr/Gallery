package cz.cuni.mff.java.advanced.gallery.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cz.cuni.mff.java.advanced.gallery.data.model.Comment;
import cz.cuni.mff.java.advanced.gallery.data.model.User;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.RecordExistsException;

public class UserDataManager extends DataManager {
	
	public static final int INCORRECT_USER_ID = -1;
	
	public static cz.cuni.mff.java.advanced.gallery.common.User isLoginCorrect(cz.cuni.mff.java.advanced.gallery.common.User user) throws DatabaseException {
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        SQLQuery query = session.createSQLQuery("select * from USERS where username = :userName and password = :password");
	        query.setParameter("userName", user.getUsername());
	        query.setParameter("password", user.getPassword());
	        query.addEntity(User.class);
	        
	        List<?> queryResult = query.list();
	        tr.commit();
	        return queryResult != null && queryResult.size() == 1 ? ((User) queryResult.get(0)) : null;
	        
		} catch(HibernateException e) {
			if(tr != null)
				tr.rollback();
			throw new DatabaseException(e);
		}
	}
	
	public static void saveUser(cz.cuni.mff.java.advanced.gallery.common.User user) throws DatabaseException, RecordExistsException {
		User completedUserData = replaceNullsWithStoredData(convert(user, User.class));
		store(completedUserData);
	}
	
	public static void removeFromWatchList(cz.cuni.mff.java.advanced.gallery.common.User watchlistOwner, cz.cuni.mff.java.advanced.gallery.common.User toRemove) throws DatabaseException {
		watchlistOwner.getWatchlist().remove(toRemove);
		User completedUserData = replaceNullsWithStoredData(convert(watchlistOwner, User.class));
		convertAndStore(completedUserData, User.class);
	}
	
	public static void addToWatchList(cz.cuni.mff.java.advanced.gallery.common.User watchlistOwner, cz.cuni.mff.java.advanced.gallery.common.User toWatch) throws DatabaseException {
		watchlistOwner.getWatchlist().add(toWatch);
		convertAndStore(watchlistOwner, User.class);
	}
	
	public static void createComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		convertAndStore(comment, Comment.class);
	}
	
	public static void hideComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		comment.setHidden(true);
		convertAndStore(comment, Comment.class);
	}
}
