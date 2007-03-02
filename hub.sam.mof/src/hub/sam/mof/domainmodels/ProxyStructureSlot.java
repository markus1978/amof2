package hub.sam.mof.domainmodels;

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.mofinstancemodel.MofStructureSlot;
import hub.sam.mof.mofinstancemodel.MofValueSpecificationList;
import hub.sam.mof.instancemodel.ValueSpecification;

import java.lang.reflect.Method;

public class ProxyStructureSlot extends MofStructureSlot {
    private final ProxyObjectInstance instance;
    private Method getter = null;
    private Method setter = null;
    private final Property property;

    protected ProxyStructureSlot(ProxyObjectInstance owner, Property property) {
        super(owner, property);
        this.instance = owner;
        this.property = property;
    }

    private void initialize() {
        if (getter == null) {
            String propertyName = property.getName();
            String getterName1 = "get" + propertyName.substring(0,1).toUpperCase();
            String getterName2 = "is" + propertyName.substring(0,1).toUpperCase();
            String setterName1 = "set" + propertyName.substring(0,1).toUpperCase();
            String setterName2 = "setIs" + propertyName.substring(0,1).toUpperCase();
            if (propertyName.length() > 1) {
                getterName1 += propertyName.substring(1, propertyName.length());
                getterName2 += propertyName.substring(1, propertyName.length());
                setterName1 += propertyName.substring(1, propertyName.length());
                setterName2 += propertyName.substring(1, propertyName.length());
            }
            for(Method method: instance.getTheObject().getClass().getMethods()) {
                if (method.getName().equals(getterName1) || method.getName().equals(getterName2)) {
                    getter = method;
                }
            }
            if (getter == null) {
                throw new ProxyModelException("Getter for property " + property.getQualifiedName() + " not found.");
            }
            if (!property.isReadOnly()) {
                for(Method method: instance.getTheObject().getClass().getMethods()) {
                    if (method.getName().equals(setterName1) || method.getName().equals(setterName2)) {
                        setter = method;
                    }
                }
                if (setter == null) {
                    throw new ProxyModelException("Setter for property " + property.getQualifiedName() + " not found.");
                }
            }
        }
    }

    Object getProxyValues() {
        initialize();
        try {
            return getter.invoke(instance.getTheObject(), new Object[]{});
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    void setProxyValue(Object value) {
        initialize();
        try {
            setter.invoke(instance.getTheObject(), new Object[] {value});
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MofValueSpecificationList getValuesAsList(ValueSpecification<UmlClass,Property,Object> qualifier) {
        return new ProxyValueSpecificationList(instance, this, instance.getContext(), getProxyValues());
    }
}
