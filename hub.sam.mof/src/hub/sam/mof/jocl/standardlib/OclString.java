package hub.sam.mof.jocl.standardlib;

public class OclString extends OclAny<OclString,String> {

    protected OclString(String value) {
        super(value);
    }

    public OclString(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int SIZE = 1;

    public OclInteger size() {
        return null;
    }

    protected OclInteger sizeEval() {
        return null;
    }

    private static final int CONCAT = 2;

    public OclString concat(OclString s) {
        return null;
    }

    protected OclString concatEval(OclString s) {
        return null;
    }

    private static final int SUBSTRING = 3;

    public OclString substring(OclInteger lower, OclInteger upper) {
        return null;
    }

    protected OclString substringEval(OclInteger lower, OclInteger upper) {
        return null;
    }

    private static final int TO_INTEGER = 4;

    public OclInteger toInteger() {
        return null;
    }

    protected OclInteger toIntegerEval() {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case SIZE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case CONCAT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SUBSTRING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case TO_INTEGER:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
