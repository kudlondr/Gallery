package cz.cuni.mff.java.advanced.gallery.common;

public abstract class ImagePreview extends IdentifiedObject {
	protected byte[] data;
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
