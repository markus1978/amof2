package hub.sam.mof.mas;


public class CommentedNodeDlg extends hub.sam.mof.reflection.ObjectDlg implements CommentedNode
{
    protected CommentedNode self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (CommentedNode)self;
    }

    public java.lang.String getComment() {
        return self.getComment();
    }

    public void setComment(java.lang.String value) {
        self.setComment(value);
    }

    public java.lang.String getQualifiedName() {
        return self.getQualifiedName();
    }

    public void setQualifiedName(java.lang.String value) {
        self.setQualifiedName(value);
    }

}

