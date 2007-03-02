package cmof;


/**
 * <b>Element</b>, isAbstract, superClass = {core.abstractions.comments.Element}
 */
public interface Element extends core.abstractions.comments.Element
{

    /**
     * <b>tag</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Tag> getTag();

    /**
     * <b>uri</b>, multiplicity=(0,1)
     */
    public java.lang.String getUri();

    public void setUri(java.lang.String value);

    /**
     * <b>ownedElement</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isComposite, isUnique, redefinedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement();

    /**
     * <b>owner</b>, multiplicity=(0,1), isDerivedUnion, isDerived, redefinedProperty = {core.abstractions.ownerships.Element.owner}
     */
    public cmof.Element getOwner();

    public void setOwner(cmof.Element value);

    public void setOwner(core.abstractions.ownerships.Element value);

    /**
     * <b>ownedComment</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Element.ownedElement}, redefinedProperty = {core.abstractions.comments.Element.ownedComment}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment();

}

