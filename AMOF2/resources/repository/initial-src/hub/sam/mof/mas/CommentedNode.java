package hub.sam.mof.mas;


/**
 * <b>CommentedNode</b>, isAbstract
 */
public interface CommentedNode extends cmof.reflection.Object
{

    /**
     * <b>comment</b>, multiplicity=(1,1)
     */
    public java.lang.String getComment();

    public void setComment(java.lang.String value);

    /**
     * <b>qualifiedName</b>, multiplicity=(1,1), isDerived
     */
    public java.lang.String getQualifiedName();

    public void setQualifiedName(java.lang.String value);

}

