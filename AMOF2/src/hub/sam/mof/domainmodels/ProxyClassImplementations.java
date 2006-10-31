package hub.sam.mof.domainmodels;

import cmof.Operation;
import cmof.Property;
import cmof.Tag;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.ObjectImpl;

import java.lang.reflect.Constructor;

public class ProxyClassImplementations extends ProxyObjectImplementations {

    public ProxyClassImplementations(ProxyModelContext context) {
        super(context);
    }

    @Override
    public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args,
                                          ClassifierSemantics<Property, Operation, String> semantics) {
        Class javaClass = ((ProxyClassInstance)((ObjectImpl)object).getClassInstance()).getTheClass();
        Object[] dismantledArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                dismantledArgs[i] = null;
            } else {
                dismantledArgs[i] = context.disMantleFromRefObject(args[i]);
            }
        }
        boolean isConstructor = false;
        for(Tag aTag: operation.getTag()) {
            if (aTag.getName().equals(JavaModelGenerationDoclet.CONSTRUCTOR_TAG)) {
                isConstructor = true;
            }
        }
        try {
            if (isConstructor) {
                return context.mantleToRefObject(getConstructor(javaClass, args).newInstance(dismantledArgs));
            } else {
                return context.mantleToRefObject(getMethod(javaClass, operation.getName(), args).
                        invoke(null, dismantledArgs));
            }
        } catch (Exception e) {
            throw new ProxyModelException("Exception during operation invokation", e);
        }
    }

    // TODO performance
    static Constructor getConstructor(Class aClass, Object[] args)
            throws NoSuchMethodException {
        MethodLoop:
        for(Constructor aConstructor : aClass.getConstructors()) {
            Class[] parameterTypes = aConstructor.getParameterTypes();
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
                return aConstructor;
            }
        }
        throw new NoSuchMethodException(aClass.getCanonicalName() + "." + aClass.getSimpleName() + "(...)");
    }
}
