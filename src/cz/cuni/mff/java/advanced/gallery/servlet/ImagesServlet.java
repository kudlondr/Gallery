package cz.cuni.mff.java.advanced.gallery.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.cuni.mff.java.advanced.gallery.beans.Factory;
import cz.cuni.mff.java.advanced.gallery.beans.Image;
import cz.cuni.mff.java.advanced.gallery.model.data.DatabaseController;

/**
 * Servlet implementation class ImagesServlet
 */
@WebServlet("/image/*")
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
//		String imageId = String.valueOf(request.getPathInfo().substring(1)); // Gets string that goes after "/images/".
//		Image imageIdentity = Factory.createImageId(imageId);
//		
//        Image image = DatabaseController.getImage(imageIdentity); // Get Image from DB.

        
        //TODO do xhtml dam <img src="/image/...jehoID".../> to zavola tenhle servlet a vratim v nem byte[] obrazku
        
        
        
        
        
        
        
        
//        response.setHeader("Content-Type", getServletContext().getMimeType(image.getName()));
//        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");
//
//        BufferedInputStream input = null;
//        BufferedOutputStream output = null;
//
//        try {
//            input = new BufferedInputStream(image.getData()); // Creates buffered input stream.
//            output = new BufferedOutputStream(response.getOutputStream());
//            byte[] buffer = new byte[8192];
//            for (int length = 0; (length = input.read(buffer)) > 0;) {
//                output.write(buffer, 0, length);
//            }
//        } finally {
//            if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
//            if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
//        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
