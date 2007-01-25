package hub.sam.mof.mas;


public class ActivityGroupDlg extends hub.sam.mof.reflection.ObjectDlg implements ActivityGroup
{
    protected ActivityGroup self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ActivityGroup)self;
    }

    public hub.sam.mof.mas.Activity getActivityAsGroup() {
        return (hub.sam.mof.mas.Activity)(java.lang.Object)self.getActivityAsGroup();
    }

    public void setActivityAsGroup(hub.sam.mof.mas.Activity value) {
        self.setActivityAsGroup(value);
    }

    public org.eclipse.draw2d.geometry.Rectangle getRectangle() {
        return self.getRectangle();
    }

    public void setRectangle(org.eclipse.draw2d.geometry.Rectangle value) {
        self.setRectangle(value);
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

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASTransition> getTransitions() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASTransition>)(java.lang.Object)self.getTransitions();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace> getPlaces() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace>)(java.lang.Object)self.getPlaces();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ValueNode> getArgument() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ValueNode>)(java.lang.Object)self.getArgument();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getEdge() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge>)(java.lang.Object)self.getEdge();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityNode> getNode() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityNode>)(java.lang.Object)self.getNode();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityGroup> getGroup() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityGroup>)(java.lang.Object)self.getGroup();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild> getGefChildren() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild>)(java.lang.Object)self.getGefChildren();
    }

    public hub.sam.mof.mas.ActivityInstance instantiate()  {
        return (hub.sam.mof.mas.ActivityInstance)(java.lang.Object)self.instantiate();
    }

    public hub.sam.mof.mas.ActivityInstance metaCreateActivityInstance()  {
        return (hub.sam.mof.mas.ActivityInstance)(java.lang.Object)self.metaCreateActivityInstance();
    }

    public hub.sam.mof.petrinets.NetInstance metaCreateNetInstance()  {
        return (hub.sam.mof.petrinets.NetInstance)(java.lang.Object)self.metaCreateNetInstance();
    }

    public hub.sam.mof.mas.ActivityInstance metaGCreateActivityInstance(java.lang.String className)  {
        return (hub.sam.mof.mas.ActivityInstance)(java.lang.Object)self.metaGCreateActivityInstance(className);
    }

    public hub.sam.mof.petrinets.NetInstance metaGCreateNetInstance(java.lang.String className)  {
        return (hub.sam.mof.petrinets.NetInstance)(java.lang.Object)self.metaGCreateNetInstance(className);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ActivityInstance> getMetaInstanceActivityInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ActivityInstance>)(java.lang.Object)self.getMetaInstanceActivityInstance();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.NetInstance> getMetaInstanceNetInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.NetInstance>)(java.lang.Object)self.getMetaInstanceNetInstance();
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

