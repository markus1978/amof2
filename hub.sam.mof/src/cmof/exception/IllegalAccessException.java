package cmof.exception;

/**
 * This exception is used whenever a object is illegaly accessed, e.g. accessing an already deleted object.
 */
public class IllegalAccessException extends ModelException {

    public IllegalAccessException() {
        super();
    }

    public IllegalAccessException(String msg) {
        super(msg);
    }
}
