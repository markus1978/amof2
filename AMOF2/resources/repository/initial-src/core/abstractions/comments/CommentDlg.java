package core.abstractions.comments;


public class CommentDlg extends hub.sam.mof.reflection.ObjectDlg implements Comment
{
    protected Comment self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Comment)self;
    }

    public java.lang.String getBody() {
        return self.getBody();
    }

    public void setBody(java.lang.String value) {
        self.setBody(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getAnnotatedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getAnnotatedElement();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.comments.Comment> getOwnedComment() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.comments.Comment>)(java.lang.Object)self.getOwnedComment();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public core.abstractions.ownerships.Element getOwner() {
        return (core.abstractions.ownerships.Element)(java.lang.Object)self.getOwner();
    }

    public void setOwner(core.abstractions.ownerships.Element value) {
        self.setOwner(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
    }

}

