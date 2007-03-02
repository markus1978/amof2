package hub.sam.mas.model.mas;

import hub.sam.mof.util.ListImpl;
import cmof.common.ReflectiveCollection;

public class ASTransitionCustom extends ASTransitionDlg {

	@Override
	public ReflectiveCollection<? extends ASPlace> getInputPlaces() {
		ReflectiveCollection<ASPlace> result = new ListImpl<ASPlace>();
		if (((ActivityNode)self).getIncoming().size() > 0) {
			result.add(self);
		}
		return result;
	}

	@Override
	public ReflectiveCollection<? extends ASPlace> getOutputPlaces() {
		ReflectiveCollection<ASPlace> result = new ListImpl<ASPlace>();
		for (ActivityEdge edge: ((ActivityNode)self).getOutgoing()) {
			result.add(edge.getTarget());
		}
		return result;
	}
	
}
