package cmof;


public class CommentImpl extends hub.sam.mof.reflection.ObjectImpl implements Comment
{
    public CommentImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public CommentImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getAnnotatedElement() {
        java.lang.Object value = get("annotatedElement");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
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

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        java.lang.Object value = get("ownedComment");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
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

}

