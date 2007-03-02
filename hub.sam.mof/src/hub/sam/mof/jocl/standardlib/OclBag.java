package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclBag<OclTypeP,JavaTypeP> extends OclCollection<OclTypeP,JavaTypeP> {

    protected OclBag(ReflectiveCollection value) {
        super(value);
    }

    public OclBag(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int OCL_EQUALS = 1;

    public OclBoolean oclEquals(OclBag<OclTypeP,JavaTypeP> bag) {
        return null;
    }

    protected OclBoolean oclEqualsEval(OclBag<OclTypeP,JavaTypeP> bag) {
        return null;
    }

    private static final int UNION_SET = 2;

    public OclBag<OclTypeP,JavaTypeP> union(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> unionEval(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int UNION_BAG = 3;

    public OclBag<OclTypeP,JavaTypeP> union(OclBag<OclTypeP,JavaTypeP> b) {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> unionEval(OclBag<OclTypeP,JavaTypeP> b) {
        return null;
    }

    private static final int INTERSECTION_BAG = 4;

    public OclBag<OclTypeP,JavaTypeP> intersection(OclBag<OclTypeP,JavaTypeP> b) {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> intersectionEval(OclBag<OclTypeP,JavaTypeP> b) {
        return null;
    }

    private static final int INTERSECTION_SET = 5;

    public OclSet<OclTypeP,JavaTypeP> intersection(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> intersectionEval(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int INCLUDING = 6;

    public OclBag<OclTypeP,JavaTypeP> including(OclTypeP object) {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> includingEval(OclTypeP object) {
        return null;
    }

    private static final int COUNT = 7;

    @Override
    public OclInteger count(OclTypeP object) {
        return null;
    }

    @Override
    protected OclInteger countEval(OclTypeP object) {
        return null;
    }

    private static final int FLATTEN = 8;

    public OclBag flatten() {
        return null;
    }

    protected OclBag flattenEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 9;

    public OclSequence<OclTypeP,JavaTypeP> asSequence() {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> asSequenceEval() {
        return null;
    }

    private static final int AS_SET = 10;

    public OclSet<OclTypeP,JavaTypeP> asSet() {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> asSetEval() {
        return null;
    }

    private static final int SELECT = 11;

    public OclBag<OclTypeP,JavaTypeP> select(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> selectEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 13;

    public OclBag<OclTypeP,JavaTypeP> reject(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> rejectEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 14;

    public OclSequence<OclTypeP,JavaTypeP> sortedBy(OclTypeP iterator, OclInteger body) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> sortedByEval(OclTypeP iterator, OclInteger body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case OCL_EQUALS:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case COUNT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FLATTEN:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SEQUENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SELECT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case REJECT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SORTED_BY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
