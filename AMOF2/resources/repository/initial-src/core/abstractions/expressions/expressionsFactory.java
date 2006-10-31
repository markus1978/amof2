package core.abstractions.expressions;


public interface expressionsFactory extends cmof.reflection.Factory {

    public core.abstractions.expressions.Expression createExpression();

    public core.abstractions.expressions.OpaqueExpression createOpaqueExpression();

}

