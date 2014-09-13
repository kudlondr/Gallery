package cz.cuni.mff.java.advanced.gallery.model.data.tables;

import cz.cuni.mff.java.advanced.gallery.exceptions.InvalidTypeException;
@Deprecated
public class ColumnData<T> {
	
	private Column column;
	private T data;
	
	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public ColumnData(Column column, T data) throws InvalidTypeException {
		this.column = column;
		this.data = data;
		
		checkType();
	}
	
	private void checkType() throws InvalidTypeException {
		if(!column.getType().equals(data.getClass()) && !column.getType().isAssignableFrom(data.getClass()) ) {
			throw new InvalidTypeException("Column type "+column.getType()+" is not equal to "+data.getClass()+" data type.");
		}
	}
}
