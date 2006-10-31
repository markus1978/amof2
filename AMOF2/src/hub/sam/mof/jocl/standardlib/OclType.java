package hub.sam.mof.jocl.standardlib;

public class OclType<OclTypeP,JavaTypeP> extends OclAny<OclTypeP,JavaTypeP> {

    public OclType(Object value) {
        super(value);
    }

    public OclType(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int allInstance = 1;

    public OclSet<OclTypeP,JavaTypeP> allInstance() {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> allInstanceEval() {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case allInstance:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
