package as;


/**
 * <b>GuardSpecification</b>, superClass = {cmof.TypedElement}
 */
public interface GuardSpecification extends cmof.TypedElement
{

    /**
     * <b>body</b>, multiplicity=(1,1)
     */
    public java.lang.String getBody();

    public void setBody(java.lang.String value);

    /**
     * <b>input</b>, multiplicity=(0,1), isComposite, subsettedProperty = {cmof.Element.ownedElement}
     */
    public as.ContextPin getInput();

    public void setInput(as.ContextPin value);

}

