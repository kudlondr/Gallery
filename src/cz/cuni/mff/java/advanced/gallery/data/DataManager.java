package cz.cuni.mff.java.advanced.gallery.data;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cz.cuni.mff.java.advanced.gallery.common.IdentifiedObject;
import cz.cuni.mff.java.advanced.gallery.data.model.ModelObject;
import cz.cuni.mff.java.advanced.gallery.data.util.HibernateUtil;
import cz.cuni.mff.java.advanced.gallery.exceptions.ConversionException;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.util.ModelMapper;

public class DataManager {
	
	protected static Session getSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		if(session == null)
			return factory.openSession();
		else
			return session;
	}
	
	protected static <T> boolean store(T object) throws DatabaseException {
		Session session = null;
		Transaction trans = null;
		try {
	        session = getSession();
	        trans = session.beginTransaction();
	        
	        session.save(object);
	
	        trans.commit();
	        return true;
		} catch(HibernateException e) {
			if(trans != null) trans.rollback();
			throw new DatabaseException(e);
		}
    }
	
	protected static <T extends IdentifiedObject, D extends ModelObject> D convert(T object, Class<D> modelObjectClass) throws DatabaseException {
		try {
			return cz.cuni.mff.java.advanced.gallery.util.ModelMapper.convert(object, modelObjectClass);
		} catch(ConversionException e) {
			throw new DatabaseException(e);
		}
	}
	
	protected static <T extends IdentifiedObject, D extends ModelObject> boolean convertAndStore(T object, Class<D> modelObjectClass) throws DatabaseException {
		D toStore = convert(object, modelObjectClass);
		return store(toStore);
	}
	
	/*protected static Object performSQL(String sqlQuery) throws DatabaseException {
		Session session = null;
		Transaction trans = null;
		try {
	        session = getSession();
	        trans = session.beginTransaction();
	        
	        Query query = session.createSQLQuery(sqlQuery);
	        
	        trans.commit();
	        return true;
		} catch(HibernateException e) {
			if(trans != null) trans.rollback();
			throw new DatabaseException(e);
		} finally {
			if(session != null) session.close();
		}
	}*/
	
	protected static <T> T fetch(int id, Class<T> objectClass) throws DatabaseException {
		Session session = null;
		Transaction trans = null;
		try{
			session = getSession();
			trans = session.beginTransaction();
			Object fetched = session.load(objectClass, id);
			T result = objectClass.cast(fetched);
			
			trans.commit();
			
			return result;
		} catch(HibernateException e) {
			if(trans != null) trans.rollback();
			throw new DatabaseException(e);
		}
	}
	
	protected static <T extends IdentifiedObject> T replaceNullsWithStoredData(T toComplete) throws DatabaseException {
		T storedObject = (T) fetch(toComplete.getId(), toComplete.getClass());
		return ModelMapper.replaceNullsWithStoredData(toComplete, storedObject);
	}
}
