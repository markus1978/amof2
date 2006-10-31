package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveSequence;

public class OclSequence<OclTypeP,JavaTypeP> extends OclCollection<OclTypeP,JavaTypeP> {

    protected OclSequence(ReflectiveSequence value) {
        super(value);
    }

    public OclSequence(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int COUNT = 1;

    @Override
    public OclInteger count(OclTypeP object) {
        return null;
    }

    @Override
    protected OclInteger countEval(OclTypeP object) {
        return null;
    }

    private static final int OCL_EQUALS = 2;

    public OclBoolean oclEquals(OclSequence<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclBoolean oclEqualsEval(OclSequence<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int UNION = 3;

    public OclSequence<OclTypeP,JavaTypeP> union(OclSequence<OclTypeP,JavaTypeP> s) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> unionEval(OclSequence<OclTypeP,JavaTypeP> s) {
        return null;
    }

    private static final int FLATTEN = 4;

    public OclSequence flatten() {
        return null;
    }

    protected OclSequence flattenEval() {
        return null;
    }

    private static final int APPEND = 5;

    public OclSequence<OclTypeP,JavaTypeP> append(OclTypeP o) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> appendEval(OclTypeP o) {
        return null;
    }

    private static final int PREPEND = 6;

    public OclSequence<OclTypeP,JavaTypeP> prepend(OclTypeP o) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> prependEval(OclTypeP o) {
        return null;
    }

    private static final int INSERT_AT = 7;

    public OclSequence<OclTypeP,JavaTypeP> insertAt(OclInteger i, OclTypeP o) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> insertAtEval(OclInteger i, OclTypeP o) {
        return null;
    }

    private static final int SUBSEQUENCE = 8;

    public OclSequence<OclTypeP,JavaTypeP> subsequence(OclInteger lower, OclInteger upper) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> subsequenceEval(OclInteger lower, OclInteger upper) {
        return null;
    }

    private static final int AT = 9;

    public OclTypeP at(OclInteger i) {
        return null;
    }

    protected OclTypeP atEval(OclInteger i) {
        return null;
    }

    private static final int INDEX_OF = 10;

    public OclInteger indexOf(OclTypeP o) {
        return null;
    }

    protected OclInteger indexOfEval(OclTypeP o) {
        return null;
    }

    private static final int FIRST = 11;

    public OclTypeP first() {
        return null;
    }

    protected OclTypeP firstEval() {
        return null;
    }

    private static final int LAST = 12;

    public OclTypeP last() {
        return null;
    }

    protected OclTypeP lastEval() {
        return null;
    }

    private static final int INCLUDING = 13;

    public OclSequence<OclTypeP,JavaTypeP> including(OclTypeP o) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> includingEval(OclTypeP o) {
        return null;
    }

    private static final int EXCLUDING = 14;

    public OclSequence<OclTypeP,JavaTypeP> excluding(OclTypeP o) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> excludingEval(OclTypeP o) {
        return null;
    }

    private static final int AS_BAG = 15;

    public OclBag<OclTypeP,JavaTypeP> asBag() {
        return null;
    }

    protected OclBag<OclTypeP,JavaTypeP> asBagEval() {
        return null;
    }

    private static final int AS_SET = 16;

    public OclSet<OclTypeP,JavaTypeP> asSet() {
        return null;
    }

    protected OclSet<OclTypeP,JavaTypeP> asSetEval() {
        return null;
    }

    private static final int AS_SEQUENCE = 17;

    public OclSequence<OclTypeP,JavaTypeP> asSequence() {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> asSequenceEval() {
        return null;
    }

    private static final int SELECT = 18;

    public OclSequence<OclTypeP,JavaTypeP> select(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> selectEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int REJECT = 19;

    public OclSequence<OclTypeP,JavaTypeP> reject(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> rejectEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int SORTED_BY = 20;

    public OclSequence<OclTypeP,JavaTypeP> sortedBy(OclTypeP iterator, OclInteger body) {
        return null;
    }

    protected OclSequence<OclTypeP,JavaTypeP> sortedByEval(OclTypeP iterator, OclInteger body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case COUNT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_EQUALS:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case UNION:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FLATTEN:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case APPEND:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case PREPEND:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INSERT_AT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case SUBSEQUENCE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INDEX_OF:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case FIRST:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case LAST:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDING:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_BAG:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SET:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case AS_SEQUENCE:
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
