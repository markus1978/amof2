package core.abstractions.generalizations;


public class ClassifierDlg extends hub.sam.mof.reflection.ObjectDlg implements Classifier
{
    protected Classifier self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Classifier)self;
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Generalization> getGeneralization() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Generalization>)(java.lang.Object)self.getGeneralization();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> getGeneral() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier>)(java.lang.Object)self.getGeneral();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> generalOperation()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier>)(java.lang.Object)self.generalOperation();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> parents()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier>)(java.lang.Object)self.parents();
    }

    public boolean conformsTo(core.abstractions.generalizations.Classifier other)  {
        return self.conformsTo(other);
    }

    public boolean conformsTo(core.abstractions.typedelements.Type other)  {
        return self.conformsTo(other);
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

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature> getFeature() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature>)(java.lang.Object)self.getFeature();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature> allFeatures()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Feature>)(java.lang.Object)self.allFeatures();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getMember() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement>)(java.lang.Object)self.getMember();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getOwnedMember() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement>)(java.lang.Object)self.getOwnedMember();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        return self.getNamesOfMember(element);
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

    public core.abstractions.namespaces.Namespace getNamespace() {
        return (core.abstractions.namespaces.Namespace)(java.lang.Object)self.getNamespace();
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

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public core.abstractions.ownerships.Element getOwner() {
        return (core.abstractions.ownerships.Element)(java.lang.Object)self.getOwner();
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

}

