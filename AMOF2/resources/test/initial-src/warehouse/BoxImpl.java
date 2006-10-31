package warehouse;


public class BoxImpl extends hub.sam.mof.reflection.ObjectImpl implements Box
{
    public BoxImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public BoxImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<? extends warehouse.Item> getItem() {
        java.lang.Object value = get("item");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value, this, "item");
        }
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

    public cmof.common.ReflectiveCollection<? extends warehouse.Element> getContent() {
        java.lang.Object value = get("content");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value, this, "content");
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

