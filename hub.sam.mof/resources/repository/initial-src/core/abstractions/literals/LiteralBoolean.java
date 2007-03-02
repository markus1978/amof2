package core.abstractions.literals;


/**
 * <b>LiteralBoolean</b>, superClass = {core.abstractions.literals.LiteralSpecification}
 */
public interface LiteralBoolean extends core.abstractions.literals.LiteralSpecification
{

    /**
     * <b>value</b>, multiplicity=(1,1)
     */
    public boolean value();

    public void setValue(boolean value);

    /**
     * <b>isComputable</b>, multiplicity=(1,1)
     */
    public boolean isComputable() ;

    /**
     * <b>booleanValue</b>, multiplicity=(0,1)
     */
    public boolean booleanValue() ;

}

