package hub.sam.mof.domainmodels;

import cmof.Operation;
import cmof.Property;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ObjectDlg;
import hub.sam.mof.reflection.ObjectImpl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class ProxyObjectImplementations implements Implementations  {

    protected final ProxyModelContext context;

    public ProxyObjectImplementations(ProxyModelContext context) {
        super();
        this.context = context;
    }

    public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
        return false;
    }

    public boolean hasImplementationFor(
            Operation operatoin, ClassifierSemantics<Property, Operation, String> semantics) {
        return true;
    }

    @SuppressWarnings({"unchecked"})
    public List<ObjectDlg> getDelegates() {
        return Collections.EMPTY_LIST;
    }

    public Object invokeImplementationFor(
            Property property, cmof.reflection.Object object,
            ClassifierSemantics<Property, Operation, String> semantics) {
        throw new RuntimeException("assert");
    }

    public Object invokeImplementationFor(
            Operation operation, cmof.reflection.Object object, Object[] args,
            ClassifierSemantics<Property, Operation, String> semantics) {
        Object javaObject = ((ProxyObjectInstance)((ObjectImpl)object).getClassInstance()).getTheObject();
        Object[] dismantledArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                dismantledArgs[i] = null;
            } else {
                dismantledArgs[i] = context.disMantleFromRefObject(args[i]);
            }
        }
        try {
            return context.mantleToRefObject(getMethod(javaObject.getClass(), operation.getName(),
                    args).invoke(javaObject, dismantledArgs));
        } catch (Exception e) {
            throw new ProxyModelException("Exception during operation invokation", e);
        }
    }

    // TODO performance
    static Method getMethod(Class aClass, String name, Object[] args)
            throws NoSuchMethodException {
        MethodLoop:
        for(Method aMethod: aClass.getMethods()) {
            if (aMethod.getName().equals(name)) {
                Class[] parameterTypes = aMethod.getParameterTypes();
                if (args.length == parameterTypes.length) {
                    for (int i = 0; i < args.length; i++) {
                        Class argType;
                        if ((args[i] instanceof cmof.reflection.Object) &&
                                (((ObjectImpl)args[i]).getClassInstance() instanceof ProxyObjectInstance)) {
                            argType = ((ProxyObjectInstance)((ObjectImpl)args[i]).getClassInstance()).getTheObject().getClass();
                        } else {
                            argType = args[i].getClass();
                        }
                        if (!parameterTypes[i].isAssignableFrom(argType)) {
                            continue MethodLoop;
                        }
                    }
                    return aMethod;
                }
            }
        }
        throw new NoSuchMethodException(aClass.getCanonicalName() + "." + name + "(...)");
    }
}
