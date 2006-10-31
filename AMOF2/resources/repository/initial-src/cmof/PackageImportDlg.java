package cmof;


public class PackageImportDlg extends hub.sam.mof.reflection.ObjectDlg implements PackageImport
{
    protected PackageImport self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (PackageImport)self;
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        return (core.abstractions.visibilities.VisibilityKind)(java.lang.Object)self.getVisibility();
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        self.setVisibility(value);
    }

    public cmof.Package getImportedPackage() {
        return (cmof.Package)(java.lang.Object)self.getImportedPackage();
    }

    public void setImportedPackage(cmof.Package value) {
        self.setImportedPackage(value);
    }

    public cmof.Namespace getImportingNamespace() {
        return (cmof.Namespace)(java.lang.Object)self.getImportingNamespace();
    }

    public void setImportingNamespace(cmof.Namespace value) {
        self.setImportingNamespace(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getSource() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Element>)(java.lang.Object)self.getSource();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getTarget() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Element>)(java.lang.Object)self.getTarget();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getRelatedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Element>)(java.lang.Object)self.getRelatedElement();
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

}

