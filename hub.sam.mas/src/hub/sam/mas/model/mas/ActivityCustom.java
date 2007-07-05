package hub.sam.mas.model.mas;

import hub.sam.mas.execution.VariableAssignment;
import hub.sam.mas.model.petrinets.Place;
import hub.sam.mas.model.petrinets.PlaceInstance;
import hub.sam.mof.util.ListImpl;
import cmof.common.ReflectiveCollection;

public class ActivityCustom extends ActivityDlg {

	/**
	 * Returns all places of an activity. Each action with an incomming (control flow) edge is a place;
	 * each input pin with an incoming (object flow) edge is a place; each expansion region with an incomming control flow is a place;
	 * each InExpasionNode is a place; all the places within an expansion regions, and the deeper expansion regions
	 * are places; each control node with an incomming (control flow) edge is a place.
	 */
	@Override
	public ReflectiveCollection<? extends ASPlace> getPlaces() {
		ReflectiveCollection<ASPlace> result = new ListImpl<ASPlace>();
		for(ActivityNode node: self.getNode()) {
			if ((node instanceof Action || node instanceof ControlNode) && node.getIncoming().size() > 0) {
				result.add(node);
			}
			if (node instanceof InitialNode) {
				((InitialNode)node).setInitialTokesn(1);
				result.add(node);
			}
			if (node instanceof Action) {
				result.addAll(((Action)node).getInput());
				/*
				for (InputPin input: ((Action)node).getInput()) {
					if (input.getIncoming().size() > 0) {
						result.add(input);
					}
				}
				*/
			}
			if (node instanceof DecisionNode) {
				result.addAll(((DecisionNode)node).getContext());
			}
			if (node instanceof ExpansionRegion) {
				result.addAll(((ExpansionRegion)node).getInputElement());
				result.addAll(((ExpansionRegion)node).getPlaces());
			}			
		}
		return result;
	}

	@Override
	public ReflectiveCollection<? extends ASTransition> getTransitions() {
		ReflectiveCollection<ASTransition> result = new ListImpl<ASTransition>();
		for(ActivityNode node: self.getNode()) {
			if (node instanceof Action || node instanceof ControlNode) {
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * Instantiate an activtiy. It also instancitates all places in sub-activities in contained expansion nodes,
	 * because they are also places of the containing net.
	 */
	@Override
	public ActivityInstance instantiate() {
		ActivityInstance result = null;
		if (self instanceof ExpansionRegion) {
			result = ((ExpansionRegion)self).metaCreateExpansionRegionInstance();
		} else {
			result = self.metaCreateActivityInstance();
		}
		for (Place place: self.getPlaces()) {
			PlaceInstance placeInstance = null;
			if (place instanceof InExpansionNode) {
				placeInstance = ((InExpansionNode)place).metaCreateInExpansionNodeInstance();
			} else if (place instanceof InputPin) {				
				placeInstance = ((InputPin)place).metaCreatePinInstance();
			} else {
				placeInstance = place.metaCreatePlaceInstance();
			}
			if (place instanceof InitialNode) {
				placeInstance.setTokens(1);
			} else {
				placeInstance.setTokens(0);
			}
			result.setPlaces(place, placeInstance);					
		}		
		result.setVariableAssignment(new VariableAssignment());		
		/*
		for (ActivityNode node: self.getNode()) {
			if (node instanceof ExpansionRegion) {
				ExpansionRegion region = (ExpansionRegion)node;
				ExpansionRegionInstance regionInstance = (ExpansionRegionInstance)region.instantiate();
				result.getInnerActivities().add(regionInstance);
				boolean first = true;
				for (InExpansionNode inNode: region.getInputElement()) {
					if (first) {
						regionInstance.setExpansionRegionInput((InExpansionNodeInstance)result.getPlaces(inNode));
					} else {
						throw new SemanticException("Mulitple InExpansionNodes not supported yet.");
					}
				}
			}
		}
		*/
		return result;
	}
}
