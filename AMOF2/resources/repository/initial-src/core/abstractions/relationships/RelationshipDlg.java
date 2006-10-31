package core.abstractions.relationships;


public class RelationshipDlg extends hub.sam.mof.reflection.ObjectDlg implements Relationship
{
    protected Relationship self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Relationship)self;
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

