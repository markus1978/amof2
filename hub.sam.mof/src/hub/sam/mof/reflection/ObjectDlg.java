package hub.sam.mof.reflection;

import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Argument;
import cmof.reflection.Extent;
import cmof.reflection.Object;
import cmof.reflection.ObjectChangeListener;
import hub.sam.mof.jocl.standardlib.OclModelElement;

import java.beans.PropertyChangeListener;

public class ObjectDlg implements Object {

    private Object reflectionself = null;
    private ImplementationsImpl implementations = null;

    protected ObjectDlg getSuper(java.lang.Class javaClass) {
        ObjectDlg result = implementations.getDelegate(javaClass.getCanonicalName() + "Custom");
        if (result == null) {
            throw new IllegalArgumentException("no super delegator found");
        }
        result.setSelf(reflectionself);
        return result;
    }

    protected void setImplementations(ImplementationsImpl implementations) {
        this.implementations = implementations;
    }

    protected void setSelf(Object self) {
        reflectionself = self;
    }

    protected Object getSelf() {
        return reflectionself;
    }

    public UmlClass getMetaClass() {
        return reflectionself.getMetaClass();
    }

    public Object container() {
        return reflectionself.container();
    }

    public java.lang.Object get(Property property) {
        return reflectionself.get(property);
    }

    public java.lang.Object get(Property property, java.lang.Object qualifier) {
        return reflectionself.get(property, qualifier);
    }

    public void set(Property property, java.lang.Object value) {
        reflectionself.set(property, value);
    }

    public void set(Property property, java.lang.Object qualifier, java.lang.Object value)  {
        reflectionself.set(property, qualifier, value);
    }

    public boolean isSet(Property property) {
        return reflectionself.isSet(property);
    }

    public boolean isSet(Property property, java.lang.Object qualifier) {
        return reflectionself.isSet(property, qualifier);
    }

    public void unset(Property property) {
        reflectionself.unset(property);
    }

    public void unset(Property property, java.lang.Object qualifier) {
        reflectionself.unset(property, qualifier);
    }

    public void delete() {
        reflectionself.delete();
    }

    public java.lang.Object invokeOperation(Operation op, ReflectiveSequence<Argument> arguments) {
        return reflectionself.invokeOperation(op, arguments);
    }

    public boolean isInstanceOfType(UmlClass type, boolean includeSubTypes) {
        return reflectionself.isInstanceOfType(type, includeSubTypes);
    }

    public Object getOutermostContainer() {
        return reflectionself.getOutermostContainer();
    }

    public ReflectiveCollection<? extends Object> getComponents() {
        return reflectionself.getComponents();
    }

    public Extent getExtent() {
        return reflectionself.getExtent();
    }

    public java.lang.Object get(String property) {
        return reflectionself.get(property);
    }

    public java.lang.Object get(String property, java.lang.Object qualifier) {
        return reflectionself.get(property, qualifier);
    }

    public void set(String property, java.lang.Object value) {
        reflectionself.set(property, value);
    }

    public void set(String property, java.lang.Object qualifier, java.lang.Object value) {
        reflectionself.set(property, qualifier, value);
    }

    public java.lang.Object invokeOperation(String opName, java.lang.Object[] args) {
        return reflectionself.invokeOperation(opName, args);
    }

    public void addObjectEventHandler(ObjectChangeListener handler) {
        reflectionself.addObjectEventHandler(handler);
    }
    
    public void removeObjectEventHandler(ObjectChangeListener handler) {
        reflectionself.removeObjectEventHandler(handler);
    }

    @Override
    public String toString() {
        return reflectionself.toString();
    }

    @Override
    public boolean equals(java.lang.Object other) {
        if (other instanceof ObjectDlg) {
            return reflectionself.equals(((ObjectDlg)other).reflectionself);
        } else if (other instanceof ObjectImpl) {
            return reflectionself.equals(other);
        } else {
            return false;
        }
    }

    public void addListener(PropertyChangeListener listener) {
        reflectionself.addListener(listener);
    }

    public void addListener(String propertyName, PropertyChangeListener listener) {
        reflectionself.addListener(propertyName, listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        reflectionself.removeListener(listener);
    }

    public void removeListener(String propertyName, PropertyChangeListener listener) {
        reflectionself.removeListener(propertyName, listener);
    }

    public OclModelElement ocl() {
        return reflectionself.ocl();
    }
}
