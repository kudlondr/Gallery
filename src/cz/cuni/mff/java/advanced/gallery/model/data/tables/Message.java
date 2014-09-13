package cz.cuni.mff.java.advanced.gallery.model.data.tables;

import java.io.Reader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Deprecated
public class Message extends Table {
	private static final String tableName = "messages";
	
	public static final Column idColumn = new Column("id", Integer.class);
	public static final Column responseIdColumn = new Column("responseId", Integer.class);
	public static final Column createdByColumn = new Column("createdBy", Integer.class);
	public static final Column belongsToColumn = new Column("belongsTo", Integer.class);
	public static final Column createdDateColumn = new Column("createDate", Date.class);
	public static final Column textColumn = new Column("text", Reader.class);
	public static final Column readColumn = new Column("read", Boolean.class);
	
	public static final List<Column> insertColumns = Arrays.asList(
			idColumn, createdByColumn, belongsToColumn, responseIdColumn, textColumn);
	
	public static final List<Column> updateKeyColumns = Arrays.asList(idColumn);
	
	public static final List<Column> updateReadColumns = Arrays.asList(readColumn);
	
	public static final List<Column> selectAllKeys = Arrays.asList(belongsToColumn, createdByColumn);
	
	private static List<Column> columns = Arrays.asList(
			idColumn, createdByColumn, belongsToColumn, createdDateColumn, responseIdColumn, textColumn, responseIdColumn);
	
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
		return null;
	}

	@Override
	public List<Column> getData() {
		return columns;
	}
}