package hub.sam.mof.mas;


public interface masFactory extends cmof.reflection.Factory {

    public hub.sam.mof.mas.Activity createActivity();

    public hub.sam.mof.mas.InputPin createInputPin();

    public hub.sam.mof.mas.OpaqueAction createOpaqueAction();

    public hub.sam.mof.mas.DecisionNode createDecisionNode();

    public hub.sam.mof.mas.ActivityInstance createActivityInstance();

    public hub.sam.mof.mas.PinInstance createPinInstance();

    public hub.sam.mof.mas.ExpansionRegion createExpansionRegion();

    public hub.sam.mof.mas.InExpansionNodeInstance createInExpansionNodeInstance();

    public hub.sam.mof.mas.InExpansionNode createInExpansionNode();

    public hub.sam.mof.mas.ExpansionRegionInstance createExpansionRegionInstance();

    public hub.sam.mof.mas.Pin createPin();

    public hub.sam.mof.mas.ValueNode createValueNode();

    public hub.sam.mof.mas.ContextPinList createContextPinList();

    public hub.sam.mof.mas.ContextPin createContextPin();

    public hub.sam.mof.mas.InputPinList createInputPinList();

    public hub.sam.mof.mas.OutputPinList createOutputPinList();

    public hub.sam.mof.mas.OutputPin createOutputPin();

    public hub.sam.mof.mas.InExpansionNodeList createInExpansionNodeList();

    public hub.sam.mof.mas.StructuredActivityNode createStructuredActivityNode();

    public hub.sam.mof.mas.OutExpansionNodeList createOutExpansionNodeList();

    public hub.sam.mof.mas.OutExpansionNode createOutExpansionNode();

    public hub.sam.mof.mas.ExpansionRegionBody createExpansionRegionBody();

    public hub.sam.mof.mas.ObjectFlow createObjectFlow();

    public hub.sam.mof.mas.ForkNode createForkNode();

    public hub.sam.mof.mas.JoinNode createJoinNode();

    public hub.sam.mof.mas.InitialNode createInitialNode();

    public hub.sam.mof.mas.GuardSpecification createGuardSpecification();

    public hub.sam.mof.mas.ContextExtensionPin createContextExtensionPin();

    public hub.sam.mof.mas.FinalNode createFinalNode();

    public hub.sam.mof.mas.ControlFlow createControlFlow();

    public hub.sam.mof.mas.TypeString createTypeString();

    public hub.sam.mof.mas.MergeNode createMergeNode();

}

