package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;

public class OclCollection<OclTypeP,JavaTypeP> extends OclAny<ReflectiveCollection<OclTypeP>,ReflectiveCollection<JavaTypeP>> {

    public OclCollection(int symbolCode, String name, OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    protected OclCollection(Object value) {
        super(value);
    }

    private static final int SIZE = 1 + (100 * 1);

    public OclInteger size() {
        return null;
    }

    protected OclInteger sizeEval() {
        return null;
    }

    private static final int INCLUDES = 2 + (100 * 1);

    public OclBoolean includes(OclTypeP object) {
        return null;
    }

    protected OclBoolean includesEval(OclTypeP object) {
        return null;
    }

    private static final int EXCLUDES = 3 + (100 * 1);

    public OclBoolean excludes(OclTypeP object) {
        return null;
    }

    protected OclBoolean excludesEval(OclTypeP object) {
        return null;
    }

    private static final int COUNT = 4 + (100 * 1);

    public OclInteger count(OclTypeP object) {
        return null;
    }

    protected OclInteger countEval(OclTypeP object) {
        return null;
    }

    private static final int INCLUDES_ALL = 5 + (100 * 1);

    public OclBoolean includesAll(OclCollection<OclTypeP,JavaTypeP> c2) {
        return null;
    }

    protected OclBoolean includesAllEval(OclCollection<OclTypeP,JavaTypeP> c2) {
        return null;
    }

    private static final int EXCLUDES_ALL = 6 + (100 * 1);

    public OclBoolean excludesAll(OclCollection<OclTypeP,JavaTypeP> c2) {
        return null;
    }

    protected OclBoolean excludesAllEval(OclCollection<OclTypeP,JavaTypeP> c2) {
        return null;
    }

    private static final int IS_EMPTY = 7 + (100 * 1);

    public OclBoolean isEmpty() {
        return null;
    }

    protected OclBoolean isEmptyEval() {
        return null;
    }

    private static final int NOT_EMPTY = 8 + (100 * 1);

    public OclBoolean notEmpty() {
        return null;
    }

    protected OclBoolean notEmptyEval() {
        return null;
    }

    private static final int EXISTS = 9 + (100 * 1);

    public OclBoolean exists(OclTypeP iterator, OclBoolean body) {
        return new OclBoolean(EXISTS, null, this, new OclAny[] {(OclAny)iterator, body});
    }

    protected OclBoolean existsEval(OclTypeP iterator, OclBoolean body) {
        for(JavaTypeP element: javaValue()) {
            ((OclAny)iterator).setJavaValue(element);
            if (body.javaValue()) {
                return new OclBoolean(true);
            }
        }
        return new OclBoolean(false);
    }

    private static final int FOR_ALL = 10 + (100 * 1);

    public OclBoolean forAll(OclTypeP iterator, OclBoolean body) {
        return new OclBoolean(FOR_ALL, null, this, new OclAny[] {(OclAny)iterator, body});
    }

    protected OclBoolean forAllEval(OclTypeP iterator, OclBoolean body) {
        for(JavaTypeP element: javaValue()) {
            ((OclAny)iterator).setJavaValue(element);
            if (!body.javaValue()) {
                return new OclBoolean(false);
            }
        }
        return new OclBoolean(true);
    }

    private static final int IS_UNIQUE = 11 + (100 * 1);

    public OclBoolean isUnique(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclBoolean isUniqueEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int ANY = 12 + (100 * 1);

    public OclTypeP any(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclTypeP anyEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int ONE = 13 + (100 * 1);

    public OclBoolean one(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    protected OclBoolean oneEval(OclTypeP iterator, OclBoolean body) {
        return null;
    }

    private static final int COLLECT = 14 + (100 * 1);

    public <OclTypePofExpression,JavaTypePofExpression> OclCollection<OclTypePofExpression,JavaTypePofExpression> collect(OclTypeP iterator, OclAny<OclTypePofExpression,JavaTypePofExpression> body) {
        return null;
    }

    protected <OclTypePofExpression,JavaTypePofExpression> OclCollection<OclTypePofExpression,JavaTypePofExpression> collectEval(OclTypeP iterator, OclAny<OclTypePofExpression,JavaTypePofExpression> body) {
        return null;
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case SIZE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDES:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDES:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case COUNT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case INCLUDES_ALL:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXCLUDES_ALL:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case IS_EMPTY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case NOT_EMPTY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case EXISTS:
                return existsEval((OclTypeP)children[0], (OclBoolean)children[1]);
            case FOR_ALL:
                return forAllEval((OclTypeP)children[0], (OclBoolean)children[1]);
            case IS_UNIQUE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case ANY:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case ONE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case COLLECT:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
