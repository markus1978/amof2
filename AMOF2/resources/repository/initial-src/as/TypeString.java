package as;


/**
 * <b>TypeString</b>, superClass = {cmof.Type}
 */
public interface TypeString extends cmof.Type
{

    /**
     * <b>qualifiedTypeName</b>, multiplicity=(1,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<java.lang.String> getQualifiedTypeName();

}

