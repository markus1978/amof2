package core.abstractions.literals;


/**
 * <b>LiteralInteger</b>, superClass = {core.abstractions.literals.LiteralSpecification}
 */
public interface LiteralInteger extends core.abstractions.literals.LiteralSpecification
{

    /**
     * <b>value</b>, multiplicity=(1,1)
     */
    public int getValue();

    public void setValue(int value);

    /**
     * <b>isComputable</b>, multiplicity=(1,1)
     */
    public boolean isComputable() ;

    /**
     * <b>integerValue</b>, multiplicity=(0,1)
     */
    public int integerValue() ;

}

