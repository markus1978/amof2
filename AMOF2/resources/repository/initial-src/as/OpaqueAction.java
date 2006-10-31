package as;


/**
 * <b>OpaqueAction</b>, superClass = {as.Action}
 */
public interface OpaqueAction extends as.Action
{

    /**
     * <b>body</b>, multiplicity=(1,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<java.lang.String> getBody();

    /**
     * <b>language</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<java.lang.String> getLanguage();

}

