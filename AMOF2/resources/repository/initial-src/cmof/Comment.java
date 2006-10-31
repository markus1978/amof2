package cmof;


/**
 * <b>Comment</b>, superClass = {core.abstractions.comments.Comment, cmof.Element}
 */
public interface Comment extends core.abstractions.comments.Comment, cmof.Element
{

    /**
     * <b>annotatedElement</b>, multiplicity=(0,*), isUnique, redefinedProperty = {core.abstractions.comments.Comment.annotatedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getAnnotatedElement();

}

