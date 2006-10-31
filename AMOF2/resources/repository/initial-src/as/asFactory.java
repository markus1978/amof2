package as;


public interface asFactory extends cmof.reflection.Factory {

    public as.ForkNode createForkNode();

    public as.ContextPin createContextPin();

    public as.InputPin createInputPin();

    public as.Pin createPin();

    public as.GuardSpecification createGuardSpecification();

    public as.ValueNode createValueNode();

    public as.Activity createActivity();

    public as.MergeNode createMergeNode();

    public as.OpaqueAction createOpaqueAction();

    public as.OutputPin createOutputPin();

    public as.JoinNode createJoinNode();

    public as.ControlFlow createControlFlow();

    public as.FinalNode createFinalNode();

    public as.DecisionNode createDecisionNode();

    public as.InitialNode createInitialNode();

    public as.ObjectFlow createObjectFlow();

    public as.ContextExtensionPin createContextExtensionPin();

    public as.TypeString createTypeString();

}

