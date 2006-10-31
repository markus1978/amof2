package hub.sam.mof.as;

import as.Activity;
import cmof.Constraint;
import cmof.Feature;
import cmof.Operation;
import cmof.Property;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.ImplementationsImpl;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AsImplementations extends ImplementationsImpl {

	private Map<Feature, AsBehavior> behaviors = new HashMap<Feature, AsBehavior>();
	private Set<Object> hasNoBehavior = new HashSet<Object>();
	private final AsExecutionEnvironment environment;

	public AsImplementations(List<ObjectDlg> delegates, AsExecutionEnvironment environment) {
		super(delegates, null);
		this.environment = environment;
	}

	@Override
	public boolean hasImplementationFor(Operation operation, ClassifierSemantics<Property, Operation, String> semantics) {
        return getBehaviorFor(operation) != null;
	}

	@Override
	public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
        return getBehaviorFor(property) != null;
	}

	private AsBehavior getBehaviorFor(Feature feature) {
		if (hasNoBehavior.contains(feature)) {
			return null;
		} else {
			AsBehavior behavior = behaviors.get(feature);
			if (behavior == null) {
				if (feature instanceof Operation) {
					if (((Operation)feature).getBodyCondition() != null) {
						behavior = new AsQuery(((Operation)feature).getBodyCondition());
					}
				}
				if (behavior == null) {
					for (Object element: feature.getOwnedElement()) {
						if (element instanceof Activity) {
							behavior = new AsActivity((Activity)element);
						} else if (element instanceof Constraint) {
							behavior = new AsQuery((Constraint)element);
						}
					}
				}
				if (behavior == null) {
					hasNoBehavior.add(feature);
					return null;
				} else {
					behaviors.put(feature, behavior);
					return behavior;
				}
			} else {
				return behavior;
			}
		}
	}

	@Override
	public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args, ClassifierSemantics<Property, Operation, String> semantics) {
        return getBehaviorFor(operation).invoke(object, args, environment);
	}

	@Override
	public Object invokeImplementationFor(Property property, cmof.reflection.Object object, ClassifierSemantics<Property, Operation, String> semantics) {
	    return getBehaviorFor(property).invoke(object, null, environment);
	}
}
