package core.abstractions.literals;


/**
 * <b>LiteralUnlimitedNatural</b>, superClass = {core.abstractions.literals.LiteralSpecification}
 */
public interface LiteralUnlimitedNatural extends core.abstractions.literals.LiteralSpecification
{

    /**
     * <b>value</b>, multiplicity=(1,1)
     */
    public long getValue();

    public void setValue(long value);

    /**
     * <b>isComputable</b>, multiplicity=(1,1)
     */
    public boolean isComputable() ;

    /**
     * <b>unlimitedValue</b>, multiplicity=(0,1)
     */
    public long unlimitedValue() ;

}

