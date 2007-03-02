package core.abstractions.constraints;


public class ConstraintDlg extends hub.sam.mof.reflection.ObjectDlg implements Constraint
{
    protected Constraint self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Constraint)self;
    }

    public core.abstractions.namespaces.Namespace getContext() {
        return (core.abstractions.namespaces.Namespace)(java.lang.Object)self.getContext();
    }

    public void setContext(core.abstractions.namespaces.Namespace value) {
        self.setContext(value);
    }

    public core.abstractions.constraints.Namespace getNamespace() {
        return (core.abstractions.constraints.Namespace)(java.lang.Object)self.getNamespace();
    }

    public void setNamespace(core.abstractions.constraints.Namespace value) {
        self.setNamespace(value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        self.setNamespace(value);
    }

    public core.abstractions.expressions.ValueSpecification getSpecification() {
        return (core.abstractions.expressions.ValueSpecification)(java.lang.Object)self.getSpecification();
    }

    public void setSpecification(core.abstractions.expressions.ValueSpecification value) {
        self.setSpecification(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.ownerships.Element> getConstrainedElement() {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getConstrainedElement();
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

