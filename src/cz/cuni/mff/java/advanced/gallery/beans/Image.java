package cz.cuni.mff.java.advanced.gallery.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import cz.cuni.mff.java.advanced.gallery.common.ImagePreview;
import cz.cuni.mff.java.advanced.gallery.data.ImageDataManager;
import cz.cuni.mff.java.advanced.gallery.data.MessageDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.ConversionException;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.util.ModelMapper;

public class Image extends cz.cuni.mff.java.advanced.gallery.common.Image {
	
	private UploadedFile uploadedImage;
	private UploadedFile uploadedImagePreview;
	private String commentText;
	private boolean inited = false;
	private Image[] images;
		
	public Image() {
		setCreatedDate(new Date());
	}
	
	public void initImageDetail(int imageId) {
		if(inited)
			return;
		try {
			inited = true;
			cz.cuni.mff.java.advanced.gallery.common.Image image = ImageDataManager.getImage(imageId);
			Set<cz.cuni.mff.java.advanced.gallery.common.Comment> comments = new HashSet<cz.cuni.mff.java.advanced.gallery.common.Comment>();
			for(cz.cuni.mff.java.advanced.gallery.common.Comment commentDB : image.getComments()) {
				comments.add(ModelMapper.convert(commentDB, Comment.class));
			}
			this.comments = comments;
			this.createdDate = image.getCreatedDate();
			this.data = image.getData();
			this.description = image.getDescription();
			this.hidden = image.getHidden();
			this.id = image.getId();
			this.name = image.getName();
			this.owner = image.getOwner();
			this.preview = image.getPreview();
		} catch(DatabaseException | ConversionException e) {
			e.printStackTrace();
		}
	}
	
	public ImagePreview getPreview() {
		if(preview == null) {
			preview = new cz.cuni.mff.java.advanced.gallery.beans.ImagePreview();
			preview.setCreatedDate(new Date());
		}
		return preview;
	}
	
    public String submit(User owner) throws IOException {
    	
        this.setStreamedData();
        this.setOwner(owner);
        
        try{
	        cz.cuni.mff.java.advanced.gallery.common.Image imageDb = ImageDataManager.insertImage(this);
        	MessageDataManager.sendMessages(owner, imageDb);
        	return "success";
        } catch(GalleryException e) {
        	e.printStackTrace();
        }
        
        return "refresh";
    }
    
    public UploadedFile getUploadedImage() {
        return uploadedImage;
    }
    public void setUploadedImage(UploadedFile uploadedFile) {
        this.uploadedImage = uploadedFile;
    }
    
    public UploadedFile getUploadedImagePreview() {
        return uploadedImagePreview;
    }
    public void setUploadedImagePreview(UploadedFile uploadedFile) {
        this.uploadedImagePreview = uploadedFile;
    }

	public InputStream getStreamedData() {
		return new ByteArrayInputStream(getData());
	}

	public void setStreamedData() {
		try	{
			setData(IOUtils.toByteArray(uploadedImage.getInputStream()));
			getPreview().setData(IOUtils.toByteArray(uploadedImagePreview.getInputStream()));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String goToDetail(User user) {
		user.setShowImage(getId());
		return "showImageDetail";
	}
	
	public void setCommentText(String value) {
		this.commentText = value;
	}
	public String getCommentText() {
		return commentText;
	}
	
	public String addComment(User commentSender) {
		Comment comment = new Comment();
		comment.setCreatedDate(new Date());
		comment.setHidden(false);
		comment.setSender(commentSender);
		comment.setText(getCommentText());
		try {
			ImageDataManager.addComment(this, comment);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "refresh";
	}
	
	public String hideComment(Comment commentToHide) {
		try {
			ImageDataManager.hideComment(commentToHide);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "refresh";
	}
	public String unhideComment(Comment commentToHide) {
		try {
			ImageDataManager.unhideComment(commentToHide);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "refresh";
	}
	public Comment[] getCommentsArray(int imageId) {
		initImageDetail(imageId);
		Comment[] arr = new Comment[getComments().size()];
		return getComments().toArray(arr);
	}
	
	public Image[] getCurrentImages() {
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
	
	public String hideImage() {
		try {
			ImageDataManager.hideImage(this);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "refresh";
	}
	public String showImage() {
		try {
			ImageDataManager.showImage(this);
		} catch(DatabaseException e) {
			e.printStackTrace();
		}
		return "refresh";
	}
}