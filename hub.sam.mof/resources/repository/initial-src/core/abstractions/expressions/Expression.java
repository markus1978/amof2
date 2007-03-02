package core.abstractions.expressions;


/**
 * <b>Expression</b>, superClass = {core.abstractions.expressions.ValueSpecification}
 */
public interface Expression extends core.abstractions.expressions.ValueSpecification
{

    /**
     * <b>symbol</b>, multiplicity=(1,1)
     */
    public java.lang.String getSymbol();

    public void setSymbol(java.lang.String value);

    /**
     * <b>operand</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getOperand();

}

