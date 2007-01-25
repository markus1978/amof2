package hub.sam.mof.as.actions;

import java.util.List;

import hub.sam.mof.mas.AnalysisEnvironment;
import hub.sam.mof.mas.SemanticException;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import as.Action;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;

public abstract class StructuralFeatureAction extends AbstractAction {
	
	MofClassSemantics semantics = null;
	
	protected Property getFeature(UmlClass contextType) {
		Property feature = resolveFeature(getAction().getBody().get(1), contextType);
		return feature;
	}
	
	protected Property resolveFeature(String body, UmlClass context) {
		if (semantics == null) {
			semantics = MofClassifierSemantics.createClassClassifierForUmlClass(context);
		}
		return semantics.getProperty(body);		
	}
	
	protected abstract boolean requiresCollectionType();
	
	@Override
	public void staticSemantics(Action action, Type contextType, AnalysisEnvironment environment) throws SemanticException {
		setAction(action);
		checkArgumentCounts(1,1,0,true);
		contextType = getContextType(contextType);
		if (!(contextType instanceof UmlClass)) {
			throw new SemanticException("Context type for action " + toString() + " must be an Class");
		}
		Property feature = getFeature((UmlClass)contextType);
		if (feature == null) {
			throw new SemanticException("The denoted feature in action " + toString() + " does not exist (" + getAction().getBody().get(1) + " in " + contextType + ").");
		}
		if (!getInputType(0).conformsTo(feature.getType())) {
			throw new SemanticException("The value for action " + toString() + " does not conform to the features type (" + getInputType(0) + " vs. " + feature.getType() + ").");
		}
		boolean isCollectionProperty = MofClassifierSemantics.createClassClassifierForUmlClass((UmlClass)contextType).isCollectionProperty(feature);
		if (requiresCollectionType() && !isCollectionProperty) {
			throw new SemanticException("The action " + toString() + " is only allowed for collection features.");
		}
	}
	
	protected void checkArguments(List<Object> in) {
		if (in.get(0) == null) {
			throw new NullPointerException("Action argument is null");
		}
	}
}
