package hub.sam.mof.util;

public class AssertionException extends RuntimeException {

	public AssertionException() {
		super();
	}
	
	public AssertionException(String message) {
		super(message);
	}
	
	public AssertionException(Throwable e) {
		super(e);
	}
}
