package hub.sam.mas.model.mas;

import hub.sam.mof.util.ListImpl;
import cmof.common.ReflectiveCollection;

public class ActionCustom extends ActionDlg {

	@Override
	public ReflectiveCollection<? extends ASPlace> getInputPlaces() {
		 ReflectiveCollection<? extends ASPlace> result = ((ASTransition)getSuper(ASTransition.class)).getInputPlaces();
		 for(InputPin pin: self.getInput()) {
			 if (pin.getIncoming().size() > 0) {
				 result.add(pin);		 
			 }
		 }		 
		 return result;
	}

	@Override
	public ReflectiveCollection<? extends ASPlace> getOutputPlaces() {
		ReflectiveCollection<ASPlace> result = new ListImpl<ASPlace>();
		for (ActivityEdge edge: self.getOutgoing()) {
			result.add(edge.getTarget());
		}
		for (OutputPin output: self.getOutput()) {
			for (ActivityEdge edge: output.getOutgoing()) {
				ActivityNode target = edge.getTarget();
				if (target instanceof InputPin) {
					result.add(target);
				} else if (target instanceof ValueNode) {
					for (ActivityEdge outEdge: target.getOutgoing()) {
						result.add(outEdge.getTarget());
					}
				}
			}
		}
		return result;
	}	

	
}
