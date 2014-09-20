package cz.cuni.mff.java.advanced.gallery.beans;

import java.util.Collection;

import cz.cuni.mff.java.advanced.gallery.data.ImageDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;

public class ImagesContainer {
	private Image[] images;

	public Image[] getImages() {
		if(images == null || images.length == 0) {
			try {
				
				Collection<cz.cuni.mff.java.advanced.gallery.common.Image> imagesCommonCol = ImageDataManager.getMostCurrentImagesList(10);
				Collection<Image> imagesCol = cz.cuni.mff.java.advanced.gallery.util.ModelMapper.convert(imagesCommonCol, Image.class);
				
				images = new Image[imagesCol.size()];
				images = imagesCol.toArray(images);
				
			} catch(GalleryException e) {
				e.printStackTrace();
			}
		}
		
		return this.images;
	}

	public void setImages(Image[] images) {
		this.images = images;
	}
	
//	public void addImage(Image image) {
//		if(imagesList == null)
//			imagesList = new ArrayList<Image>();
//		
//		imagesList.add(image);
//	}
}
