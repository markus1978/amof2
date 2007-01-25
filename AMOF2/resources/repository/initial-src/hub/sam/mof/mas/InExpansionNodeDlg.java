package hub.sam.mof.mas;


public class InExpansionNodeDlg extends hub.sam.mof.reflection.ObjectDlg implements InExpansionNode
{
    protected InExpansionNode self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (InExpansionNode)self;
    }

    public hub.sam.mof.mas.ExpansionRegion getRegionAsInput() {
        return (hub.sam.mof.mas.ExpansionRegion)(java.lang.Object)self.getRegionAsInput();
    }

    public void setRegionAsInput(hub.sam.mof.mas.ExpansionRegion value) {
        self.setRegionAsInput(value);
    }

    public hub.sam.mof.mas.InExpansionNodeInstance metaCreateInExpansionNodeInstance()  {
        return (hub.sam.mof.mas.InExpansionNodeInstance)(java.lang.Object)self.metaCreateInExpansionNodeInstance();
    }

    public hub.sam.mof.mas.PinInstance metaCreatePinInstance()  {
        return (hub.sam.mof.mas.PinInstance)(java.lang.Object)self.metaCreatePinInstance();
    }

    public hub.sam.mof.petrinets.PlaceInstance metaCreatePlaceInstance()  {
        return (hub.sam.mof.petrinets.PlaceInstance)(java.lang.Object)self.metaCreatePlaceInstance();
    }

    public hub.sam.mof.mas.InExpansionNodeInstance metaGCreateInExpansionNodeInstance(java.lang.String className)  {
        return (hub.sam.mof.mas.InExpansionNodeInstance)(java.lang.Object)self.metaGCreateInExpansionNodeInstance(className);
    }

    public hub.sam.mof.mas.PinInstance metaGCreatePinInstance(java.lang.String className)  {
        return (hub.sam.mof.mas.PinInstance)(java.lang.Object)self.metaGCreatePinInstance(className);
    }

    public hub.sam.mof.petrinets.PlaceInstance metaGCreatePlaceInstance(java.lang.String className)  {
        return (hub.sam.mof.petrinets.PlaceInstance)(java.lang.Object)self.metaGCreatePlaceInstance(className);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.InExpansionNodeInstance> getMetaInstanceInExpansionNodeInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.InExpansionNodeInstance>)(java.lang.Object)self.getMetaInstanceInExpansionNodeInstance();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.PinInstance> getMetaInstancePinInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.PinInstance>)(java.lang.Object)self.getMetaInstancePinInstance();
    }

    public int getInitialTokesn() {
        return self.getInitialTokesn();
    }

    public void setInitialTokesn(int value) {
        self.setInitialTokesn(value);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.PlaceInstance> getMetaInstancePlaceInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.PlaceInstance>)(java.lang.Object)self.getMetaInstancePlaceInstance();
    }

    public int getNum() {
        return self.getNum();
    }

    public void setNum(int value) {
        self.setNum(value);
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

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNodeList> getList() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNodeList>)(java.lang.Object)self.getList();
    }

    public hub.sam.mof.mas.Activity getActivityAsNode() {
        return (hub.sam.mof.mas.Activity)(java.lang.Object)self.getActivityAsNode();
    }

    public void setActivityAsNode(hub.sam.mof.mas.Activity value) {
        self.setActivityAsNode(value);
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getIncoming() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge>)(java.lang.Object)self.getIncoming();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getOutgoing() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge>)(java.lang.Object)self.getOutgoing();
    }

    public org.eclipse.draw2d.geometry.Rectangle getRectangle() {
        return self.getRectangle();
    }

    public void setRectangle(org.eclipse.draw2d.geometry.Rectangle value) {
        self.setRectangle(value);
    }

    public java.lang.String getComment() {
        return self.getComment();
    }

    public void setComment(java.lang.String value) {
        self.setComment(value);
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

}

