package core.abstractions.expressions;


/**
 * <b>ValueSpecification</b>, isAbstract, superClass = {core.abstractions.ownerships.Element}
 */
public interface ValueSpecification extends core.abstractions.ownerships.Element
{

    /**
     * <b>isComputable</b>, multiplicity=(1,1)
     */
    public boolean isComputable() ;

    /**
     * <b>integerValue</b>, multiplicity=(0,1)
     */
    public int integerValue() ;

    /**
     * <b>booleanValue</b>, multiplicity=(0,1)
     */
    public boolean booleanValue() ;

    /**
     * <b>stringValue</b>, multiplicity=(0,1)
     */
    public java.lang.String stringValue() ;

    /**
     * <b>unlimitedValue</b>, multiplicity=(0,1)
     */
    public long unlimitedValue() ;

    /**
     * <b>isNull</b>, multiplicity=(1,1)
     */
    public boolean isNull() ;

}

