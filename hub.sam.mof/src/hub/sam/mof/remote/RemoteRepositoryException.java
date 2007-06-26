package hub.sam.mof.remote;

import cmof.exception.ModelException;

public class RemoteRepositoryException extends ModelException {

	public RemoteRepositoryException() {
		super();
	}

	public RemoteRepositoryException(Exception cause) {
		super(cause);
	}

	public RemoteRepositoryException(String msg, Exception e) {
		super(msg, e);
	}

	public RemoteRepositoryException(String msg) {
		super(msg);
	}

}
