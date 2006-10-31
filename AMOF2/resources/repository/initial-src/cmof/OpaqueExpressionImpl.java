package cmof;


public class OpaqueExpressionImpl extends hub.sam.mof.reflection.ObjectImpl implements OpaqueExpression
{
    public OpaqueExpressionImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public OpaqueExpressionImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public boolean isComputable()  {
        java.lang.Object value = invokeOperation("isComputable", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public int integerValue()  {
        java.lang.Object value = invokeOperation("integerValue", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Integer)value;
        }
    }

    public boolean booleanValue()  {
        java.lang.Object value = invokeOperation("booleanValue", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public java.lang.String stringValue()  {
        java.lang.Object value = invokeOperation("stringValue", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public long unlimitedValue()  {
        java.lang.Object value = invokeOperation("unlimitedValue", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Long)value;
        }
    }

    public boolean isNull()  {
        java.lang.Object value = invokeOperation("isNull", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
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

    public cmof.Type getType() {
        java.lang.Object value = get("type");
        if (value == null) {
           return null;
        } else {
            return (cmof.Type)value;
        }
    }

    public void setType(cmof.Type value) {
        set("type", value);
    }

    public void setType(core.abstractions.typedelements.Type value) {
        set("type", value);
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

    public java.lang.String getQualifiedName() {
        java.lang.Object value = get("qualifiedName");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setQualifiedName(java.lang.String value) {
        set("qualifiedName", value);
    }

    public cmof.Namespace getNamespace() {
        java.lang.Object value = get("namespace");
        if (value == null) {
           return null;
        } else {
            return (cmof.Namespace)value;
        }
    }

    public void setNamespace(cmof.Namespace value) {
        set("namespace", value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        set("namespace", value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces()  {
        java.lang.Object value = invokeOperation("allNamespaces", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl((cmof.common.ReflectiveSequence)value);
        }
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        java.lang.Object value = invokeOperation("isDistinguishableFrom_core.abstractions.namespaces.NamedElement_core.abstractions.namespaces.Namespace", new java.lang.Object[] { n, ns });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public java.lang.String separator()  {
        java.lang.Object value = invokeOperation("separator", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public java.lang.String qualifiedNameOperation()  {
        java.lang.Object value = invokeOperation("qualifiedName", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
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

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
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

    public java.lang.String getBody() {
        java.lang.Object value = get("body");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setBody(java.lang.String value) {
        set("body", value);
    }

    public java.lang.String getLanguage() {
        java.lang.Object value = get("language");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setLanguage(java.lang.String value) {
        set("language", value);
    }

}

