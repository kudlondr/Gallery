package cz.cuni.mff.java.advanced.gallery.model.data.tables;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.RecordExistsException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
@Deprecated
public class User extends Table {

	private static final String unique = "U_USERS";
	
	private static final String tableName = "users";
	
	public static final Column idColumn = new Column("id", Integer.class);
	public static final Column nameColumn = new Column("name", String.class);
	public static final Column surnameColumn = new Column("surname", String.class);
	public static final Column showEmailColumn = new Column("showemail", Boolean.class);
	public static final Column showNameColumn = new Column("showname", Boolean.class);
	public static final Column showSurnameColumn = new Column("showsurname", Boolean.class);
	public static final Column userNameColumn = new Column("username", String.class);
	public static final Column passwordColumn = new Column("password", String.class);
	public static final Column emailColumn = new Column("email", String.class);
	
	private static final List<Column> autentication = Arrays.asList(userNameColumn, passwordColumn);
	private static List<Column> allData = Arrays.asList(
			idColumn, nameColumn, surnameColumn, showEmailColumn, showNameColumn, showSurnameColumn,
			userNameColumn, passwordColumn, emailColumn);
	private static List<Column> partialData = Arrays.asList(
			idColumn, nameColumn, surnameColumn, showEmailColumn, showNameColumn, showSurnameColumn,
			userNameColumn, emailColumn);
	
	private static final List<Column> keys = Arrays.asList(emailColumn);
	
	private static List<Column> columns = Arrays.asList(idColumn, nameColumn, surnameColumn,
			showEmailColumn, showNameColumn, showSurnameColumn, userNameColumn, passwordColumn,
			emailColumn);

	public List<Column> getAutenticationColumns() {
		return autentication;
	}
	
	@Override
	public void performInsert(Connection connection, ColumnData<?>[] data) throws GalleryException {
		try{
			super.performInsert(connection, data);
		} catch(DatabaseException e) {
			if(e.getMessage().contains(unique))
				throw new RecordExistsException(e);
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
		return allData;
	}
	
	public List<Column> getPartialData() {
		return partialData;
	}
}
