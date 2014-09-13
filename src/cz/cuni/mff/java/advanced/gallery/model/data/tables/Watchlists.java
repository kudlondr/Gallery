package cz.cuni.mff.java.advanced.gallery.model.data.tables;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Deprecated
public class Watchlists extends Table {
	private static final String tableName = "watchlists";
	
	public static final Column idColumn = new Column("id", Integer.class);
	public static final Column belongsToColumn = new Column("belongsTo", Integer.class);
	public static final Column watchingColumn = new Column("watching", Integer.class);
	public static final Column createdDateColumn = new Column("createDate", Date.class);
	
	public static final List<Column> insertColumns = Arrays.asList(
			belongsToColumn, watchingColumn);
	
	public static final List<Column> keyColumns = Arrays.asList(idColumn);
	
	private static List<Column> columns = Arrays.asList(
			idColumn, belongsToColumn, watchingColumn, createdDateColumn);
	
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
