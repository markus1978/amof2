package org.oslo.ocl20.standard.types;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.BridgeFactory;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.model.types.StringType;
import org.oslo.ocl20.semantics.model.types.TypeFactory;
import org.oslo.ocl20.standard.lib.OclString;


/**
 * @author dha
 *
 */
public class StringTypeImpl extends PrimitiveImpl implements StringType {
	/** Construct a StringType */
	public StringTypeImpl(OclProcessor proc) {
		super(proc);
		setName("String");
	}

	public void createOperations(TypeFactory tf) {
		BridgeFactory bf = processor.getBridgeFactory();
		//--- Add operations for STRING_TYPE ---
		getOperations().add(bf.buildOperation(tf.buildIntegerType(), "size", new Classifier[] {}));
		getOperations().add(bf.buildOperation(tf.buildStringType(), "concat", new Classifier[] { tf.buildStringType(), }));
		getOperations().add(bf.buildOperation(tf.buildStringType(), "substring", new Classifier[] { tf.buildIntegerType(), tf.buildIntegerType() }));
		getOperations().add(bf.buildOperation(tf.buildIntegerType(), "toInteger", new Classifier[] {}));
		getOperations().add(bf.buildOperation(tf.buildRealType(), "toReal", new Classifier[] {}));
		
		super.createOperations(tf);
	}
	/** Check if this (a String) conforms with t2 */
	public Boolean conformsTo(Classifier t2) {
		return TypeConformance.isAssignableTo(this, t2) ? Boolean.TRUE : Boolean.FALSE;
	}

	/** Accept a Semantic Visitor */ 
	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this,obj);
	}
	
	/** ToString */
	public String toString(){
		return "String";
	}

	public Object getDelegate() {
		return java.lang.String.class;
	}

	public Class getImplClass() {
		return OclString.class;
	}
}
