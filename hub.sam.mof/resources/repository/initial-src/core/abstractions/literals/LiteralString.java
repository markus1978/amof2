package core.abstractions.literals;


/**
 * <b>LiteralString</b>, superClass = {core.abstractions.literals.LiteralSpecification}
 */
public interface LiteralString extends core.abstractions.literals.LiteralSpecification
{

    /**
     * <b>value</b>, multiplicity=(1,1)
     */
    public java.lang.String getValue();

    public void setValue(java.lang.String value);

    /**
     * <b>isComputable</b>, multiplicity=(1,1)
     */
    public boolean isComputable() ;

    /**
     * <b>stringValue</b>, multiplicity=(0,1)
     */
    public java.lang.String stringValue() ;

}

