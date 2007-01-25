package hub.sam.mof.mas;

import java.util.Collection;
import java.util.Vector;

import hub.sam.util.Clusterable;

public class SemanticException extends RuntimeException  implements Clusterable {

	private static final long serialVersionUID = 1L;

	public SemanticException(Throwable cause) {
		super(cause);
	}

	public SemanticException(String msg) {
		super(msg);
	}
	
	private Collection exceptions = new Vector();

	@SuppressWarnings("unchecked")
	public void addException(SemanticException ex) {
		exceptions.add(ex);
	}
	
	public Collection getExceptions() {
		return exceptions;
	}
}
