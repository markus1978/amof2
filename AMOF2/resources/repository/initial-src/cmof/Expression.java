package cmof;


/**
 * <b>Expression</b>, superClass = {cmof.ValueSpecification, core.abstractions.expressions.Expression}
 */
public interface Expression extends cmof.ValueSpecification, core.abstractions.expressions.Expression
{

    /**
     * <b>operand</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement}, redefinedProperty = {core.abstractions.expressions.Expression.operand}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.ValueSpecification> getOperand();

}

