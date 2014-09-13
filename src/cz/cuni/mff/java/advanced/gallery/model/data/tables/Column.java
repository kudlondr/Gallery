package cz.cuni.mff.java.advanced.gallery.model.data.tables;

@Deprecated
public class Column {
	protected String name;
	protected Class type;
	
	public Column(String name, Class type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Class getType() {
		return this.type;
	}
	
	public boolean equals(Object obj) {
		
		return obj instanceof Column
				&& ((Column)obj).getName().equals(this.name)
				&& ((Column)obj).getType().equals(this.type);
	}
	
	@Override
	public String toString() {
		return name + " " + type;
	}
}
