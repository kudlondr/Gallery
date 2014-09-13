package cz.cuni.mff.java.advanced.gallery.model.data.tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Deprecated
public class Row {
	
	private List<Column> columns;
	
	public Row(Column... columns) {
		this.columns = new ArrayList<Column>(Arrays.asList(columns));
	}

	public List<Column> getColumns() {
		return columns;
	}
}
