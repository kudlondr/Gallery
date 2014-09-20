package cz.cuni.mff.java.advanced.gallery.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import cz.cuni.mff.java.advanced.gallery.data.ImageDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;

public class Image extends cz.cuni.mff.java.advanced.gallery.common.Image {
	
	private UploadedFile uploadedFile;
		
    public String submit() throws IOException {
    	
        this.setStreamedData(uploadedFile.getInputStream());
        
        try{
	        if(ImageDataManager.saveImage(this)) {
	        	return "success";
	        }
        } catch(GalleryException e) {
        	e.printStackTrace();
        }
        
        return "failure";
    }
    
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

	public InputStream getStreamedData() {
		return new ByteArrayInputStream(getData());
	}

	public void setStreamedData(InputStream data) {
		try	{
			setData(IOUtils.toByteArray(data));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
