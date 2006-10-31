package cmof;


public class BehavioralFeatureDlg extends hub.sam.mof.reflection.ObjectDlg implements BehavioralFeature
{
    protected BehavioralFeature self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (BehavioralFeature)self;
    }

    public cmof.CallConcurrencyKind getConcurrency() {
        return (cmof.CallConcurrencyKind)(java.lang.Object)self.getConcurrency();
    }

    public void setConcurrency(cmof.CallConcurrencyKind value) {
        self.setConcurrency(value);
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getParameter() {
        return (cmof.common.ReflectiveSequence<? extends cmof.Parameter>)(java.lang.Object)self.getParameter();
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter() {
        return (cmof.common.ReflectiveSequence<? extends cmof.Parameter>)(java.lang.Object)self.getFormalParameter();
    }

    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getReturnResult() {
        return (cmof.common.ReflectiveSequence<? extends cmof.Parameter>)(java.lang.Object)self.getReturnResult();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Type>)(java.lang.Object)self.getRaisedException();
    }

    public boolean isDistinguishableFrom(cmof.NamedElement n, cmof.Namespace ns)  {
        return self.isDistinguishableFrom(n, ns);
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return self.isDistinguishableFrom(n, ns);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getFeaturingClassifier();
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

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.NamedElement>)(java.lang.Object)self.getMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.NamedElement>)(java.lang.Object)self.getOwnedMember();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember()  {
        return self.getNamesOfMember();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        return self.getNamesOfMember(element);
    }

    public boolean membersAreDistinguishable()  {
        return self.membersAreDistinguishable();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getRedefinitionContext();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement>)(java.lang.Object)self.getRedefinedElement();
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        return self.isConsistentWith(redefinee);
    }

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable)  {
        return self.isRedefinitionContextValid(redefinable);
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

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.getImportedMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        return (cmof.common.ReflectiveCollection<? extends cmof.ElementImport>)(java.lang.Object)self.getElementImport();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageImport>)(java.lang.Object)self.getPackageImport();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Constraint>)(java.lang.Object)self.getOwnedRule();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importedMemberOperation()  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.importedMemberOperation();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importMembers(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.importMembers(imps);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> excludeCollisions(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.excludeCollisions(imps);
    }

}

