package cz.cuni.mff.java.advanced.gallery.common;

import java.util.HashSet;
import java.util.Set;

public abstract class Watchlist extends IdentifiedObject {
	protected User owner;
	protected Set<User> watching;
	
	public User getBelongsTo() {
		return owner;
	}
	public void setBelongsTo(User belongsTo) {
		this.owner = belongsTo;
	}
	public Set<User> getWatching() {
		if(watching == null) {
			watching = new HashSet<User>();
		}
		return watching;
	}
	public void setWatching(Set<User> watching) {
		this.watching = watching;
	}
}
