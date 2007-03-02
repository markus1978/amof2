package hub.sam.mas.model.petrinets;

import hub.sam.mof.util.ListImpl;
import cmof.common.ReflectiveCollection;

public class NetInstanceCustom extends NetInstanceDlg {

	@Override
	public void run() {		
		
		ReflectiveCollection<? extends Transition> activeTransitions = self.getEnabledTransitions();		
		while(activeTransitions.size() > 0) {			
			activeTransitions.iterator().next().fire(self);			
			activeTransitions = self.getEnabledTransitions();
		}
	}
	
	@Override
	public ReflectiveCollection<? extends Transition> getEnabledTransitions() {
		ReflectiveCollection<Transition> result = new ListImpl<Transition>();
		for (Transition transition: self.getMetaClassifierNet().getTransitions()) {
			if (transition.isEnabled(self)) {
				result.add(transition);
			}
		}
		return result;
	}
}
