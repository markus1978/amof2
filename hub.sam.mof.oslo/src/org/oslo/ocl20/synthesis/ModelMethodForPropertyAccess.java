package org.oslo.ocl20.synthesis;

import java.util.ArrayList;
import java.util.List;

public class ModelMethodForPropertyAccess {
    private String methodName;
    private List<Class> parameterTypes = new ArrayList<Class>();
    private List<Object> parameterValues = new ArrayList<Object>();
    
    public ModelMethodForPropertyAccess(String methodName) {
        this.methodName = methodName;
    }
    
    public String getMethodName() {
        return methodName;
    }
    
    public void addParameter(Class type, Object value) {
        this.parameterTypes.add(type);
        this.parameterValues.add(value);
    }

    public Class[] getParameterTypes() {
        return parameterTypes.toArray(new Class[] {});
    }
    
    public List<Class> getParameterTypesAsList() {
        return parameterTypes;
    }
    
    public Object[] getParameterValues() {
        return parameterValues.toArray(new Object[] {});
    }
    
    public List<Object> getParameterValuesAsList() {
        return parameterValues;
    }

}
