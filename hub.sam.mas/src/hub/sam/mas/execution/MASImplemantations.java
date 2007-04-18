package hub.sam.mas.execution;

import hub.sam.mas.management.MasContext;
import hub.sam.mas.model.mas.Activity;
import hub.sam.mas.model.mas.ActivityEdge;
import hub.sam.mas.model.mas.ActivityInstance;
import hub.sam.mas.model.mas.ActivityNode;
import hub.sam.mas.model.mas.PinInstance;
import hub.sam.mas.model.mas.ValueNode;
import hub.sam.mas.model.petrinets.Place;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.ImplementationsImpl;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.List;

import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;

public class MASImplemantations extends ImplementationsImpl {
	
	private final MasContext masContext;
	private final ExecutionEnvironment env;
	
	public MASImplemantations(List<ObjectDlg> delegates, MasContext masContext, ExecutionEnvironment env) {
		super(delegates, null);
		this.masContext = masContext;
		this.env = env;
	}

	@Override
	public boolean hasImplementationFor(Operation operation, ClassifierSemantics<Property, Operation, String> semantics) {
		return masContext.getLink(operation) != null;
	}

	@Override
	public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
		return false;
	}

	@Override
	public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args, 
			ClassifierSemantics<Property, Operation, String> semantics) {
		Activity activity = masContext.getLink(operation).getActivity();
		ActivityInstance activityInstance = activity.instantiate();
		activityInstance.setOclContext(object);
		activityInstance.setEnv(env);		
		boolean hasReturn = false;
		int i = 0;
		for (Parameter param: operation.getFormalParameter()) {
			if (param.getDirection() != ParameterDirectionKind.RETURN) {
				String paramName = param.getName();				
				for (ActivityNode node: activity.getNode()) {
					if (node instanceof ValueNode && paramName.equals(((ValueNode)node).getName())) {
						for(ActivityEdge edge: node.getOutgoing()) {
							activityInstance.getPlaces((Place)edge.getTarget()).setTokens(1);
							((PinInstance)activityInstance.getPlaces((Place)edge.getTarget())).setValue(args[i]);
						}
					}
				}
				i++;
			} else {
				hasReturn = true;
			}
		}
		activityInstance.run();		
		activityInstance.delete();
		if (hasReturn) {
			return activityInstance.getReturn();
		} else {
			return null;
		}
	}

	@Override
	public Object invokeImplementationFor(Property property, cmof.reflection.Object object,
	        ClassifierSemantics<Property, Operation, String> semantics) {
		return null;
	}

}
