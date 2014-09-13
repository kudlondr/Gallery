package cz.cuni.mff.java.advanced.gallery.model.data.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.exceptions.InvalidColumnException;
import cz.cuni.mff.java.advanced.gallery.exceptions.InvalidTypeException;
import cz.cuni.mff.java.advanced.gallery.model.data.DataConverter;
@Deprecated
public abstract class Table {
	
	private static final String insertQuery = "insert into %s (%s) values (%s)";
	
	private static final String updateQuery = "update %s set %s where %s";
	
	private static final String selectQuery = "select %s from %s where %s";
	
	private static final String deleteQuery = "delete from %s where %s";
		
	public void performInsert(Connection connection, ColumnData<?>[] data) throws GalleryException {
		validateInputData(data);
		PreparedStatement statement = null;
		try{
			StringBuilder columns = new StringBuilder();
			StringBuilder params = new StringBuilder();
			columns.append(data[0].getColumn().getName());
			params.append("?");
			for(int i = 1; i < data.length; ++i) {
				columns.append(", ");
				columns.append(data[i].getColumn().getName());
				params.append(", ?");
			}
			String query = String.format(insertQuery, getTableName(), columns.toString(), params.toString());
		
			statement = connection.prepareStatement(query);
			setParams(statement, data);
			
			statement.execute();
		} catch(SQLException e) {
			throw new DatabaseException(e);
		} finally {
			if(statement != null) {
				try{ statement.close(); }
				catch(SQLException e) { throw new DatabaseException(e); }
			}
		}
	}
	
	public final void performUpdate(Connection connection, ColumnData<?>[] data, ColumnData<?>[] keys) throws GalleryException {
		validateInputData(data);
		validateInputData(keys);
		
		PreparedStatement statement = null;
		try{
			StringBuilder toSet = new StringBuilder();
			toSet.append(data[0].getColumn().getName());
			toSet.append("=?");
			
			for(int i = 1; i < data.length; ++i) {
				toSet.append(", ");
				toSet.append(data[i].getColumn().getName());
				toSet.append("=?");
			}
			
			StringBuilder condition = new StringBuilder();
			condition.append(keys[0].getColumn().getName());
			condition.append("=?");
			for(int i = 1; i < keys.length; ++i) {
				condition.append(" and ");
				condition.append(keys[i].getColumn().getName());
				condition.append("=?");
			}
			
			String query = String.format(updateQuery, getTableName(), toSet.toString(), condition);
		
			statement = connection.prepareStatement(query);
			setParams(statement, concat(data, keys));
			
			statement.execute();
		} catch(SQLException e) {
			throw new GalleryException(e);
		} finally {
			if(statement != null) {
				try{ statement.close(); }
				catch(SQLException e) { throw new GalleryException(e); }
			}
		}
	}
	
	public final List<List<ColumnData<?>>> performSelect(Connection connection, ColumnData<?>[] keys) throws GalleryException {
		validateInputData(keys);
		PreparedStatement statement = null;
		ResultSet selectResult = null;
		
		try{
			StringBuilder toSelect = new StringBuilder();
			toSelect.append(getHeader().get(0).getName());
			
			for(int i = 1; i < getHeader().size(); ++i) {
				toSelect.append(", ");
				toSelect.append(getHeader().get(i).getName());
			}
			
			
			StringBuilder condition = new StringBuilder();
			condition.append(keys[0].getColumn().getName());
			condition.append("=?");
			for(int i = 1; i < keys.length; ++i) {
				condition.append(" and ");
				condition.append(keys[i].getColumn().getName());
				condition.append("=?");
			}
			
			String query = String.format(selectQuery, toSelect, getTableName(), condition);
		
			statement = connection.prepareStatement(query);
			setParams(statement, keys);
			
			statement.execute();
			selectResult = statement.getResultSet();
			
			return DataConverter.convertData(selectResult, this.getHeader());
			
		} catch(SQLException e) {
			throw new GalleryException(e);
		} finally {
			if(selectResult != null) {
				try{ selectResult.close(); } catch(SQLException e) { throw new DatabaseException(e); }
			}
			if(statement != null) {
				try{ statement.close(); } catch(SQLException e) { throw new DatabaseException(e); }
			}
		}
	}
	
	public final void performDelete(Connection connection, ColumnData<?>[] keys) throws GalleryException {
		validateInputData(keys);
		PreparedStatement statement = null;
		
		try{
			StringBuilder condition = new StringBuilder();
			condition.append(keys[0].getColumn().getName());
			condition.append("=?");
			for(int i = 1; i < keys.length; ++i) {
				condition.append(" and ");
				condition.append(keys[i].getColumn().getName());
				condition.append("=?");
			}
			
			String query = String.format(deleteQuery, getTableName(), condition);
		
			statement = connection.prepareStatement(query);
			setParams(statement, keys);
			
			statement.execute();
		} catch(SQLException e) {
			throw new GalleryException(e);
		} finally {
			if(statement != null) {
				try{ statement.close(); }
				catch(SQLException e) { throw new GalleryException(e); }
			}
		}
	}
	
	private void validateInputData(ColumnData<?>[] data) throws InvalidColumnException, InvalidTypeException {
		for(ColumnData<?> dataColumn : data) {
			if(!getHeader().contains(dataColumn.getColumn())) {
				throw new InvalidColumnException("Invalid column "+dataColumn+" for insert into "+getTableName());
			}
		}
	}
	
	private void setParams(PreparedStatement statement, ColumnData<?>[] data) throws SQLException {
		
		for(int parameterIndex = 1, dataIndex = 0; dataIndex < data.length; ++parameterIndex, ++ dataIndex) {
			if(data[dataIndex].getData() instanceof String) {
				statement.setString(parameterIndex, (String) data[dataIndex].getData());
			} else if(data[dataIndex].getData() instanceof Boolean) {
				statement.setBoolean(parameterIndex, (Boolean) data[dataIndex].getData());
			} else if(data[dataIndex].getData() instanceof byte[]) {
				byte[] dataArray = (byte[]) data[dataIndex].getData();
				statement.setBlob(parameterIndex, new ByteInputStream(dataArray, dataArray.length));
			} else if(data[dataIndex].getData() instanceof Integer) {
				statement.setInt(parameterIndex, (Integer) data[dataIndex].getData());
			}
		}
	}
	
	public abstract String getTableName();
	
	public abstract List<Column> getHeader();
	
	public abstract List<Column> getKeys();
	
	public abstract List<Column> getData();
	
	private ColumnData<?>[] concat(ColumnData<?>[] first, ColumnData<?>[] second) {
		
		ColumnData<?>[] concated = new ColumnData<?>[first.length + second.length];
		System.arraycopy(first, 0, concated, 0, first.length);
		System.arraycopy(second, 0, concated, first.length, second.length);
		return concated;
	}
}
