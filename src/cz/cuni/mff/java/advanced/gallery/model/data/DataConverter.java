package cz.cuni.mff.java.advanced.gallery.model.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cz.cuni.mff.java.advanced.gallery.exceptions.ConversionException;
import cz.cuni.mff.java.advanced.gallery.exceptions.DatabaseException;
import cz.cuni.mff.java.advanced.gallery.exceptions.GalleryException;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.Column;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.ColumnData;
import cz.cuni.mff.java.advanced.gallery.model.data.tables.Table;
@Deprecated
public class DataConverter {
	public static ColumnData<?>[] convertData(Object beanObject, List<Column> tableHeader) throws ConversionException {
		
		List<ColumnData<?>> result = new ArrayList<ColumnData<?>>();
		
		for(Column column: tableHeader) {
			try {
				Method method = beanObject.getClass().getMethod(
						"get" + Character.toUpperCase(column.getName().charAt(0)) + column.getName().substring(1));
				Object data = method.invoke(beanObject);
				
				result.add(new ColumnData<Object>(column, data));
			} catch(Exception e) {
				throw new ConversionException(e);
			}
		}
		
		ColumnData<?>[] resultArray = new ColumnData<?>[result.size()];
		return result.toArray(resultArray);
	}
	
	public static Object convertData(List<ColumnData<?>> databaseRecord, Table tableDescription) throws ConversionException {
		try{
			Class<?> beanClass = Class.forName(
					"cz.cuni.mff.java.advanced.gallery.beans." + Character.toUpperCase(tableDescription.getTableName().charAt(0))
					+ tableDescription.getTableName().substring(1, tableDescription.getTableName().length() - 1));
			Object beanInstance = beanClass.newInstance();
			
			return convertData(databaseRecord, tableDescription, beanInstance);
			
		} catch(ClassNotFoundException e) {
			throw new ConversionException(e);
		} catch(IllegalAccessException e) {
			throw new ConversionException(e);
		} catch(InstantiationException e) {
			throw new ConversionException(e);
		}
	}
	
	public static <T> T convertData(List<ColumnData<?>> databaseRecord, Table tableDescription, T beanInstance) throws ConversionException {
		try{
			Class<T> beanClass = (Class<T>) beanInstance.getClass();
			
			for(ColumnData<?> data : databaseRecord) {
				Method setMethod = beanClass.getMethod(
						"set" + Character.toUpperCase(data.getColumn().getName().charAt(0)) + data.getColumn().getName().substring(1),
						data.getColumn().getType());
				setMethod.invoke(beanInstance, data.getData());
			}
			
			return beanInstance;
			
		} catch(NoSuchMethodException e) {
			throw new ConversionException(e);
		} catch(IllegalAccessException e) {
			throw new ConversionException(e);
		} catch(InvocationTargetException e) {
			throw new ConversionException(e);
		}
	}
	
	public static List<List<ColumnData<?>>> convertData(ResultSet databaseResult, List<Column> columns) throws GalleryException {
		List<List<ColumnData<?>>> converted = new ArrayList<List<ColumnData<?>>>();
		
		try {
			while(databaseResult.next()) {
				List<ColumnData<?>> row = new ArrayList<ColumnData<?>>();
				for(int index = 0; index < columns.size(); ++index) {
					String typeName = columns.get(index).getType().getSimpleName();
					
					typeName = fixTypeName(typeName);
					
					Method getMethod = ResultSet.class.getMethod("get" + typeName, int.class); 
					Object result = getMethod.invoke(databaseResult, index + 1);
					
					result = fixResultType(result);
					
					row.add(new ColumnData<Object>(columns.get(index), result));
				}
				converted.add(row);
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		} catch(NoSuchMethodException e) {
			throw new ConversionException(e);
		} catch(SecurityException e) {
			throw new ConversionException(e);
		} catch(InvocationTargetException e) {
			throw new ConversionException(e);
		} catch(IllegalAccessException e) {
			throw new ConversionException(e);
		}
		
		return converted;
	}
	
	private static String fixTypeName(String typeName) {
		if("Integer".equals(typeName)) {
			return "Int";
		}
		if("byte[]".equals(typeName) || "InputStream".equals(typeName)) {
			return "Blob";
		}
		if("Reader".equals(typeName)) {
			return "Clob";
		}
		return typeName;
	}
	
	private static Object fixResultType(Object toConvert) throws SQLException {
		if(toConvert instanceof Blob) {
			return ((Blob) toConvert).getBinaryStream();
		}
		if(toConvert instanceof Clob) {
			return ((Clob) toConvert).getCharacterStream();
		}
		return toConvert;
	}
}
