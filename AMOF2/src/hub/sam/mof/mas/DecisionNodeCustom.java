package hub.sam.mof.mas;

import hub.sam.mof.petrinets.NetInstance;
import hub.sam.mof.petrinets.Transition;
import hub.sam.mof.util.ListImpl;
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
		System.out.println("decision " + self.getBody());
		Object decisionExpressionResult = OpaqueActionCustom.evaluateExpression(self, self.getBody(), self.getContext(),
				(ActivityInstance) context);
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
				Object guardResult = OpaqueActionCustom.evaluateExpression(self, guard.getBody(), pins, (ActivityInstance) context);
				
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
