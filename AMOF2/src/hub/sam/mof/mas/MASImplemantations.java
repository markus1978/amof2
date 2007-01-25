package hub.sam.mof.mas;

import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.petrinets.Place;
import hub.sam.mof.reflection.ImplementationsImpl;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.List;
import java.util.Map;

import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;

public class MASImplemantations extends ImplementationsImpl {
	
	private final Map<String, Activity> activities;
	private final ExecutionEnvironment env;
	
	public MASImplemantations(List<ObjectDlg> delegates, Map<String, Activity> activities, ExecutionEnvironment env) {
		super(delegates, null);
		this.activities = activities;
		this.env = env;
	}

	@Override
	public boolean hasImplementationFor(Operation operatoin, ClassifierSemantics<Property, Operation, String> semantics) {
		Activity activity = activities.get(operatoin.getQualifiedName());
		return activity != null;
	}

	@Override
	public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
		return false;
	}

	@Override
	public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args, 
			ClassifierSemantics<Property, Operation, String> semantics) {
		Activity activity = activities.get(operation.getQualifiedName());
		ActivityInstance activtiyInstance = activity.instantiate();
		activtiyInstance.setOclContext(object);
		activtiyInstance.setEnv(env);
		int i = 0;
		for (Parameter param: operation.getFormalParameter()) {
			if (param.getDirection() != ParameterDirectionKind.RETURN) {
				String paramName = param.getName();				
				for (ActivityNode node: activity.getNode()) {
					if (node instanceof ValueNode && paramName.equals(((ValueNode)node).getName())) {
						for(ActivityEdge edge: node.getOutgoing()) {
							activtiyInstance.getPlaces((Place)edge.getTarget()).setTokens(1);
							((PinInstance)activtiyInstance.getPlaces((Place)edge.getTarget())).setValue(args[i]);
						}
					}
				}
				i++;
			}
		}
		activtiyInstance.run();
		activtiyInstance.delete();
		return null; // TODO return parameters
	}

	@Override
	public Object invokeImplementationFor(Property property, cmof.reflection.Object object, ClassifierSemantics<Property, Operation, String> semantics) {
		return null;
	}

}
