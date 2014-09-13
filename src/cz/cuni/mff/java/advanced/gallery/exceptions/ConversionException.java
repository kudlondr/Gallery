package cz.cuni.mff.java.advanced.gallery.exceptions;

public class ConversionException extends GalleryException {

	private static final long serialVersionUID = -7307480110018621508L;

	public ConversionException(Exception e) {
		super(e);
	}
}
