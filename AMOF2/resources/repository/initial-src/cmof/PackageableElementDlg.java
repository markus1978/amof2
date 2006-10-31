package cmof;


public class PackageableElementDlg extends hub.sam.mof.reflection.ObjectDlg implements PackageableElement
{
    protected PackageableElement self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (PackageableElement)self;
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

    public cmof.Namespace getNamespace() {
        return (cmof.Namespace)(java.lang.Object)self.getNamespace();
    }

    public void setNamespace(cmof.Namespace value) {
        self.setNamespace(value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        self.setNamespace(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Tag> getTag() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Tag>)(java.lang.Object)self.getTag();
    }

    public java.lang.String getUri() {
        return self.getUri();
    }

    public void setUri(java.lang.String value) {
        self.setUri(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public cmof.Element getOwner() {
        return (cmof.Element)(java.lang.Object)self.getOwner();
    }

    public void setOwner(cmof.Element value) {
        self.setOwner(value);
    }

    public void setOwner(core.abstractions.ownerships.Element value) {
        self.setOwner(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Comment>)(java.lang.Object)self.getOwnedComment();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        return (core.abstractions.visibilities.VisibilityKind)(java.lang.Object)self.getVisibility();
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        self.setVisibility(value);
    }

    public java.lang.String getQualifiedName() {
        return self.getQualifiedName();
    }

    public void setQualifiedName(java.lang.String value) {
        self.setQualifiedName(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces()  {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace>)(java.lang.Object)self.allNamespaces();
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return self.isDistinguishableFrom(n, ns);
    }

    public java.lang.String separator()  {
        return self.separator();
    }

    public java.lang.String qualifiedNameOperation()  {
        return self.qualifiedNameOperation();
    }

}

