package core.abstractions.comments;


/**
 * <b>Element</b>, superClass = {core.abstractions.ownerships.Element}
 */
public interface Element extends core.abstractions.ownerships.Element
{

    /**
     * <b>ownedComment</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.comments.Comment> getOwnedComment();

}

