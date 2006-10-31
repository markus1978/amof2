package core.abstractions.relationships;


public class DirectedRelationshipDlg extends hub.sam.mof.reflection.ObjectDlg implements DirectedRelationship
{
    protected DirectedRelationship self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (DirectedRelationship)self;
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getSource() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getSource();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getTarget() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getTarget();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getRelatedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getRelatedElement();
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

