package cz.cuni.mff.java.advanced.gallery.beans;

import cz.cuni.mff.java.advanced.gallery.exceptions.FactoryException;

public class Factory {
	
	public static Image createImageId(String imageId) throws FactoryException {
		Image image = new Image();
		try {
//			int identity = Integer.parseInt(imageId);
//			image.setId(identity);
		} catch(NumberFormatException e) {
			throw new FactoryException(e);
		}
		
		return image;
	}
}
