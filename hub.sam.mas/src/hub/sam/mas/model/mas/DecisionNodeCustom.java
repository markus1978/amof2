package hub.sam.mas.model.mas;

import hub.sam.mas.execution.SemanticException;
import hub.sam.mas.model.petrinets.NetInstance;
import hub.sam.mas.model.petrinets.Transition;
import hub.sam.mof.util.ListImpl;

import java.util.HashMap;
import java.util.Map;

import cmof.common.ReflectiveCollection;

public class DecisionNodeCustom extends DecisionNodeDlg {

	@Override
	public ReflectiveCollection<? extends ASPlace> getInputPlaces() {
		 ReflectiveCollection<? extends ASPlace> result = ((ASTransition)getSuper(ASTransition.class)).getInputPlaces();
		 result.addAll(self.getContext());
		 return result;
	}

	@Override
	public ReflectiveCollection<? extends ASPlace> getOutputPlaces() {
		ReflectiveCollection<ASPlace> result = new ListImpl<ASPlace>();
		result.add(self.getSelected().getTarget());
		return result;
	}	
	
	@Override
	public void fire(NetInstance context) {		
		DebugInfo.printInfo("decision " + self.getBody());		
		ReflectiveCollection<? extends ContextPin> contextPins = self.getContext();		
		
		Map<String, Object> extensions = new HashMap<String, Object>(((ActivityInstance)context).getVariableAssignment().getExtensions()); 
		OpaqueActionCustom.evaluateInputPins(contextPins,(ActivityInstance) context, extensions, ((ActivityInstance)context).getVariableAssignment().getExtensions());
							
		Object decisionExpressionResult = OpaqueActionCustom.evaluateExpression(self.getBody(), 
				((ActivityInstance)context).getOclContext(), extensions, (ActivityInstance) context);
		for (ActivityEdge edge: self.getOutgoing()) {
			GuardSpecification guard = edge.getGuardSpecification();
			boolean foundTrueEdge = false;
			if (guard == null) {
				throw new SemanticException("outgoing decision edge without guard.");
			} else {
				ReflectiveCollection<? extends InputPin> pins = new ListImpl<InputPin>();
				InputPin guardInput = guard.getInput();
				if (guardInput != null) {
					pins.add(guardInput);
				}
				Object guardResult = 
					OpaqueActionCustom.evaluateExpression(guard.getBody(), 
							((ActivityInstance)context).getOclContext(), extensions, (ActivityInstance) context);					
				
				if (guardResult.equals(decisionExpressionResult)) {
					if (foundTrueEdge) {
						throw new SemanticException("More then one decision branch is true");
					} else {
						foundTrueEdge = true;
						self.setSelected(edge);
					}
				}
			}
		}
		((Transition)getSuper(Transition.class)).fire(context);
	}	
}
