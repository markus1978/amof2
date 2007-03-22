package hub.sam.mof.ocl;

import cmof.exception.ModelException;

public class OclException extends ModelException {

	public OclException(Exception cause) {
		super(cause);
	}
	
	public OclException(String msg, Exception cause) {
		super(msg, cause);
	}
	
	public OclException(String msg) {
		super(msg);
	}

}
