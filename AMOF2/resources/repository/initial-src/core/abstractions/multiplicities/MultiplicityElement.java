package core.abstractions.multiplicities;


/**
 * <b>MultiplicityElement</b>, isAbstract, superClass = {core.abstractions.elements.Element}
 * <br>constraint - upper_gt_0 : 
 * <pre>upperBound()->notEmpty() implies upperBound() > 0</pre>
 * <br>constraint - lower_ge_0 : 
 * <pre>lowerBound()->notEmpty() implies lowerBound() >= 0</pre>
 * <br>constraint - upper_ge_lower : 
 * <pre>(upperBound()->notEmpty() and lowerBound()->notEmpty()) implies upperBound() >= lowerBound()</pre>
 */
public interface MultiplicityElement extends core.abstractions.elements.Element
{

    /**
     * <b>isOrdered</b>, multiplicity=(1,1)
     */
    public boolean isOrdered();

    public void setIsOrdered(boolean value);

    /**
     * <b>isUnique</b>, multiplicity=(1,1)
     */
    public boolean isUnique();

    public void setIsUnique(boolean value);

    /**
     * <b>lower</b>, multiplicity=(0,1)
     */
    public int getLower();

    public void setLower(int value);

    /**
     * <b>upper</b>, multiplicity=(0,1)
     */
    public long getUpper();

    public void setUpper(long value);

    /**
     * <b>lowerBound</b>, multiplicity=(0,1)
     */
    public int lowerBound() ;

    /**
     * <b>upperBound</b>, multiplicity=(0,1)
     */
    public long upperBound() ;

    /**
     * <b>isMultivalued</b>, multiplicity=(1,1)
     */
    public boolean isMultivalued() ;

    /**
     * <b>includesCardinality</b>, multiplicity=(1,1)
     */
    public boolean includesCardinality(int C) ;

    /**
     * <b>includesMultiplicity</b>, multiplicity=(1,1)
     */
    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M) ;

}

