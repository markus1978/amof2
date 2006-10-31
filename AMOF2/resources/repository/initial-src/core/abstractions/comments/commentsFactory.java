package core.abstractions.comments;


public interface commentsFactory extends cmof.reflection.Factory {

    public core.abstractions.comments.Comment createComment();

    public core.abstractions.comments.Element createElement();

}

