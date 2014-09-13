package cz.cuni.mff.java.advanced.gallery.model.data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DataManager {
	
	private static Session getSession() {
		SessionFactory factory = HibernateUtil.getSessionFactory();
        return factory.openSession();
	}
	
	public static <T> boolean store(T object) {
        Session session = getSession();
        session.beginTransaction();
        
        session.save(object);

        session.getTransaction().commit();
        
        return true;
    }
	
	public static <T> T fetch(int id, Class<T> objectClass) {
		Session session = getSession();
		session.beginTransaction();
		
		return objectClass.cast(session.load(objectClass, id));
	}
}
