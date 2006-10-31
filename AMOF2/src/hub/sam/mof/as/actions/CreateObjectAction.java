package hub.sam.mof.as.actions;

import as.Action;
import cmof.Classifier;
import cmof.Enumeration;
import cmof.PrimitiveType;
import cmof.Type;
import cmof.UmlClass;
import core.primitivetypes.Boolean;
import core.primitivetypes.Integer;
import core.primitivetypes.UnlimitedNatural;
import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.as.AsSemanticException;
import hub.sam.mof.ocl.MofEnumerationImpl;
import hub.sam.mof.ocl.MofOclModelElementTypeImpl;
import hub.sam.mof.util.AssertionException;
import org.oslo.ocl20.semantics.bridge.ModelElement;
import org.oslo.ocl20.semantics.model.types.BooleanType;
import org.oslo.ocl20.semantics.model.types.RealType;
import org.oslo.ocl20.semantics.model.types.StringType;
import org.oslo.ocl20.standard.types.PrimitiveImpl;

import java.util.Arrays;
import java.util.List;

public class CreateObjectAction extends AbstractAction {

	private static int unique = 0;

	protected Classifier getClassifierToCreate(AsAnalysisEnvironment environment) {
		String body = getAction().getBody().get(1);
		ModelElement oclModelElementTypeToCreate = null;
		if (body.contains("::")) {
			oclModelElementTypeToCreate = environment.getOclEnvironment().lookupPathName(Arrays.asList(body.split("::")));
		} else {
			oclModelElementTypeToCreate = environment.getOclEnvironment().lookup(body);
		}
		String errorPrefix = "Action " + toString() + " ";
		Type requiredType = getAction().getOutput().get(0).getType();
		if (oclModelElementTypeToCreate instanceof MofOclModelElementTypeImpl || oclModelElementTypeToCreate instanceof MofEnumerationImpl) {
			Object mofDelegate = null;
			if (oclModelElementTypeToCreate instanceof MofOclModelElementTypeImpl) {
				mofDelegate = ((MofOclModelElementTypeImpl)oclModelElementTypeToCreate).getMofDelegate();
			} else {
				mofDelegate = ((MofEnumerationImpl)oclModelElementTypeToCreate).getMofDelegate();
			}
			if (!requiredType.equals(mofDelegate)) {
				throw new AsSemanticException(errorPrefix + "has wrong type.");
			} else {
				if (!(mofDelegate instanceof Classifier || mofDelegate instanceof Enumeration)) {
					throw new AsSemanticException(errorPrefix + "has a type that is not a classifier.");
				} else {
					return (Classifier)((MofOclModelElementTypeImpl)oclModelElementTypeToCreate).getMofDelegate();
				}
			}
		} else if (oclModelElementTypeToCreate instanceof PrimitiveImpl) {
			if (oclModelElementTypeToCreate instanceof BooleanType) {
				if (!requiredType.getName().equals(Boolean.class.getSimpleName())) {
					throw new AsSemanticException(errorPrefix + "has wrong type.");
				} else {
					return environment.getBooleanType();
				}
			} else if (oclModelElementTypeToCreate instanceof StringType) {
				if (!requiredType.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
					throw new AsSemanticException(errorPrefix + "has wrong type.");
				} else {
					return environment.getStringType();
				}
			} else if (oclModelElementTypeToCreate instanceof RealType) {
				if (!requiredType.getName().equals(Integer.class.getSimpleName()) && !requiredType.getName().equals(UnlimitedNatural.class.getSimpleName())) {
					throw new AsSemanticException(errorPrefix + "has wrong type.");
				} else {
					if (requiredType.getName().equals(Integer.class.getSimpleName())) {
						return environment.getIntegerType();
					} else {
						return environment.getUnlimitedNaturalType();
					}
				}
			} else {
				throw new AsSemanticException(errorPrefix + "has unknown type.");
			}
		} else {
			throw new AsSemanticException(errorPrefix + "has unknown type.");
		}
	}

	private boolean isUnique() {
		if (getAction().getBody().size() == 3) {
			if (!new String("unique").equals(getAction().getBody().get(2))) {
				throw new AsSemanticException("Action " + toString() + " has unknown argument unique.");
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public void staticSemantics(Action action, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException {
		setAction(action);
		checkArgumentCounts(-1,0,1,false);
		if (!(getAction().getBody().size() == 2 || getAction().getBody().size() == 3)) {
			throw new AsSemanticException("Action " + toString() + " has wrong number of arguments.");
		}
		if (isUnique() && !(getClassifierToCreate(environment).equals(environment.getIntegerType()))) {
			throw new AsSemanticException("Action " + toString() + " has wrong type for unique.");
		}
		getClassifierToCreate(environment);
	}

	@Override
	public void invoke(Action action, List in, List<Object> out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame) {
		Object result = null;
		setAction(action);
		Classifier classifier = getClassifierToCreate(environment);
		if (classifier instanceof UmlClass) {
			result = environment.createElement((UmlClass)classifier);
		} else if (classifier instanceof PrimitiveType) {
			if (classifier.equals(environment.getStringType())) {
				result = new java.lang.String();
			} else if (classifier.equals(environment.getBooleanType())) {
				result = new java.lang.Boolean(true);
			} else if (classifier.equals(environment.getIntegerType())) {
				if (isUnique()) {
					result = new java.lang.Integer(unique++);
				} else {
					result = new java.lang.Integer(0);
				}
			} else if (classifier.equals(environment.getUnlimitedNaturalType())) {
				result = new java.lang.Long(0);
			} else {
				throw new AssertionException("not implemented.");
			}
		} else {
			throw new AssertionException("not implemented.");
		}
		out.add(result);
	}
}
