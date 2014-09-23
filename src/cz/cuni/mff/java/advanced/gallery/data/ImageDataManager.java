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
import cz.cuni.mff.java.advanced.gallery.data.model.ImagePreview;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;

public class ImageDataManager extends DataManager {
	
	
	public static Image insertImage(cz.cuni.mff.java.advanced.gallery.common.Image imageData) throws DatabaseException {
		ImagePreview preview = convert(imageData.getPreview(), ImagePreview.class);
		Image image = convert(imageData, Image.class);
		
		image.setPreview(preview);
		
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        session.save(preview);
	        
	        session.save(image);
	        
	        tr.commit();
	        
	        return image;
		} catch(HibernateException e) {
			tr.rollback();
			throw new DatabaseException(e);
		}
	}
	
	
	public static Collection<cz.cuni.mff.java.advanced.gallery.common.Image> getMostCurrentImagesList(int recordsCount) throws DatabaseException {
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        SQLQuery query = session.createSQLQuery("select * from (select * from IMAGES order by CREATEDDATE desc) where rownum <= :recordsCount and hidden=false");
	        query.setParameter("recordsCount", recordsCount);
	        query.addEntity(Image.class);
	        
	        List<?> queryResult = query.list();
	        Collection<cz.cuni.mff.java.advanced.gallery.common.Image> images = new ArrayList<cz.cuni.mff.java.advanced.gallery.common.Image>();
	        for(Object resultRecord : queryResult) {
	        	images.add((Image) resultRecord);
	        }
	        tr.commit();
	        return images;
		} catch(HibernateException e) {
			if(tr != null)
				tr.rollback();
			throw new DatabaseException(e);
		}
	}
	
	public static cz.cuni.mff.java.advanced.gallery.common.Image getImage(int imageIdentity) throws DatabaseException {
		return fetch(imageIdentity, Image.class);
	}
	
	public static void hideImage(cz.cuni.mff.java.advanced.gallery.common.Image image) throws DatabaseException {
		image.setHidden(true);
		convertAndStore(image, Image.class);
	}
	
	public static void showImage(cz.cuni.mff.java.advanced.gallery.common.Image image) throws DatabaseException {
		image.setHidden(false);
		convertAndStore(image, Image.class);
	}
	
	public static void unhideComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		comment.setHidden(false);
		convertAndStore(comment, Comment.class);
	}
	
	public static void hideComment(cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		comment.setHidden(true);
		convertAndStore(comment, Comment.class);
	}

	public static boolean addComment(cz.cuni.mff.java.advanced.gallery.common.Image image, cz.cuni.mff.java.advanced.gallery.common.Comment comment) throws DatabaseException {
		Comment modelComment = convert(comment, Comment.class);
		
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        session.save(modelComment);
	        
	        Image imageDB = (Image) session.load(Image.class, image.getId());
	        imageDB.getComments().add(modelComment);
	        session.saveOrUpdate(imageDB);
	        
	        tr.commit();
	        
	        image.getComments().add(modelComment);
	        return true;
		} catch(HibernateException e) {
			tr.rollback();
			throw new DatabaseException(e);
		}
	}


	public static Collection<cz.cuni.mff.java.advanced.gallery.common.Image> getImagesByUser(int userId) throws DatabaseException {
		Session session = null;
		Transaction tr = null;
		try {
	        session = getSession();
	        tr = session.beginTransaction();
	        
	        SQLQuery query = session.createSQLQuery("select * from (select * from IMAGES order by CREATEDDATE desc) where ownerid <= :userid");
	        query.setParameter("userid", userId);
	        query.addEntity(Image.class);
	        
	        List<?> queryResult = query.list();
	        Collection<cz.cuni.mff.java.advanced.gallery.common.Image> images = new ArrayList<cz.cuni.mff.java.advanced.gallery.common.Image>();
	        for(Object resultRecord : queryResult) {
	        	images.add((Image) resultRecord);
	        }
	        tr.commit();
	        return images;
		} catch(HibernateException e) {
			if(tr != null)
				tr.rollback();
			throw new DatabaseException(e);
		}
	}
}
