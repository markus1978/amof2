package core.abstractions.redefinitions;


public class RedefinableElementDlg extends hub.sam.mof.reflection.ObjectDlg implements RedefinableElement
{
    protected RedefinableElement self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (RedefinableElement)self;
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.redefinitions.RedefinableElement> getRedefinedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.redefinitions.RedefinableElement>)(java.lang.Object)self.getRedefinedElement();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getRedefinitionContext() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier>)(java.lang.Object)self.getRedefinitionContext();
    }

    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee)  {
        return self.isConsistentWith(redefinee);
    }

    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable)  {
        return self.isRedefinitionContextValid(redefinable);
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

