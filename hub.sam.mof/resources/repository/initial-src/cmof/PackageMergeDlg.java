package cmof;


public class PackageMergeDlg extends hub.sam.mof.reflection.ObjectDlg implements PackageMerge
{
    protected PackageMerge self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (PackageMerge)self;
    }

    public cmof.Package getMergingPackage() {
        return (cmof.Package)(java.lang.Object)self.getMergingPackage();
    }

    public void setMergingPackage(cmof.Package value) {
        self.setMergingPackage(value);
    }

    public cmof.Package getMergedPackage() {
        return (cmof.Package)(java.lang.Object)self.getMergedPackage();
    }

    public void setMergedPackage(cmof.Package value) {
        self.setMergedPackage(value);
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

