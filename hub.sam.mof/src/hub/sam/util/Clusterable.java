package hub.sam.util;

import java.util.Collection;

public interface Clusterable<T> {

	public Collection<T> getExceptions();
	
	public String getMessage();
}
