package hub.sam.mof.as;

import java.util.Collection;
import java.util.Vector;

import hub.sam.util.Clusterable;

public class AsSemanticException extends RuntimeException  implements Clusterable {

	private static final long serialVersionUID = 1L;

	public AsSemanticException(Throwable cause) {
		super(cause);
	}

	public AsSemanticException(String msg) {
		super(msg);
	}
	
	private Collection exceptions = new Vector();

	@SuppressWarnings("unchecked")
	public void addException(AsSemanticException ex) {
		exceptions.add(ex);
	}
	
	public Collection getExceptions() {
		return exceptions;
	}
}
