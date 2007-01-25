package hub.sam.mof.as.actions;

import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.mas.ExecutionEnvironment;

import java.util.List;

import as.Action;
import cmof.Property;
import cmof.common.ReflectiveCollection;


public class WriteStructuralFeatureValueAction extends StructuralFeatureAction {

	@Override
	protected boolean requiresCollectionType() {
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void invoke(Action action, List in, List out, Object context, ExecutionEnvironment environment, AsExecutionFrame frame) {
		setAction(action);
		Property featureToWrite = getFeature(((cmof.reflection.Object)context).getMetaClass());
		checkArguments(in);
		Object value = in.get(0);
		((ReflectiveCollection)((cmof.reflection.Object)context).get(featureToWrite)).add(value);
	}
}
