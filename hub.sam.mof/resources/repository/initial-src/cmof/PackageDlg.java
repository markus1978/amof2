package cmof;


public class PackageDlg extends hub.sam.mof.reflection.ObjectDlg implements Package
{
    protected Package self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Package)self;
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getPackagedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.getPackagedElement();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getOwnedType() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Type>)(java.lang.Object)self.getOwnedType();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Package> getNestedPackage() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Package>)(java.lang.Object)self.getNestedPackage();
    }

    public cmof.Package getNestingPackage() {
        return (cmof.Package)(java.lang.Object)self.getNestingPackage();
    }

    public void setNestingPackage(cmof.Package value) {
        self.setNestingPackage(value);
    }

    public void setNestingPackage(core.basic.Package value) {
        self.setNestingPackage(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageMerge> getPackageMerge() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageMerge>)(java.lang.Object)self.getPackageMerge();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> visibleMembers()  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.visibleMembers();
    }

    public boolean makesVisible(cmof.NamedElement el)  {
        return self.makesVisible(el);
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

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.getImportedMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        return (cmof.common.ReflectiveCollection<? extends cmof.ElementImport>)(java.lang.Object)self.getElementImport();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.NamedElement>)(java.lang.Object)self.getOwnedMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageImport>)(java.lang.Object)self.getPackageImport();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Constraint>)(java.lang.Object)self.getOwnedRule();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.NamedElement>)(java.lang.Object)self.getMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importedMemberOperation()  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.importedMemberOperation();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember()  {
        return self.getNamesOfMember();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        return self.getNamesOfMember(element);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importMembers(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.importMembers(imps);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> excludeCollisions(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.excludeCollisions(imps);
    }

    public boolean membersAreDistinguishable()  {
        return self.membersAreDistinguishable();
    }

}

