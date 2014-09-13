package cz.cuni.mff.java.advanced.gallery.model.data.tables;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.RecordExistsException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.model.data.DataConverter;
@Deprecated
public class Image extends Table {
	
	private static final String unique = "U_IMAGES";
	
	private static final String tableName = "images";
	
	public static final Column idColumn = new Column("id", Integer.class);
	public static final Column createdDateColumn = new Column("createdDate", Date.class);
	public static final Column hiddenColumn = new Column("hidden", Boolean.class);
	public static final Column dataColumn = new Column("data", InputStream.class);
	public static final Column userIdColumn = new Column("userId", Integer.class);
	public static final Column nameColumn = new Column("name", String.class);
	public static final Column descriptionColumn = new Column("description", Reader.class);
	
	private static final List<Column> insertColumns = Arrays.asList(dataColumn, userIdColumn, nameColumn, descriptionColumn);
	
	private static final List<Column> updateColumns = Arrays.asList(hiddenColumn, nameColumn, descriptionColumn);
	
	private static final List<Column> ids = Arrays.asList(idColumn);
	
	private static final List<Column> data = Arrays.asList(idColumn, createdDateColumn, hiddenColumn, dataColumn, descriptionColumn);
	
	private static final List<Column> keys = Arrays.asList(userIdColumn, nameColumn);
	
	private static final List<Column> columns = Arrays.asList(
			idColumn, createdDateColumn, hiddenColumn, dataColumn, userIdColumn, nameColumn);
	
	@Override
	public void performInsert(Connection connection, ColumnData<?>[] data) throws GalleryException {
		//TODO might be useful to check name+userId combination before sending the image from user... so that there wont be any need to submit the image more than once
		try{
			super.performInsert(connection, data);
		} catch(DatabaseException e) {
			if(e.getMessage().contains(unique))
				throw new RecordExistsException(e);
		}
	}
	
	public final List<List<ColumnData<?>>> performSelectCurrentList(Connection connection, int lastN) throws GalleryException {
		PreparedStatement statement = null;
		ResultSet selectResult = null;
		
		try{
			StringBuilder toSelect = new StringBuilder();
			toSelect.append(getHeader().get(0).getName());
			
			for(int i = 1; i < getHeader().size(); ++i) {
				toSelect.append(", ");
				toSelect.append(getHeader().get(i).getName());
			}
			
			//TODO keys asi nepujde takhle pridat!!!!!!!
			String query = "SELECT "+toSelect+" FROM (select "+keys+" from "+tableName+" order by "+createdDateColumn.getName()+" desc) where rownum <= ?";
		
			statement = connection.prepareStatement(query);
			statement.setInt(1, lastN);
			
			statement.execute();
			selectResult = statement.getResultSet();
			
			return DataConverter.convertData(selectResult, this.getHeader());
			
		} catch(SQLException e) {
			throw new GalleryException(e);
		} finally {
			if(selectResult != null) {
				try{ selectResult.close(); }
				catch(SQLException e) { throw new DatabaseException(e); }
			}
			if(statement != null) {
				try{ statement.close(); }
				catch(SQLException e) { throw new DatabaseException(e); }
			}
		}
	}
	
	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public List<Column> getHeader() {
		return columns;
	}

	@Override
	public List<Column> getKeys() {
		return keys;
	}

	@Override
	public List<Column> getData() {
		return data;
	}

	public List<Column> getInsertColumns() {
		return insertColumns;
	}
	
	public List<Column> getUpdateColumns() {
		return updateColumns; 
	}
	
	public List<Column> getIds() {
		return ids;
	}
}
