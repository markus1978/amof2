package core.abstractions.literals;


/**
 * <b>LiteralNull</b>, superClass = {core.abstractions.literals.LiteralSpecification}
 */
public interface LiteralNull extends core.abstractions.literals.LiteralSpecification
{

    /**
     * <b>isComputable</b>, multiplicity=(1,1)
     */
    public boolean isComputable() ;

    /**
     * <b>isNull</b>, multiplicity=(1,1)
     */
    public boolean isNull() ;

}

