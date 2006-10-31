package cmof;


public class UmlClassImpl extends hub.sam.mof.reflection.ObjectImpl implements UmlClass
{
    public UmlClassImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public UmlClassImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
    }

    public boolean isAbstract() {
        java.lang.Object value = get("isAbstract");
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public void setIsAbstract(boolean value) {
        set("isAbstract", value);
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Operation> getOwnedOperation() {
        java.lang.Object value = get("ownedOperation");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl((cmof.common.ReflectiveSequence)value);
        }
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedAttribute() {
        java.lang.Object value = get("ownedAttribute");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl((cmof.common.ReflectiveSequence)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.UmlClass> getSuperClass() {
        java.lang.Object value = get("superClass");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getMetaInstances() {
        java.lang.Object value = get("metaInstances");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Classifier getMetaClassifier() {
        java.lang.Object value = get("metaClassifier");
        if (value == null) {
           return null;
        } else {
            return (cmof.Classifier)value;
        }
    }

    public void setMetaClassifier(cmof.Classifier value) {
        set("metaClassifier", value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getAttribute() {
        java.lang.Object value = get("attribute");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Feature> getFeature() {
        java.lang.Object value = get("feature");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getGeneral() {
        java.lang.Object value = get("general");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean conformsTo(cmof.Classifier other)  {
        java.lang.Object value = invokeOperation("conformsTo_cmof.Classifier", new java.lang.Object[] { other });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public boolean conformsTo(core.abstractions.typedelements.Type other)  {
        java.lang.Object value = invokeOperation("conformsTo_core.abstractions.typedelements.Type", new java.lang.Object[] { other });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        java.lang.Object value = get("importedMember");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        java.lang.Object value = get("elementImport");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember() {
        java.lang.Object value = get("ownedMember");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport() {
        java.lang.Object value = get("packageImport");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule() {
        java.lang.Object value = get("ownedRule");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        java.lang.Object value = get("member");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importedMemberOperation()  {
        java.lang.Object value = invokeOperation("importedMember", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember()  {
        java.lang.Object value = invokeOperation("getNamesOfMember", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        java.lang.Object value = invokeOperation("getNamesOfMember_core.abstractions.namespaces.NamedElement", new java.lang.Object[] { element });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importMembers(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        java.lang.Object value = invokeOperation("importMembers_cmof.PackageableElement", new java.lang.Object[] { imps });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> excludeCollisions(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        java.lang.Object value = invokeOperation("excludeCollisions_cmof.PackageableElement", new java.lang.Object[] { imps });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean membersAreDistinguishable()  {
        java.lang.Object value = invokeOperation("membersAreDistinguishable", new java.lang.Object[] {  });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
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

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getInheritedMember() {
        java.lang.Object value = get("inheritedMember");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> inheritedMemberOperation()  {
        java.lang.Object value = invokeOperation("inheritedMember", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> parents()  {
        java.lang.Object value = invokeOperation("parents", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> allParents()  {
        java.lang.Object value = invokeOperation("allParents", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> inheritableMembers(core.abstractions.umlsuper.Classifier c)  {
        java.lang.Object value = invokeOperation("inheritableMembers_core.abstractions.super.Classifier", new java.lang.Object[] { c });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n)  {
        java.lang.Object value = invokeOperation("hasVisibilityOf_core.abstractions.namespaces.NamedElement", new java.lang.Object[] { n });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public boolean maySpecializeType(core.abstractions.umlsuper.Classifier c)  {
        java.lang.Object value = invokeOperation("maySpecializeType_core.abstractions.super.Classifier", new java.lang.Object[] { c });
        if (value == null) {
           throw new RuntimeException("assert");
        } else {
            return (java.lang.Boolean)value;
        }
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature> allFeatures()  {
        java.lang.Object value = invokeOperation("allFeatures", new java.lang.Object[] {  });
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value);
        }
    }

    public cmof.Package getPackage() {
        java.lang.Object value = get("package");
        if (value == null) {
           return null;
        } else {
            return (cmof.Package)value;
        }
    }

    public void setPackage(cmof.Package value) {
        set("package", value);
    }

    public void setPackage(core.basic.Package value) {
        set("package", value);
    }

}

