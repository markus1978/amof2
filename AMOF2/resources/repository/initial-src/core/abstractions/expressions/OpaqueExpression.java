package core.abstractions.expressions;


/**
 * <b>OpaqueExpression</b>, superClass = {core.abstractions.expressions.ValueSpecification}
 */
public interface OpaqueExpression extends core.abstractions.expressions.ValueSpecification
{

    /**
     * <b>body</b>, multiplicity=(1,1)
     */
    public java.lang.String getBody();

    public void setBody(java.lang.String value);

    /**
     * <b>language</b>, multiplicity=(0,1)
     */
    public java.lang.String getLanguage();

    public void setLanguage(java.lang.String value);

}

