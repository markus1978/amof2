package hub.sam.mof.mas;


/**
 * <b>TypeString</b>, superClass = {cmof.Type}
 */
public interface TypeString extends cmof.reflection.Object, cmof.Type
{

    /**
     * <b>qualifiedTypeName</b>, multiplicity=(1,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<java.lang.String> getQualifiedTypeName();

}

