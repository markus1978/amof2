package cmof;


public class ElementImportImpl extends hub.sam.mof.reflection.ObjectImpl implements ElementImport
{
    public ElementImportImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ElementImportImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        java.lang.Object value = get("visibility");
        if (value == null) {
           return null;
        } else {
            return (core.abstractions.visibilities.VisibilityKind)value;
        }
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        set("visibility", value);
    }

    public java.lang.String getAlias() {
        java.lang.Object value = get("alias");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setAlias(java.lang.String value) {
        set("alias", value);
    }

    public cmof.PackageableElement getImportedElement() {
        java.lang.Object value = get("importedElement");
        if (value == null) {
           return null;
        } else {
            return (cmof.PackageableElement)value;
        }
    }

    public void setImportedElement(cmof.PackageableElement value) {
        set("importedElement", value);
    }

    public cmof.Namespace getImportingNamespace() {
        java.lang.Object value = get("importingNamespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public void setImportingNamespace(cmof.Namespace value) {
        set("importingNamespace", value);
    }

    public java.lang.String getName()  {
        java.lang.Object value = invokeOperation("getName", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getSource() {
        java.lang.Object value = get("source");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getTarget() {
        java.lang.Object value = get("target");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getRelatedElement() {
        java.lang.Object value = get("relatedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Tag> getTag() {
        java.lang.Object value = get("tag");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public java.lang.String getUri() {
        java.lang.Object value = get("uri");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setUri(java.lang.String value) {
        set("uri", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        java.lang.Object value = get("ownedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Element getOwner() {
        java.lang.Object value = get("owner");
        if (value == null) {
           return null;
        } else {
            return (cmof.Element)value;
        }
    }

    public void setOwner(cmof.Element value) {
        set("owner", value);
    }

    public void setOwner(core.abstractions.ownerships.Element value) {
        set("owner", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        java.lang.Object value = invokeOperation("allOwnedElements", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean mustBeOwned()  {
        java.lang.Object value = invokeOperation("mustBeOwned", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

}

