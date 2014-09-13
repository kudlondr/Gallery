package cz.cuni.mff.java.advanced.gallery.beans;

public class ImageComments {
	
	private Image image;
	private Comment[] comments;
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Comment[] getComments() {
		return comments;
	}
	public void setComments(Comment[] comments) {
		this.comments = comments;
	}
}