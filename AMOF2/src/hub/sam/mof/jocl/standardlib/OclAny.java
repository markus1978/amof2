package hub.sam.mof.jocl.standardlib;

public class OclAny<OclTypeP,JavaTypeP> {

    protected final int symbolCode;
    protected final String name;
    protected final OclAny self;
    protected final OclAny[] children;

    protected Object value;

    protected OclAny(Object value) {
        super();
        this.value = value;
        symbolCode = 0;
        self = null;
        children = null;
        name = null;
    }

    public OclAny(int symbolCode, String name, OclAny self, OclAny[] children) {
        super();
        this.symbolCode = symbolCode;
        this.self = self;
        this.children = children;
        this.name = name;
    }

    private static final int OCL_EQUALS = 1 + (100 * 2);

    public OclBoolean oclEquals(OclAny object2) {
        return new OclBoolean(OCL_EQUALS, null, this, new OclAny[] {object2});
    }

    protected OclBoolean oclEqualsEval(OclAny object2) {
        return new OclBoolean(this.javaValue().equals(object2.javaValue()));
    }

    private static final int NOT_EQUALS = 2 + (100 * 2);

    public OclBoolean notEquals(OclAny object2) {
        return new OclBoolean(NOT_EQUALS, null, self, new OclAny[] {object2});
    }

    protected OclBoolean notEqualsEval(OclAny object2) {
        return new OclBoolean(!this.value.equals(object2.value));
    }

    private static final int OCL_IS_UNDEFINED = 3 + (100 * 2);

    public OclBoolean oclIsUndefined() {
        return new OclBoolean(OCL_IS_UNDEFINED, null, self, new OclAny[] {});
    }

    protected OclBoolean oclIsUndefinedEval() {
        return null;
    }

    private static final int OCL_AS_TYPE = 4 + (100 * 2);

    public <OclTypePofType,JavaTypePofType> OclAny<OclTypePofType,JavaTypePofType>
            oclAsType(OclType<OclTypePofType,JavaTypePofType> type) {
        return new OclAny<OclTypePofType,JavaTypePofType>(OCL_AS_TYPE, null, self, new OclAny[] {type});
    }

    protected <OclTypePofType,JavaTypeofType> OclAny<OclTypePofType,JavaTypeofType> oclAsTypeEval(OclType<OclTypePofType,JavaTypeofType> type) {
        return null;
    }

    private static final int OCL_IS_TYPE_OF = 5 + (100 * 2);

    public OclBoolean oclIsTypeOf(OclType type) {
        return new OclBoolean(OCL_IS_TYPE_OF, null, self, new OclAny[] {type});
    }

    protected OclBoolean oclIsTypeOfEval(OclType type) {
        return null;
    }

    private static final int OCL_IS_KIND_OF = 6 + (100 * 2);

    public OclBoolean oclIsKindOf(OclType type) {
        return new OclBoolean(OCL_IS_KIND_OF, null, self, new OclAny[] {type});
    }

    protected OclBoolean oclIsKindOfEval(OclType type) {
        return null;
    }

    public JavaTypeP javaValue() {
        if (value != null) {
            return (JavaTypeP)value;
        } else {
            return (JavaTypeP)self.eval(symbolCode, name, children).javaValue();
        }
    }

    protected void setJavaValue(JavaTypeP value) {
        this.value = value;
    }

    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case OCL_EQUALS:
                return oclEqualsEval(children[0]);
            case NOT_EQUALS:
                return notEqualsEval(children[0]);
            case OCL_IS_UNDEFINED:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_AS_TYPE:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_IS_TYPE_OF:
                throw new RuntimeException("This part of the OCL library is not implemented");
            case OCL_IS_KIND_OF:
                throw new RuntimeException("This part of the OCL library is not implemented");
            default:
                throw new RuntimeException("This part of the OCL library is not implemented");
        }
    }
}
