package cz.cuni.mff.java.advanced.gallery.model.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
@Deprecated
public class DatabaseConnector {
		
	private static boolean available = false;
	
	static {
		try {
			Class.forName("org.h2.Driver");
			available = true;
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection createConnection() throws DatabaseException {
		
		if(!isConnectionAvailable()) throw new DatabaseException("Database connection is unavailable, because org.h2.Driver was not loaded.");
		
		try{
		    return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/h2/galerie", "sa", "");
		    
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public static void closeConnection(Connection connection) throws DatabaseException {
		try{
			connection.close();
			
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	public static boolean isConnectionAvailable() {
		
		return available;
	}
	
	public static boolean isDatabaseAvailable() throws DatabaseException {
		Connection connection = createConnection();
		closeConnection(connection);
		
		return true;
	}
}