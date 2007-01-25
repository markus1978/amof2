package hub.sam.mof.mas;


public class ObjectFlowDlg extends hub.sam.mof.reflection.ObjectDlg implements ObjectFlow
{
    protected ObjectFlow self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ObjectFlow)self;
    }

    public hub.sam.mof.mas.GuardSpecification getGuardSpecification() {
        return (hub.sam.mof.mas.GuardSpecification)(java.lang.Object)self.getGuardSpecification();
    }

    public void setGuardSpecification(hub.sam.mof.mas.GuardSpecification value) {
        self.setGuardSpecification(value);
    }

    public hub.sam.mof.mas.Activity getActivity() {
        return (hub.sam.mof.mas.Activity)(java.lang.Object)self.getActivity();
    }

    public void setActivity(hub.sam.mof.mas.Activity value) {
        self.setActivity(value);
    }

    public hub.sam.mof.mas.ActivityNode getTarget() {
        return (hub.sam.mof.mas.ActivityNode)(java.lang.Object)self.getTarget();
    }

    public void setTarget(hub.sam.mof.mas.ActivityNode value) {
        self.setTarget(value);
    }

    public hub.sam.mof.mas.ActivityNode getSource() {
        return (hub.sam.mof.mas.ActivityNode)(java.lang.Object)self.getSource();
    }

    public void setSource(hub.sam.mof.mas.ActivityNode value) {
        self.setSource(value);
    }

    public cmof.common.ReflectiveSequence<hub.sam.mase.m2model.ActivityEdgeBendpoint> getBendpoints() {
        return self.getBendpoints();
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

