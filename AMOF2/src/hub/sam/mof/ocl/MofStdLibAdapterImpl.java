package hub.sam.mof.ocl;

import java.util.Iterator;

import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.util.AssertionException;
import hub.sam.mof.util.TypeWrapperSetImpl;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.standard.lib.OclEnumerationImpl;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.EnumerationType;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OclAnyType;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclAnyModelElement;
import org.oslo.ocl20.standard.lib.OclEnumeration;
import org.oslo.ocl20.standard.lib.StdLibAdapterImpl;
import org.oslo.ocl20.standard.types.OrderedSetTypeImpl;

import com.sun.org.apache.xalan.internal.xsltc.ProcessorVersion;

import cmof.Enumeration;
import cmof.EnumerationLiteral;
import cmof.common.ReflectiveCollection;

/*
 * This class is in normal oslo bridges not necessary, but aMOF2 does not use java.util for its collections.
 * Thus, when oslo is checking for a collection using "instanceof java.util.*", the standard StdLibAdaptorImpl
 * fails.
 */
public class MofStdLibAdapterImpl extends StdLibAdapterImpl {

	private final OclProcessor processor;
	
	public MofStdLibAdapterImpl(OclProcessor proc) {
		super(proc);
		this.processor = proc;
	}

	@SuppressWarnings("unchecked")
	private Object[] toArray(ReflectiveCollection col) {		
		int size = col.size();		
		if (size == 0) {
			return new Object[] {};
		} else {
			Object[] result = new Object[size+1];
			Iterator it = col.iterator();
			for (int i = 0; i < size; i++) {
				result[i] = it.next();
			}
			result[result.length-1] = result[0];
			return result;
		}
	}
	
	@Override
	public OclAny OclAny(Classifier type, Object obj) {
		if (type == null) return this.Undefined();
		if (obj instanceof ReflectiveCollection) {			
			return oclCollection(obj);
		} else if (obj instanceof OclAnyModelElement && ((OclAny)obj).asJavaObject().getClass().isEnum()) {
			Object javaObject = ((OclAny)obj).asJavaObject();
			Enumeration enumeration = ((MofOclProcessor)processor).getEnumerations().get(javaObject.getClass());
			return super.Enumeration(processor.getBridgeFactory().buildEnumeration(enumeration),javaObject);						
		} else {		
			return super.OclAny(type, obj);
		}
	}
	
	@SuppressWarnings("unchecked")
	private OclAny oclCollection(Object o) {
		Object untypedSet = ((TypeWrapperSetImpl)o).getUnypedSet();
		if (untypedSet instanceof MofOclSetImpl) {
			return ((MofOclSetImpl)untypedSet).getOclCollection();
		} else if (untypedSet instanceof MofOclListImpl) {
			return ((MofOclListImpl)untypedSet).getOclCollection();
		}
		
		cmof.Property property = ((TypeWrapperSetImpl)o).getProperty();
		//Classifier elementType = processor.getBridgeFactory().buildClassifier(property.getType());
		OclAnyType oat = processor.getTypeFactory().buildOclAnyType();
		if (property.isUnique() && property.isOrdered()) {
			return super.OrderedSet(oat, toArray(((ReflectiveCollection)o)));
		} else if (!property.isUnique() && property.isOrdered()) {
			return super.Sequence(oat, toArray(((ReflectiveCollection)o)));
		} else if (property.isUnique() && !property.isOrdered()) {
			return super.Set(oat, toArray(((ReflectiveCollection)o)));			
		} else if (!property.isUnique() && !property.isOrdered()) {
			return super.Bag(oat, toArray(((ReflectiveCollection)o)));
		} else {
			throw new AssertionException("unreachable");
		}
	}

	@Override
	public OclAny OclAny(Object obj) {				
		if (obj == null) return this.Undefined();		
		if (obj instanceof ReflectiveCollection) {
			return oclCollection(obj);
		} else if (obj instanceof OclAnyModelElement && ((OclAny)obj).asJavaObject().getClass().isEnum()) {
			Object javaObject = ((OclAny)obj).asJavaObject();
			Enumeration enumeration = ((MofOclProcessor)processor).getEnumerations().get(javaObject.getClass());
			return super.Enumeration(processor.getBridgeFactory().buildEnumeration(enumeration),javaObject);						
		} else {
			return super.OclAny(obj);
		}
	}

}
