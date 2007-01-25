package hub.sam.mof.as.actions;

import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.mas.ExecutionEnvironment;

import java.util.List;

import cmof.Property;
import cmof.UmlClass;

import as.Action;


public class WriteStructuralFeatureAction extends StructuralFeatureAction {

	@Override
	protected boolean requiresCollectionType() {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void invoke(Action action, List in, List out, Object context, ExecutionEnvironment environment, AsExecutionFrame frame) {
		setAction(action);
		Property featureToWrite = getFeature(((cmof.reflection.Object)context).getMetaClass());		
		checkArguments(in);
		Object value = in.get(0);
		((cmof.reflection.Object)context).set(featureToWrite, value);
	}
}
