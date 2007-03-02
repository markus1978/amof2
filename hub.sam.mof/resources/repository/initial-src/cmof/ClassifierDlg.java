package cmof;


public class ClassifierDlg extends hub.sam.mof.reflection.ObjectDlg implements Classifier
{
    protected Classifier self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Classifier)self;
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getMetaInstances() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getMetaInstances();
    }

    public cmof.Classifier getMetaClassifier() {
        return (cmof.Classifier)(java.lang.Object)self.getMetaClassifier();
    }

    public void setMetaClassifier(cmof.Classifier value) {
        self.setMetaClassifier(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getAttribute() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Property>)(java.lang.Object)self.getAttribute();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Feature> getFeature() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Feature>)(java.lang.Object)self.getFeature();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getGeneral() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getGeneral();
    }

    public boolean conformsTo(cmof.Classifier other)  {
        return self.conformsTo(other);
    }

    public boolean conformsTo(core.abstractions.typedelements.Type other)  {
        return self.conformsTo(other);
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

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

    public java.lang.String getQualifiedName() {
        return self.getQualifiedName();
    }

    public void setQualifiedName(java.lang.String value) {
        self.setQualifiedName(value);
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

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
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

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Comment>)(java.lang.Object)self.getOwnedComment();
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        return (core.abstractions.visibilities.VisibilityKind)(java.lang.Object)self.getVisibility();
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        self.setVisibility(value);
    }

    public boolean isAbstract() {
        return self.isAbstract();
    }

    public void setIsAbstract(boolean value) {
        self.setIsAbstract(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getInheritedMember() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement>)(java.lang.Object)self.getInheritedMember();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> inheritedMemberOperation()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement>)(java.lang.Object)self.inheritedMemberOperation();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> parents()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier>)(java.lang.Object)self.parents();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> allParents()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier>)(java.lang.Object)self.allParents();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> inheritableMembers(core.abstractions.umlsuper.Classifier c)  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement>)(java.lang.Object)self.inheritableMembers(c);
    }

    public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n)  {
        return self.hasVisibilityOf(n);
    }

    public boolean maySpecializeType(core.abstractions.umlsuper.Classifier c)  {
        return self.maySpecializeType(c);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature> allFeatures()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature>)(java.lang.Object)self.allFeatures();
    }

    public cmof.Package getPackage() {
        return (cmof.Package)(java.lang.Object)self.getPackage();
    }

    public void setPackage(cmof.Package value) {
        self.setPackage(value);
    }

    public void setPackage(core.basic.Package value) {
        self.setPackage(value);
    }

}

