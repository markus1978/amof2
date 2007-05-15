package hub.sam.mof.ocl.oslobridge;

import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.util.AssertionException;

import org.oslo.ocl20.generation.lib.OclAnyModelElementImpl;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.EnumLiteral;
import org.oslo.ocl20.semantics.bridge.ModelElement;
import org.oslo.ocl20.semantics.bridge.Property;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclAnyModelElement;
import org.oslo.ocl20.standard.lib.OclEnumeration;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclUndefined;
import org.oslo.ocl20.synthesis.ModelEvaluationAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class MofEvaluationAdaptor implements ModelEvaluationAdapter {

	public static List<Object> currentValue = new ArrayList<Object>();

	private final MofOclProcessor processor;
	private Map<Property, Boolean> hasExtraGetter = new HashMap<Property, Boolean>();

	public MofEvaluationAdaptor(MofOclProcessor processor) {
		this.processor = processor;
	}

	public Object getImpl(ModelElement me) {
		// TODO Auto-generated method stub
		return null;
	}

	public Class getImplClass(Classifier me) {
		return null;
	}

	public String getGetterName(Property property) {
		if (property instanceof MofAdditionalPropertyImpl) {
			int index = ((MofAdditionalPropertyImpl)property).getIndex();
			return "getOclAdditionalValue" + index;
		}

		String result = JavaMapping.mapping.getJavaGetMethodNameForProperty((cmof.Property)property.getDelegate());
		Boolean extraGetter = hasExtraGetter.get(property);
		if (extraGetter == null) {
			extraGetter = ((MofPropertyImpl)property).hasExtraGetter();
			hasExtraGetter.put(property, extraGetter);
		}
		if (extraGetter.booleanValue()) {
			return "_" + result;
		} else {
			return result;
		}
	}

	public String getSetterName(Property property) {
		return JavaMapping.mapping.getJavaSetMethodNameForProperty((cmof.Property)property.getDelegate());
	}

	public Object getEnumLiteralValue(EnumLiteral enumLit) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean OclModelElement_equalTo(OclAny o1, OclAny o2) {
		if (o1 instanceof OclUndefined || o2 instanceof OclUndefined) {
			throw new OclException("A subexpression evaluated to undefined.");
		}
		if (o1 == null || o2 == null ) {
			return false;
		}
		return ((cmof.reflection.Object)o1.asJavaObject()).equals(o2.asJavaObject());
	}

	public boolean OclModelElement_oclIsNew(OclAny o1) {
		throw new AssertionException("not implemented");
	}

	public boolean OclModelElement_oclIsUndefined(OclAny o1) {        
        return o1 == null;        
	}

	public Object OclModelElement_oclAsType(OclAny obj, OclType type) {
		if (obj instanceof OclAnyModelElement) {
			return obj;
		}
		return null;
	}

	public boolean OclModelElement_oclIsTypeOf(OclAny obj, OclType type) {
		if (obj instanceof OclAnyModelElement && type.asJavaObject() instanceof MofOclModelElementTypeImpl) {
			return ((cmof.reflection.Object)((OclAnyModelElement)obj).asJavaObject()).getMetaClass().equals(
					((MofOclModelElementTypeImpl)type.asJavaObject()).getImplementation());
		} else {
			return false;
		}
	}

	public boolean OclModelElement_oclIsKindOf(OclAny obj, OclType type) {
		if (obj instanceof OclAnyModelElement && type.asJavaObject() instanceof MofOclModelElementTypeImpl) {
			return ((cmof.reflection.Object)((OclAnyModelElement)obj).asJavaObject()).getMetaClass().equals(
					((MofOclModelElementTypeImpl)type.asJavaObject()).getImplementation()) ||
					((cmof.reflection.Object)((OclAnyModelElement)obj).asJavaObject()).getMetaClass().allParents().contains(
					((MofOclModelElementTypeImpl)type.asJavaObject()).getImplementation());
		} else {
			return false;
		}
	}

	public OclSet OclType_allInstances(OclType o1) {
		throw new AssertionException("not implemented");
	}

	public OclType OclModelElement_oclType(OclAnyModelElement impl) {
		Object o = impl.asJavaObject();

		if (o instanceof cmof.reflection.Object) {
			Classifier type = processor.getBridgeFactory().buildClassifier(((cmof.reflection.Object)o).getMetaClass());
			return processor.getStdLibAdapter().Type(type);
		} else if (o.getClass().isEnum()) {
			Classifier type = processor.getBridgeFactory().buildEnumeration(processor.getEnumerations().get(o.getClass()));
			return processor.getStdLibAdapter().Type(type);
		} else {
			throw new AssertionException("not implemented");
		}
	}

	public boolean EnumLiteral_equalTo(OclEnumeration e1, OclAny e2) {
		if (e2 instanceof OclEnumeration) {
            Object o1 = e1;
            while(o1 instanceof OclAnyModelElement) {
                o1 = ((OclAnyModelElement)o1).asJavaObject();
            }

            Object o2 = e2;
            while(o2 instanceof OclAnyModelElement) {
                o2 = ((OclAnyModelElement)o2).asJavaObject();
            }

            return o1.equals(o2);
		} else {
			return false;
		}
	}

	public boolean EnumLiteral_oclIsNew(Object o1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean EnumLiteral_oclIsUndefined(Object o1) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object EnumLiteral_oclAsType(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean EnumLiteral_oclIsTypeOf(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean EnumLiteral_oclIsKindOf(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return false;
	}

	public Set EnumLiteral_allInstances(Object o1) {
		// TODO Auto-generated method stub
		return null;
	}
}
