package hub.sam.sdlplus.semantics;

import hub.sam.sdlplus.*;

/**
 * This class combines a error message with the element that causes the error. It can be throws, since it is an
 * Exception. Is used by spari to represent errors in the input model.
 */
public class SemanticError extends Exception implements Comparable {

    private String msg;
    private cmof.reflection.Object context;

    public String getMessage() {
        return "SemanticError for entity " + SdlCompiler.getCompiler().getContextInformation(context) + ", reason: " + msg;
    }

    public SemanticError(cmof.reflection.Object context, String msg) {
        this.context = context;
        this.msg = msg;
    }

    public int compareTo(Object compareTo) {
        if (compareTo instanceof SemanticError) {
            SemanticError compareToError = (SemanticError)compareTo;
            Object line = context.get("line");
            if (line instanceof Comparable) {
                return ((Comparable)line).compareTo(compareToError.context.get("line"));
            }
        }
        return 0;
    }


}
