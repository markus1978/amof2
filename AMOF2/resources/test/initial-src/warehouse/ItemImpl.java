package warehouse;


public class ItemImpl extends hub.sam.mof.reflection.ObjectImpl implements Item
{
    public ItemImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ItemImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public int getWeight() {
        java.lang.Object value = get("weight");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Integer)value;
        }
    }

    public void setWeight(int value) {
        set("weight", value);
    }

    public warehouse.VisibilityKind getVisibility() {
        java.lang.Object value = get("visibility");
        if (value == null) {
           return null;
        } else {
            return (warehouse.VisibilityKind)value;
        }
    }

    public void setVisibility(warehouse.VisibilityKind value) {
        set("visibility", value);
    }

    public hub.sam.mof.test.TestClass getJavaTypeTest() {
        java.lang.Object value = get("javaTypeTest");
        if (value == null) {
           return null;
        } else {
            return (hub.sam.mof.test.TestClass)value;
        }
    }

    public void setJavaTypeTest(hub.sam.mof.test.TestClass value) {
        set("javaTypeTest", value);
    }

    public cmof.common.ReflectiveCollection<hub.sam.mof.test.TestClass> getJavaTypeTestList() {
        java.lang.Object value = get("javaTypeTestList");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value, this, "javaTypeTestList");
        }
    }

    public java.lang.String getIdentifier() {
        java.lang.Object value = get("identifier");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setIdentifier(java.lang.String value) {
        set("identifier", value);
    }

    public java.lang.String getPosition() {
        java.lang.Object value = get("position");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setPosition(java.lang.String value) {
        set("position", value);
    }

    public warehouse.Container getContainer() {
        java.lang.Object value = get("container");
        if (value == null) {
           return null;
        } else {
            return (warehouse.Container)value;
        }
    }

    public void setContainer(warehouse.Container value) {
        set("container", value);
    }

    public void test()  {
        invokeOperation("test", new java.lang.Object[] {  });
    }

}

