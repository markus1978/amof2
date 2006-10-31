package hub.sam.mof.merge;

import cmof.exception.ModelException;

@SuppressWarnings("serial")
public final class MergeException extends ModelException {

    MergeException(String msg) {
        super(msg);
    }
}
