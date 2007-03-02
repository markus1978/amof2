package hub.sam.mas.model.mas;

import hub.sam.mas.execution.SemanticException;
import hub.sam.mas.model.petrinets.NetInstance;
import hub.sam.mas.model.petrinets.Place;
import hub.sam.mas.model.petrinets.PlaceInstance;
import hub.sam.mas.model.petrinets.Transition;

import java.util.Collection;
import java.util.Vector;

public class ExpansionRegionCustom extends ExpansionRegionDlg {

	/**
	 * The context already contains instances for every place. Perform will create new
	 * instance of the corresponding subnet, which will contain copies of the already existing places.
	 * Through these copies the new places will be initialised with the values and tokens given
	 * from the outside activity. This instance is deleted after the first iteration and a new one
	 * is created for the next iteration.
	 */
	@Override
	public void fire(NetInstance context) {
		// find the InExpansionNode
		InExpansionNode inNode = null;
		for (InExpansionNode node: self.getInputElement()) {
			if (inNode == null) {
				inNode = node;
			} else {
				throw new SemanticException("Mulitple InExpasionNodes not supported yet.");
			}
		}
		InExpansionNodeInstance inNodeInstance = (InExpansionNodeInstance)context.getPlaces(inNode);
		
		// iterate through all values
		for(Object value: (Iterable)inNodeInstance.getValue()) {			
					
			// instantiate and initialise places
			ActivityInstance instance = self.instantiate();
			instance.setEnv(((ActivityInstance)context).getEnv());
			instance.setOclContext(((ActivityInstance)context).getOclContext());
			for (Place place: self.getPlaces()) {
				PlaceInstance outsidePlace = context.getPlaces(place);
				PlaceInstance insidePlace = instance.getPlaces(place);
				insidePlace.setTokens(outsidePlace.getTokens());
				if (insidePlace instanceof PinInstance) {					
					((PinInstance)insidePlace).setValue(((PinInstance)outsidePlace).getValue());
				} 
			}
			
			// propagate the iterator value
			Collection<ASPlace> iteratorValuePlaces = new Vector<ASPlace>();					
				for (ActivityEdge edge: inNode.getOutgoing()) {
					ActivityNode target = edge.getTarget();
					if (target instanceof InputPin) {
						iteratorValuePlaces.add((ASPlace)target);
					} else if (target instanceof ValueNode) {
						for (ActivityEdge outEdge: target.getOutgoing()) {
							iteratorValuePlaces.add((ASPlace)outEdge.getTarget());
						}
					}			
			}		
			for(Place place: iteratorValuePlaces) {
				instance.getPlaces(place).setTokens(1);
				if (place instanceof InputPin) {
					((PinInstance)instance.getPlaces(place)).setValue(value);
				}
			}
			
			// run it
			instance.run();
		
			// delete the instance
			instance.delete();
		}
		
		// TODO propagate values ?!
				
		((Transition)getSuper(Transition.class)).fire(context);
	}

	
}
