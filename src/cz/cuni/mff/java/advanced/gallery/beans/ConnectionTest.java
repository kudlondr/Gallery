package cz.cuni.mff.java.advanced.gallery.beans;

import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.model.data.DatabaseConnector;

public class ConnectionTest {

	private String result = "unknown";
	
	public void setResult(String value) {
		result = value;
	}
	
	public String getResult() {
		return result;
	}
	
	public String performTest() {
		try{
			if(DatabaseConnector.isDatabaseAvailable()) {
				setResult("available");
				return "success";
			}
		} catch(DatabaseException e){
			e.printStackTrace();
		}
		
		setResult("unavailable");
		return "failure";
	}
}
