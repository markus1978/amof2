package qualifier;


public class SourceImpl extends hub.sam.mof.reflection.ObjectImpl implements Source
{
    public SourceImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public SourceImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public qualifier.Target getSuperAttr(qualifier.QualifierType qualifier) {
        java.lang.Object value = get("superAttr", qualifier);
        if (value == null) {
           return null;
        } else {
            return (qualifier.Target)value;
        }
    }

    public void setSuperAttr(qualifier.QualifierType qualifier, qualifier.Target value) {
        set("superAttr", qualifier, value);
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

