package cz.cuni.mff.java.advanced.gallery.exceptions;

public class GalleryException extends Exception {

	private static final long serialVersionUID = 4472493021708535564L;
	
	public GalleryException(String message) {
		super(message);
	}
	
	public GalleryException(Exception e) {
		super(e);
	}
}
