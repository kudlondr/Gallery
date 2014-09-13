package cz.cuni.mff.java.advanced.gallery.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import cz.cuni.mff.java.advanced.gallery.model.data.DataManager;

public class Image extends cz.cuni.mff.java.advanced.gallery.common.Image {
	
	private UploadedFile uploadedFile;
		
    public String submit() throws IOException {
    	
        this.setStreamedData(uploadedFile.getInputStream());
        
        if(DataManager.store(this)) {
        	return "success";
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
	
	public StreamedContent getImage() {
//		FacesContext context = FacesContext.getCurrentInstance();
		
//		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
//		    // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
//		    return new DefaultStreamedContent();
//		}
//		else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            return new DefaultStreamedContent(getStreamedData());
//        }
//		return new DefaultStreamedContent(getData());
	}
}
