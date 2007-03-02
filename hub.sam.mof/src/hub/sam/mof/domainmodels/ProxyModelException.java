package hub.sam.mof.domainmodels;

import cmof.exception.ModelException;

public class ProxyModelException extends ModelException {
    public ProxyModelException(String msg) {
        super(msg);
    }

    public ProxyModelException(String msg, Exception t) {
        super(msg, t);
    }
}
