package cz.cuni.mff.java.advanced.gallery.exceptions;

public class RecordExistsException extends GalleryException {

	private static final long serialVersionUID = -3320692439760478756L;

	public RecordExistsException(Exception e) {
		super(e);
	}
}
