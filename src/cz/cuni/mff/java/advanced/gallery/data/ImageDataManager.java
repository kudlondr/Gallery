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
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;

public class ImageDataManager extends DataManager {
	
	
	public static boolean saveImage(cz.cuni.mff.java.advanced.gallery.common.Image image) throws DatabaseException {
		return store(image);
	}
	
	
	public static Collection<cz.cuni.mff.java.advanced.gallery.common.Image> getMostCurrentImagesList(int recordsCount) throws DatabaseException {
		Session session = null;
		Transaction trans = null;
		try {
	        session = getSession();
	        trans = session.beginTransaction();
	        
	        SQLQuery query = session.createSQLQuery("select * from (select * from IMAGES order by CREATEDDATE desc) where rownum <= :recordsCount");
	        query.setParameter("recordsCount", recordsCount);
	        query.addEntity(Image.class);
	        
	        List<?> queryResult = query.list();
	        Collection<cz.cuni.mff.java.advanced.gallery.common.Image> images = new ArrayList<cz.cuni.mff.java.advanced.gallery.common.Image>();
	        for(Object resultRecord : queryResult) {
	        	images.add((Image) resultRecord);
	        }
	        
	        trans.commit();
	        
	        return images;
		} catch(HibernateException e) {
			if(trans != null) trans.rollback();
			throw new DatabaseException(e);
		}
	}
	
	public static cz.cuni.mff.java.advanced.gallery.common.Image getImage(int imageIdentity) throws DatabaseException {
		try {
			return fetch(imageIdentity, Image.class);
		} catch(Exception e) {
			throw new DatabaseException(e);
		}
	}
	
	public static void hideImage(cz.cuni.mff.java.advanced.gallery.common.Image image) throws DatabaseException {
		image.setHidden(true);
		convertAndStore(image, Image.class);
	}
	
	public static void showImage(cz.cuni.mff.java.advanced.gallery.common.Image image) throws DatabaseException {
		image.setHidden(false);
		convertAndStore(image, Image.class);
	}
	
	public static void hideComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		comment.setHidden(true);
		convertAndStore(comment, Comment.class);
	}
	
	public static void createComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		convertAndStore(comment, Comment.class);
	}
}
