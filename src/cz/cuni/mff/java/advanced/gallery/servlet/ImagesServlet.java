package cz.cuni.mff.java.advanced.gallery.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.cuni.mff.java.advanced.gallery.common.Image;
import cz.cuni.mff.java.advanced.gallery.data.ImageDataManager;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;

/**
 * Servlet implementation class ImagesServlet
 */
@WebServlet("/faces/images/*")
public class ImagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImagesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imageId = String.valueOf(request.getPathInfo().substring(1)); // Gets string that goes after "/image/".
		Image image = null;
		BufferedInputStream input = null;
        BufferedOutputStream output = null;
        
		try {
			int identity = Integer.parseInt(imageId);			
		    image = ImageDataManager.getImage(identity); // Get Image from DB.
		    
		    response.setHeader("Content-Type", getServletContext().getMimeType(image.getName()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");
	        
	        if(image.getPreview() == null) {
	        	return;
	        }
	        
	        input = new BufferedInputStream(new ByteArrayInputStream(image.getPreview().getData())); // Creates buffered input stream.
            output = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            for (int length = 0; (length = input.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }
            
		} catch(NumberFormatException e) {
			e.printStackTrace();
		} catch(DatabaseException e) {
			e.printStackTrace();
		} finally {
            if (output != null) try { output.close(); } catch (IOException e) { e.printStackTrace(); }
            if (input != null) try { input.close(); } catch (IOException e) { e.printStackTrace(); }
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}