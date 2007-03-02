package qualifier;


public class ASourceImpl extends hub.sam.mof.reflection.ObjectImpl implements ASource
{
    public ASourceImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ASourceImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public qualifier.BTarget getSuperAttrB(qualifier.BQualifierType qualifier) {
        java.lang.Object value = get("superAttrB", qualifier);
        if (value == null) {
           return null;
        } else {
            return (qualifier.BTarget)value;
        }
    }

    public void setSuperAttrB(qualifier.BQualifierType qualifier, qualifier.BTarget value) {
        set("superAttrB", qualifier, value);
    }

    public qualifier.ATarget getSuperAttrA(qualifier.AQualifierType qualifier) {
        java.lang.Object value = get("superAttrA", qualifier);
        if (value == null) {
           return null;
        } else {
            return (qualifier.ATarget)value;
        }
    }

    public void setSuperAttrA(qualifier.AQualifierType qualifier, qualifier.ATarget value) {
        set("superAttrA", qualifier, value);
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

