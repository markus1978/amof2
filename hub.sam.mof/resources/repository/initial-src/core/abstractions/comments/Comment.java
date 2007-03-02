package core.abstractions.comments;


/**
 * <b>Comment</b>, superClass = {core.abstractions.comments.Element}
 */
public interface Comment extends core.abstractions.comments.Element
{

    /**
     * <b>body</b>, multiplicity=(1,1)
     */
    public java.lang.String getBody();

    public void setBody(java.lang.String value);

    /**
     * <b>annotatedElement</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getAnnotatedElement();

}

