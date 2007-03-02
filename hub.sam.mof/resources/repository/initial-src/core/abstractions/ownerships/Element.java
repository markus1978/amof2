package core.abstractions.ownerships;


/**
 * <b>Element</b>, isAbstract, superClass = {core.abstractions.elements.Element}
 * <br>constraint - not_own_self : 
 * <pre>not self.allOwnedElements()->includes(self)</pre>
 * <br>constraint - has_owner : 
 * <pre>self.mustBeOwned() implies owner->notEmpty()</pre>
 */
public interface Element extends core.abstractions.elements.Element
{

    /**
     * <b>ownedElement</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement();

    /**
     * <b>owner</b>, multiplicity=(0,1), isDerivedUnion, isDerived
     */
    public core.abstractions.ownerships.Element getOwner();

    public void setOwner(core.abstractions.ownerships.Element value);

    /**
     * <b>allOwnedElements</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements() ;

    /**
     * <b>mustBeOwned</b>, multiplicity=(1,1)
     */
    public boolean mustBeOwned() ;

}

