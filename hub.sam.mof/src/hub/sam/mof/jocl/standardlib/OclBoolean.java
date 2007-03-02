package hub.sam.mof.jocl.standardlib;

public class OclBoolean extends OclAny<OclBoolean,Boolean> {

    public OclBoolean(Boolean value) {
        super(value);
    }

    public OclBoolean(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int OR = 1;

    public OclBoolean or(OclBoolean b) {
        return null;
    }

    protected OclBoolean orEval(OclBoolean b) {
        return null;
    }

    private static final int AND = 2;

    public OclBoolean and(OclBoolean b) {
        return null;
    }

    protected OclBoolean andEval(OclBoolean b) {
        return null;
    }

    private static final int XOR = 3;

    public OclBoolean xor(OclBoolean b) {
        return null;
    }

    protected OclBoolean xorEval(OclBoolean b) {
        return null;
    }

    private static final int IMPLIES = 4;

    public OclBoolean implies(OclBoolean b) {
        return null;
    }

    protected OclBoolean impliesEval(OclBoolean b) {
        return null;
    }

    private static final int NOT = 5;

    public OclBoolean not() {
        return null;
    }

    protected OclBoolean notEval() {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case OR:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AND:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case XOR:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case IMPLIES:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case NOT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }

}
