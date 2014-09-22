package cz.cuni.mff.java.advanced.gallery.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cz.cuni.mff.java.advanced.gallery.data.model.Comment;
import cz.cuni.mff.java.advanced.gallery.data.model.Image;
import cz.cuni.mff.java.advanced.gallery.data.model.User;
import cz.cuni.mff.java.advanced.gallery.data.model.Watchlist;
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
	
	public static cz.cuni.mff.java.advanced.gallery.common.User getUser(int userId) throws DatabaseException {
		return fetch(userId, User.class);
	}
	
	public static void saveUser(cz.cuni.mff.java.advanced.gallery.common.User user) throws DatabaseException, RecordExistsException {
		User completedUserData = replaceNullsWithStoredData(convert(user, User.class));
		store(completedUserData);
	}
	
	public static boolean removeFromWatchList(cz.cuni.mff.java.advanced.gallery.common.User watchlistOwner, cz.cuni.mff.java.advanced.gallery.common.User toRemove) throws DatabaseException {
		User modelUserToRemove = convert(toRemove, User.class);
		
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        User userDB = (User) session.load(User.class, watchlistOwner.getId());
	        userDB.getWatchlist().add(modelUserToRemove);
	        session.saveOrUpdate(userDB);
	        
	        tr.commit();
	        
	        watchlistOwner.getWatchlist().remove(toRemove);
	        return true;
		} catch(HibernateException e) {
			tr.rollback();
			throw new DatabaseException(e);
		}
	}
	
	public static boolean addToWatchList(cz.cuni.mff.java.advanced.gallery.common.User watchlistOwner, cz.cuni.mff.java.advanced.gallery.common.User toWatch) throws DatabaseException {
		User modelUserToWatch = convert(toWatch, User.class);
		
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        User userDB = (User) session.load(User.class, watchlistOwner.getId());
	        userDB.getWatchlist().add(modelUserToWatch);
	        session.saveOrUpdate(userDB);
	        
	        tr.commit();
	        
	        watchlistOwner.getWatchlist().add(toWatch);
	        return true;
		} catch(HibernateException e) {
			tr.rollback();
			throw new DatabaseException(e);
		}
	}
	
	public static void createComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		convertAndStore(comment, Comment.class);
	}
	
	public static void hideComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		comment.setHidden(true);
		convertAndStore(comment, Comment.class);
	}
	
	public static Collection<cz.cuni.mff.java.advanced.gallery.common.Comment> getComments(int userDetailId) throws DatabaseException {
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        SQLQuery query = session.createSQLQuery("select * from COMMENTS com join USER_COMMENTS uc on com.id= uc.commentid where USERID=:userid");
	        query.setParameter("userid", userDetailId);
	        query.addEntity(Comment.class);
	        
	        List<?> queryResult = query.list();
	        tr.commit();
	        List<cz.cuni.mff.java.advanced.gallery.common.Comment> comments = new ArrayList<cz.cuni.mff.java.advanced.gallery.common.Comment>();
	        for(Object resultItem : queryResult) {
	        	comments.add((cz.cuni.mff.java.advanced.gallery.common.Comment) resultItem);
	        }
	        return comments;
	        
		} catch(HibernateException e) {
			if(tr != null)
				tr.rollback();
			throw new DatabaseException(e);
		}
	}
}
