package cz.cuni.mff.java.advanced.gallery.exceptions;

public class DatabaseException extends GalleryException {

	private static final long serialVersionUID = 5815453901885552478L;

	public DatabaseException(String message) {
		super(message);
	}
	
	public DatabaseException(Exception e) {
		super(e);
	}
}
