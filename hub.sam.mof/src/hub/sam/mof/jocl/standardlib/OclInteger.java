package hub.sam.mof.jocl.standardlib;

public class OclInteger extends OclAny<OclInteger,Integer> {

    public OclInteger(int value) {
        super(value);
    }

    public OclInteger(Integer value) {
        super(value);
    }

    public OclInteger(Long value) {
        super(value.intValue());
    }

    public OclInteger(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int NEG = 1;

    public OclInteger neg() {
        return null;
    }

    protected OclInteger negEval() {
        return null;
    }

    private static final int ABS = 2;

    public OclInteger abs() {
        return null;
    }

    protected OclInteger absEval() {
        return null;
    }

    private static final int SUB = 3;

    public OclInteger sub(OclInteger i) {
        return null;
    }

    protected OclInteger subEval(OclInteger i) {
        return null;
    }

    private static final int ADD = 4;

    public OclInteger add(OclInteger i) {
        return null;
    }

    protected OclInteger addEval(OclInteger i) {
        return null;
    }

    private static final int MUL = 5;

    public OclInteger mul(OclInteger i) {
        return null;
    }

    protected OclInteger mulEval(OclInteger i) {
        return null;
    }

    private static final int DIV = 6;

    public OclInteger div(OclInteger i) {
        return null;
    }

    protected OclInteger divEval(OclInteger i) {
        return null;
    }

    private static final int MOD = 7;

    public OclInteger mod(OclInteger i) {
        return null;
    }

    protected OclInteger modEval(OclInteger i) {
        return null;
    }

    private static final int MAX = 8;

    public OclInteger max(OclInteger i) {
        return null;
    }

    protected OclInteger maxEval(OclInteger i) {
        return null;
    }

    private static final int MIN = 9;

    public OclInteger min(OclInteger i) {
        return null;
    }

    protected OclInteger minEval(OclInteger i) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case NEG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case ABS:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SUB:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case ADD:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case MUL:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case DIV:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case MOD:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case MAX:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case MIN:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }

}
