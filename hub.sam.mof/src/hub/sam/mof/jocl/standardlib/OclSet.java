package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclSet<OclTypeP,JavaTypeP> extends OclCollection<OclTypeP,JavaTypeP> {

    public OclSet(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    public OclSet(ReflectiveCollection object) {
        super(object);
    }

    private static final int UNION_SET = 1;

    public OclSet<OclTypeP,JavaTypeP> union(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> unionEval(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int UNION_BAG = 2;

    public OclBag<OclTypeP,JavaTypeP> union(OclBag<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> unionEval(OclBag<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int INTERSECTION_SET = 3;

    public OclSet<OclTypeP,JavaTypeP> intersection(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> intersectionEval(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int INTERSECTION_BAG = 4;

    public OclSet<OclTypeP,JavaTypeP> intersection(OclBag<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> intersectionEval(OclBag<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int SUB = 5;

    public OclSet<OclTypeP,JavaTypeP> sub(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> subEval(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int INCLUDING = 6;

    public OclSet<OclTypeP,JavaTypeP> including(OclTypeP object) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> includingEval(OclTypeP object) {
        return null;
    }

    private static final int EXCLUDING = 7;

    public OclSet<OclTypeP,JavaTypeP> excluding(OclTypeP object) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> excludingEval(OclTypeP object) {
        return null;
    }

    private static final int SYMMETRIC_DIFFERENCE = 8;

    public OclSet<OclTypeP,JavaTypeP> symmetricDifference(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> symmetricDifferenceEval(OclSet<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int FLATTEN = 9;

    public OclSet<OclTypeP,JavaTypeP> flatten() {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> flattenEval() {
        return null;
    }

    private static final int AS_SET = 10;

    public OclSet<OclTypeP,JavaTypeP> asSet() {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> asSetEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 11;

    public OclSequence<OclTypeP,JavaTypeP> asSequence() {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> asSequenceEval() {
        return null;
    }

    private static final int AS_BAG = 12;

    public OclBag<OclTypeP,JavaTypeP> asBag() {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> asBagEval() {
        return null;
    }

    private static final int SELECT = 13;

    public OclSet<OclTypeP,JavaTypeP> select(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> selectEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 14;

    public OclSet<OclTypeP,JavaTypeP> reject(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> rejectEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 15;

    public OclSequence<OclTypeP,JavaTypeP> sortedBy(OclTypeP iterator, OclInteger body) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> sortedByEval(OclTypeP iterator, OclInteger body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case UNION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INTERSECTION_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SUB:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SYMMETRIC_DIFFERENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FLATTEN:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SEQUENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_BAG:
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

