package cmof;


public class PropertyDlg extends hub.sam.mof.reflection.ObjectDlg implements Property
{
    protected Property self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Property)self;
    }

    public cmof.Property oppositeOperation()  {
        return (cmof.Property)(java.lang.Object)self.oppositeOperation();
    }

    public boolean isConsistentWith(cmof.RedefinableElement redefinee)  {
        return self.isConsistentWith(redefinee);
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        return self.isConsistentWith(redefinee);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> subsettingContext()  {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.subsettingContext();
    }

    public cmof.Element getOwnedBehavior() {
        return (cmof.Element)(java.lang.Object)self.getOwnedBehavior();
    }

    public void setOwnedBehavior(cmof.Element value) {
        self.setOwnedBehavior(value);
    }

    public cmof.Property getQualifier() {
        return (cmof.Property)(java.lang.Object)self.getQualifier();
    }

    public void setQualifier(cmof.Property value) {
        self.setQualifier(value);
    }

    public boolean isID() {
        return self.isID();
    }

    public void setIsID(boolean value) {
        self.setIsID(value);
    }

    public java.lang.String getDetails() {
        return self.getDetails();
    }

    public void setDetails(java.lang.String value) {
        self.setDetails(value);
    }

    public boolean isReadOnly() {
        return self.isReadOnly();
    }

    public void setIsReadOnly(boolean value) {
        self.setIsReadOnly(value);
    }

    public boolean isDerivedUnion() {
        return self.isDerivedUnion();
    }

    public void setIsDerivedUnion(boolean value) {
        self.setIsDerivedUnion(value);
    }

    public cmof.UmlClass getUmlClass() {
        return (cmof.UmlClass)(java.lang.Object)self.getUmlClass();
    }

    public void setUmlClass(cmof.UmlClass value) {
        self.setUmlClass(value);
    }

    public void setUmlClass(core.basic.UmlClass value) {
        self.setUmlClass(value);
    }

    public cmof.Association getAssociation() {
        return (cmof.Association)(java.lang.Object)self.getAssociation();
    }

    public void setAssociation(cmof.Association value) {
        self.setAssociation(value);
    }

    public cmof.Association getOwningAssociation() {
        return (cmof.Association)(java.lang.Object)self.getOwningAssociation();
    }

    public void setOwningAssociation(cmof.Association value) {
        self.setOwningAssociation(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getRedefinedProperty() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Property>)(java.lang.Object)self.getRedefinedProperty();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Property> getSubsettedProperty() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Property>)(java.lang.Object)self.getSubsettedProperty();
    }

    public cmof.Property getOpposite() {
        return (cmof.Property)(java.lang.Object)self.getOpposite();
    }

    public void setOpposite(cmof.Property value) {
        self.setOpposite(value);
    }

    public void setOpposite(core.basic.Property value) {
        self.setOpposite(value);
    }

    public cmof.DataType getDatatype() {
        return (cmof.DataType)(java.lang.Object)self.getDatatype();
    }

    public void setDatatype(cmof.DataType value) {
        self.setDatatype(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getFeaturingClassifier();
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

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
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

    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Classifier>)(java.lang.Object)self.getRedefinitionContext();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement>)(java.lang.Object)self.getRedefinedElement();
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

    public cmof.Type getType() {
        return (cmof.Type)(java.lang.Object)self.getType();
    }

    public void setType(cmof.Type value) {
        self.setType(value);
    }

    public void setType(core.abstractions.typedelements.Type value) {
        self.setType(value);
    }

    public int lowerBound()  {
        return self.lowerBound();
    }

    public long upperBound()  {
        return self.upperBound();
    }

    public boolean isMultivalued()  {
        return self.isMultivalued();
    }

    public boolean includesCardinality(int C)  {
        return self.includesCardinality(C);
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M)  {
        return self.includesMultiplicity(M);
    }

    public boolean isOrdered() {
        return self.isOrdered();
    }

    public void setIsOrdered(boolean value) {
        self.setIsOrdered(value);
    }

    public boolean isUnique() {
        return self.isUnique();
    }

    public void setIsUnique(boolean value) {
        self.setIsUnique(value);
    }

    public int getLower() {
        return self.getLower();
    }

    public void setLower(int value) {
        self.setLower(value);
    }

    public long getUpper() {
        return self.getUpper();
    }

    public void setUpper(long value) {
        self.setUpper(value);
    }

    public java.lang.String getDefault() {
        return self.getDefault();
    }

    public void setDefault(java.lang.String value) {
        self.setDefault(value);
    }

    public boolean isComposite() {
        return self.isComposite();
    }

    public void setIsComposite(boolean value) {
        self.setIsComposite(value);
    }

    public boolean isDerived() {
        return self.isDerived();
    }

    public void setIsDerived(boolean value) {
        self.setIsDerived(value);
    }

}

