package cz.cuni.mff.java.advanced.gallery.exceptions;

public class SecurityException extends GalleryException {
	
	private static final long serialVersionUID = 2512803080400983203L;
	
	public SecurityException(Exception e) {
		super(e);
	}
	public SecurityException(String message) {
		super(message);
	}
}
