package core.abstractions.multiplicityexpressions;


/**
 * <b>MultiplicityElement</b>, isAbstract, superClass = {core.abstractions.multiplicities.MultiplicityElement, core.abstractions.ownerships.Element}
 * <br>constraint - lower_eq_lowerbound : 
 * <pre>lower=lowerBound()</pre>
 * <br>constraint - upper_eq_upperbound : 
 * <pre>upper = upperBound()</pre>
 */
public interface MultiplicityElement extends core.abstractions.multiplicities.MultiplicityElement, core.abstractions.ownerships.Element
{

    /**
     * <b>lower</b>, multiplicity=(0,1), isDerived, redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.lower}
     */
    public int getLower();

    public void setLower(int value);

    /**
     * <b>upper</b>, multiplicity=(0,1), isDerived, redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.upper}
     */
    public long getUpper();

    public void setUpper(long value);

    /**
     * <b>upperValue</b>, multiplicity=(0,1), isComposite, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public core.abstractions.expressions.ValueSpecification getUpperValue();

    public void setUpperValue(core.abstractions.expressions.ValueSpecification value);

    /**
     * <b>lowerValue</b>, multiplicity=(0,1), isComposite, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public core.abstractions.expressions.ValueSpecification getLowerValue();

    public void setLowerValue(core.abstractions.expressions.ValueSpecification value);

    /**
     * <b>lower</b>, multiplicity=(0,1)
     */
    public int lowerOperation() ;

    /**
     * <b>upper</b>, multiplicity=(0,1)
     */
    public long upperOperation() ;

    /**
     * <b>lowerBound</b>, multiplicity=(0,1)
     */
    public int lowerBound() ;

    /**
     * <b>upperBound</b>, multiplicity=(0,1)
     */
    public long upperBound() ;

}

