package concretecore.concreteabstractions.concreteexpressions;


public interface concreteexpressionsFactory extends cmof.reflection.Factory {

    public concretecore.concreteabstractions.concreteexpressions.OpaqueExpression createOpaqueExpression();

    public concretecore.concreteabstractions.concreteexpressions.ValueSpecification createValueSpecification();

    public concretecore.concreteabstractions.concreteexpressions.Expression createExpression();

}

