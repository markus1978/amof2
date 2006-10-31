package hub.sam.mof.jocl.standardlib;

import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;

public class OclModelElement<T> extends OclAny<OclModelElement<T>,T> {

    protected OclModelElement(Object value) {
        super(value);
    }

    public OclModelElement(int symbolCode, String name,
                              OclAny self, OclAny[] children) {
        super(symbolCode, name, self, children);
    }

    private static final int GET = 1;

    protected OclAny get(String property, Class type) {
        try {
            return (OclAny)type.getConstructor(new Class[] {int.class, String.class, OclAny.class, OclAny[].class}).
                    newInstance(new Object[] {GET, property, this, new OclAny[] {}});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected OclAny getEval(String name) {
        return getReturnForResult(((cmof.reflection.Object)this.javaValue()).get(name));
    }

    public static final int QUALIFIED_GET = 2;

    protected OclAny get(String property, OclAny qualifier, Class type) {
        return new OclModelElement(QUALIFIED_GET, property, this, new OclAny[] {qualifier});
    }

    protected OclAny qualifiedGetEval() {
        return getReturnForResult(((cmof.reflection.Object)self.javaValue()).get(
                name, children[1].javaValue()));
    }

    private static final int INVOKE = 3;

    protected OclAny invoke(String operation, OclAny[] args, Class type) {
        return new OclModelElement(INVOKE, operation, this, args);
    }

    protected OclAny invokeEval() {
        Object[] args = new Object[children.length];
        for (int i = 0; i < children.length; i++) {
            args[i] = children[i].javaValue();
        }
        return getReturnForResult(((cmof.reflection.Object)self.javaValue()).invokeOperation(name, args));
    }

    private OclAny getReturnForResult(java.lang.Object result) {
        if (result instanceof cmof.reflection.Object) {
            return new OclModelElement(result);
        } else if (result instanceof ReflectiveSequence) {
            return new OclSequence((ReflectiveSequence)result);
        } else if (result instanceof ReflectiveCollection) {
            return new OclSet((ReflectiveCollection)result);
        } else if (result instanceof String) {
            return new OclString((String)result);
        } else if (result instanceof Boolean) {
            return new OclBoolean((Boolean)result);
        } else if (result instanceof Integer) {
            return new OclInteger((Integer)result);
        } else if (result instanceof Long) {
            return new OclInteger((Long)result);
        } else if (result.getClass().isEnum()) {
            throw new RuntimeException("not implemented");
        } else {
            throw new RuntimeException("unsupported type");
        }
    }

    @Override
    protected OclAny eval(int symbolCode, String name, OclAny[] children) {
        switch(symbolCode) {
            case GET:
                return getEval(name);
            case QUALIFIED_GET:
                return qualifiedGetEval();
            case INVOKE:
                return invokeEval();
            default:
                return super.eval(symbolCode, name, children);
        }
    }
}
