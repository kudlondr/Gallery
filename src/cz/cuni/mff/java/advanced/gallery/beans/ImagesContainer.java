package cz.cuni.mff.java.advanced.gallery.beans;

import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.model.data.DatabaseController;

public class ImagesContainer {
	private List<Image> imagesList;
	private Image[] images;

	public Image[] getImages() {
		if(imagesList == null || imagesList.isEmpty()) {
			try{
				DatabaseController.getMostCurrentImagesList(this);
				images = new Image[imagesList.size()];
				images = imagesList.toArray(images);
			} catch(GalleryException e) {
				this.imagesList = new ArrayList<Image>();
			}
		}
		
		return this.images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}
	
	public void addImage(Image image) {
		if(imagesList == null)
			imagesList = new ArrayList<Image>();
		
		imagesList.add(image);
	}
}
