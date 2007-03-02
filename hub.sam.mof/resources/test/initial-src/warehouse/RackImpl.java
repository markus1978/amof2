package warehouse;


public class RackImpl extends hub.sam.mof.reflection.ObjectImpl implements Rack
{
    public RackImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public RackImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }



    public cmof.common.ReflectiveCollection<? extends warehouse.Box> getBox() {
        java.lang.Object value = get("box");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value, this, "box");
        }
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

    public java.lang.String getFoo() {
        java.lang.Object value = get("foo");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setFoo(java.lang.String value) {
        set("foo", value);
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

    public void fooOperation()  {
        invokeOperation("foo", new java.lang.Object[] {  });
    }

}

