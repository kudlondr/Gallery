package cz.cuni.mff.java.advanced.gallery.model.data;

import java.sql.Connection;
import java.util.List;

import cz.cuni.mff.java.advanced.gallery.beans.ImagesContainer;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.ColumnData;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.Comment;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.Image;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.Message;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.User;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.Watchlists;
@Deprecated
public class DatabaseController {
	
	public static void createUser(cz.cuni.mff.java.advanced.gallery.beans.User userBean) throws GalleryException {
		
		Connection connection = DatabaseConnector.createConnection();
		User userTable = new User();
		
		ColumnData<?>[] databaseObject = DataConverter.convertData(userBean, userTable.getHeader());
		
		userTable.performInsert(connection, databaseObject);
		DatabaseConnector.closeConnection(connection);
	}
	
	public static void updateUser(cz.cuni.mff.java.advanced.gallery.beans.User userBean) throws GalleryException {
		
		Connection connection = DatabaseConnector.createConnection();
		User userTable = new User();
		
		ColumnData<?>[] databaseObjectData = DataConverter.convertData(userBean,
				userBean.getPassword().isEmpty() ? userTable.getPartialData() : userTable.getData());
		ColumnData<?>[] databaseObjectKeys = DataConverter.convertData(userBean, userTable.getKeys());
		
		userTable.performUpdate(connection, databaseObjectData, databaseObjectKeys);
		DatabaseConnector.closeConnection(connection);
	}
	
	public static boolean isLoginCorrect(cz.cuni.mff.java.advanced.gallery.beans.User userBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		User userTable = new User();
		
		ColumnData<?>[] databaseObjectKeys = DataConverter.convertData(userBean, userTable.getAutenticationColumns());
		
		List<List<ColumnData<?>>> usersFromDatabase = userTable.performSelect(connection, databaseObjectKeys);
		
		if(!usersFromDatabase.isEmpty()) {
			DataConverter.convertData(usersFromDatabase.get(0), userTable, userBean);
		}
		DatabaseConnector.closeConnection(connection);
		return !usersFromDatabase.isEmpty();
	}
	
	public static void createImage(cz.cuni.mff.java.advanced.gallery.beans.Image imageBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Image imageTable = new Image();
		ColumnData<?>[] data = DataConverter.convertData(imageBean, imageTable.getInsertColumns());
		imageTable.performInsert(connection, data);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static void hideImage(cz.cuni.mff.java.advanced.gallery.beans.Image imageBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Image imageTable = new Image();
		ColumnData<?>[] data = DataConverter.convertData(imageBean, imageTable.getUpdateColumns());
		ColumnData<?>[] keys = DataConverter.convertData(imageBean, imageTable.getIds());
		
		imageTable.performUpdate(connection, data, keys);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static cz.cuni.mff.java.advanced.gallery.beans.Image getImage(cz.cuni.mff.java.advanced.gallery.beans.Image imageIdentity) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		cz.cuni.mff.java.advanced.gallery.beans.Image imageBeanResult = new cz.cuni.mff.java.advanced.gallery.beans.Image();
		Image imageTable = new Image();
		ColumnData<?>[] keys = DataConverter.convertData(imageIdentity, imageTable.getKeys());
		
		List<List<ColumnData<?>>> foundImages = imageTable.performSelect(connection, keys);
		
		if(!foundImages.isEmpty()) {
			imageBeanResult = DataConverter.convertData(foundImages.get(0), imageTable, new cz.cuni.mff.java.advanced.gallery.beans.Image());
		} else {
			imageBeanResult = null;
		}
			
		DatabaseConnector.closeConnection(connection);
		
		return imageBeanResult;
	}
	
	public static void getMostCurrentImagesList(ImagesContainer container) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Image imageTable = new Image();
		List<List<ColumnData<?>>> dbImages = imageTable.performSelectCurrentList(connection, 10);
		
		for(List<ColumnData<?>> row : dbImages) {
			Object converted = DataConverter.convertData(row, imageTable);
			container.addImage((cz.cuni.mff.java.advanced.gallery.beans.Image) converted);
		}
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static void createComment(cz.cuni.mff.java.advanced.gallery.beans.Comment commentBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Comment commentTable = new Comment();
		ColumnData<?>[] columns = DataConverter.convertData(commentBean, Comment.insertColumns);
		
		commentTable.performInsert(connection, columns);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static void hideComment(cz.cuni.mff.java.advanced.gallery.beans.Comment commentBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Comment commentTable = new Comment();
		ColumnData<?>[] keys = DataConverter.convertData(commentBean, Comment.updateKeyColumns);
		ColumnData<?>[] hidden = DataConverter.convertData(commentBean, Comment.updateHiddenColumns);
		
		commentTable.performUpdate(connection, hidden, keys);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static cz.cuni.mff.java.advanced.gallery.beans.Comment[] getComments(
			cz.cuni.mff.java.advanced.gallery.beans.Image commentsToImage) throws GalleryException {
		
		return getComments(commentsToImage.getId(), "I"); //I stands for image TODO const
	}
	
	public static cz.cuni.mff.java.advanced.gallery.beans.Comment[] getComments(
			cz.cuni.mff.java.advanced.gallery.beans.User commentsToUserPage) throws GalleryException {
		
		return getComments(commentsToUserPage.getId(), "U"); //U stands fo User TODO create const
	}
	
	private static cz.cuni.mff.java.advanced.gallery.beans.Comment[] getComments(int id, String type) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Comment commentTable = new Comment();
		ColumnData<?>[] keys = new ColumnData<?>[]{
				new ColumnData<Integer>(Comment.idColumn, id),
				new ColumnData<String>(Comment.typeColumn, type)}; //I stands for image TODO constant
		
		List<List<ColumnData<?>>> result = commentTable.performSelect(connection, keys);
		
		cz.cuni.mff.java.advanced.gallery.beans.Comment[] resultComments = new cz.cuni.mff.java.advanced.gallery.beans.Comment[result.size()];
		
		for(int i = 0; i < result.size(); ++i) {
			resultComments[i] = (cz.cuni.mff.java.advanced.gallery.beans.Comment) DataConverter.convertData(result.get(i), commentTable);
		}
		
		DatabaseConnector.closeConnection(connection);
		
		return resultComments;
	}
	
	public static void createMessage(cz.cuni.mff.java.advanced.gallery.beans.Message messageBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Message messageTable = new Message();
		ColumnData<?>[] columns = DataConverter.convertData(messageBean, Message.insertColumns);
		
		messageTable.performInsert(connection, columns);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static void markAsReadMessage(cz.cuni.mff.java.advanced.gallery.beans.Message messageBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Message messageTable = new Message();
		ColumnData<?>[] keys = DataConverter.convertData(messageBean, Message.updateKeyColumns);
		ColumnData<?>[] columns = DataConverter.convertData(messageBean, Message.updateReadColumns);
		
		messageTable.performUpdate(connection, columns, keys);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static void addToWatchList(cz.cuni.mff.java.advanced.gallery.beans.Watchlist watchlistBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Watchlists watchlistTable = new Watchlists();
		ColumnData<?>[] columns = DataConverter.convertData(watchlistBean, Watchlists.insertColumns);
		
		watchlistTable.performInsert(connection, columns);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static void removeFromWatchList(cz.cuni.mff.java.advanced.gallery.beans.Watchlist watchlistBean) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Watchlists watchlistTable = new Watchlists();
		ColumnData<?>[] keys = DataConverter.convertData(watchlistBean, Watchlists.keyColumns);
		
		watchlistTable.performDelete(connection, keys);
		
		DatabaseConnector.closeConnection(connection);
	}
	
	public static cz.cuni.mff.java.advanced.gallery.beans.Watchlist[] getWatching(cz.cuni.mff.java.advanced.gallery.beans.User user) throws GalleryException {
		Connection connection = DatabaseConnector.createConnection();
		
		Watchlists watchlistTable = new Watchlists();
		ColumnData<?>[] keys = new ColumnData<?>[]{ new ColumnData<Integer>(Watchlists.belongsToColumn, user.getId()) };
		
		List<List<ColumnData<?>>> result = watchlistTable.performSelect(connection, keys);
		
		cz.cuni.mff.java.advanced.gallery.beans.Watchlist[] resultComments = new cz.cuni.mff.java.advanced.gallery.beans.Watchlist[result.size()];
		
		for(int i = 0; i < result.size(); ++i) {
			resultComments[i] = (cz.cuni.mff.java.advanced.gallery.beans.Watchlist) DataConverter.convertData(result.get(i), watchlistTable);
		}
		
		DatabaseConnector.closeConnection(connection);
		
		return resultComments;
	}
}
