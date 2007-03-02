package qualifier;


public class BTargetImpl extends hub.sam.mof.reflection.ObjectImpl implements BTarget
{
    public BTargetImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public BTargetImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public qualifier.ASource getAsource() {
        java.lang.Object value = get("asource");
        if (value == null) {
           return null;
        } else {
            return (qualifier.ASource)value;
        }
    }

    public void setAsource(qualifier.ASource value) {
        set("asource", value);
    }

    public qualifier.Source getSource() {
        java.lang.Object value = get("source");
        if (value == null) {
           return null;
        } else {
            return (qualifier.Source)value;
        }
    }

    public void setSource(qualifier.Source value) {
        set("source", value);
    }

    public java.lang.String getName() {
        java.lang.Object value = get("name");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setName(java.lang.String value) {
        set("name", value);
    }

}

