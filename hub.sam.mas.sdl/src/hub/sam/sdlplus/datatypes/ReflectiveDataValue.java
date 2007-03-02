package hub.sam.sdlplus.datatypes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectiveDataValue implements DataValueRepresentation {
    private final java.lang.Class theClass;

    public ReflectiveDataValue(Class theClass) {
        super();
        this.theClass = theClass;
    }

    private static Method getMethod(Class aClass, String name, Class[] parameterTypes)
            throws NoSuchMethodException {
        loop:
        for(Method method: aClass.getMethods()) {
            if (name.equals(method.getName())) {
                Class[] methodsTypes = method.getParameterTypes();
                if (methodsTypes.length != parameterTypes.length) {
                    continue loop;
                }
                int i = 0;
                for (Class parameterType: method.getParameterTypes()) {
                    if (!parameterType.isAssignableFrom(parameterTypes[i++])) {
                         continue loop;
                    }
                }
                return method;
            }
        }
        throw new NoSuchMethodException(name);
    }

    public Object callOperand(String operand, Object[] operands) {
        java.lang.Class[] parameterTypes = new java.lang.Class[operands.length];
        int i = 0;
        for(Object value: operands) {
            if (value instanceof DataValueRepresentation) {
                parameterTypes[i++] = ((DataValueRepresentation)value).getJavaType();
            } else {
                parameterTypes[i++] = value.getClass();
            }
        }

        Method javaOperand = null;
        try {
            javaOperand = getMethod(theClass, operand, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        try {
            return javaOperand.invoke(this, operands);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Class getJavaType() {
        return theClass;
    }
}
