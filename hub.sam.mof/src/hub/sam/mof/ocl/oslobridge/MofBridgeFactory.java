package hub.sam.mof.ocl.oslobridge;

import java.util.HashMap;
import java.util.Map;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.bridge.BridgeFactoryImpl;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.EnumLiteral;
import org.oslo.ocl20.semantics.bridge.EnumerationType;
import org.oslo.ocl20.semantics.bridge.ModelElement;
import org.oslo.ocl20.semantics.bridge.Namespace;
import org.oslo.ocl20.semantics.bridge.OclModelElementType;
import org.oslo.ocl20.semantics.bridge.Operation;
import org.oslo.ocl20.semantics.bridge.Property;

import cmof.DataType;
import cmof.Enumeration;
import cmof.EnumerationLiteral;
import cmof.PrimitiveType;
import cmof.UmlClass;
import core.primitivetypes.Boolean;
import core.primitivetypes.Integer;
import core.primitivetypes.String;
import core.primitivetypes.UnlimitedNatural;

public class MofBridgeFactory extends BridgeFactoryImpl {

	public MofBridgeFactory(OclProcessor proc) {
		super(proc);
	}

	public Property buildProperty(Classifier ret, java.lang.String op_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Operation buildOperation(Classifier ret, java.lang.String op_name,
			Classifier[] params) {
		Operation oper = new MofOperationImpl(null, super.processor);
		oper.setName(op_name);
		oper.setReturnType(ret);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				Classifier paramType = params[i];
				//oper.getParameterNames().add(paramName);
				oper.getParameterTypes().add(paramType);
			}
		}
		return oper;
	}
	
	Map modelElementTypes = new HashMap();
	
	public void resetModelElementType() {
		modelElementTypes.clear(); 
	}
	
	@SuppressWarnings("unchecked")
	public OclModelElementType buildOclModelElementType(cmof.Classifier ecls) {
		OclModelElementType t = (OclModelElementType)modelElementTypes.get(ecls);
		if (t == null) {
			t = new MofOclModelElementTypeImpl(ecls, super.processor);
			modelElementTypes.put(ecls,t);
		}
		return t; 
	}

	public OclModelElementType buildOclModelElementType(Object o) {
		if (o instanceof UmlClass)
			return buildOclModelElementType((UmlClass)o);
		else
			return null;
	}

	public EnumerationType buildEnumeration(Object o) {
		if (o instanceof Enumeration)
			return buildEnumeration((Enumeration)o);
		else {
			return null;
		}
	}

	public EnumLiteral buildEnumLiteral(Object o) {
		if (o instanceof EnumerationLiteral)
			return buildEnumLiteral((EnumerationLiteral)o, buildEnumeration(((EnumerationLiteral)o).getEnumeration()));
		else {
			return null;
		}
	}

	//private Map<cmof.Classifier, Classifier> classifierCache = new HashMap<cmof.Classifier, Classifier>();
	public Classifier buildClassifier(Object o) {
		return (Classifier) buildModelElement(o);
		/*
		if (o instanceof cmof.Classifier) {
			cmof.Classifier classifier = (cmof.Classifier)o;		
			Classifier result = classifierCache.get(classifier);
			if (result == null) {
				result = new MofClassifierImpl(processor);
				result.setName(classifier.getName());
				result.setNamespace(buildNamespace(classifier.getPackage()));
				if (classifier instanceof UmlClass) {
					for (NamedElement feature: classifier.getMember()) {
						if (feature instanceof cmof.Property) {
							if (feature.getName() != null && feature.getOwner() instanceof UmlClass) {
								result.addProperty(buildProperty(feature));
							}
						}
					}
				} else {
					for (Feature feature: classifier.getFeature()) {
						if (feature instanceof cmof.Property) {
							if (feature.getName() != null && feature.getOwner() instanceof UmlClass) {
								result.addProperty(buildProperty(feature));
							}
						}
					}
				}
			}
			return result;
		} else if (o instanceof Element){
			throw new AssertionException();			
		} else {
			return null;
		}*/
	}

	public ModelElement buildModelElement(Object o) {
		if (o instanceof UmlClass) {
			return buildOclModelElementType((UmlClass)o);
		} else if (o instanceof Enumeration) {
			return buildEnumeration((Enumeration) o);
		} else if (o instanceof PrimitiveType) {
			DataType dt = (DataType)o;
			java.lang.String name = dt.getName();
			if (name.equals(String.class.getSimpleName())) {
				return super.processor.getTypeFactory().buildStringType();
			} else if (name.equals(Integer.class.getSimpleName())) {
				return super.processor.getTypeFactory().buildIntegerType();
			} else if (name.equals(Boolean.class.getSimpleName())) {
				return super.processor.getTypeFactory().buildBooleanType();
			} else if (name.equals(UnlimitedNatural.class.getSimpleName())) {
				return super.processor.getTypeFactory().buildIntegerType();
			} else {
				return buildOclModelElementType(dt); 
			}
		}
		return null;
	}
	
	public EnumerationType buildEnumeration(Enumeration enumeration) {
		return new MofEnumerationImpl(enumeration, super.processor);
	}
	
	public EnumLiteral buildEnumLiteral(EnumerationLiteral eenumLit, EnumerationType enumerationType) {
		return new MofEnumerationLiteralImpl(eenumLit, enumerationType);
	}

	private final Map<cmof.Package,Namespace> nsCache = new HashMap<cmof.Package,Namespace>();
	public Namespace buildNamespace(Object o) {
		cmof.Package pkg = (cmof.Package)o;
		Namespace result = nsCache.get(pkg);
		if (result == null) {
			result = new MofNamespaceImpl(pkg, processor);
			nsCache.put(pkg, result);
		}
		return result;
	}

	public Property buildProperty(Object o) {		
		return new MofPropertyImpl((cmof.Property)o, processor);
	}

}
