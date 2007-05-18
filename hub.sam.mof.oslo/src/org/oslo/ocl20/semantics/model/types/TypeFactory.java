package org.oslo.ocl20.semantics.model.types;

import org.oslo.ocl20.semantics.bridge.Classifier;
/**
 * @author dha
 *
 */
public interface TypeFactory {
	Classifier buildClassifier();
	BagType buildBagType(Classifier elementType);
	BooleanType buildBooleanType();
	CollectionType buildCollectionType(Classifier elementType);
	IntegerType buildIntegerType();
	OclAnyType buildOclAnyType();
	TypeType buildTypeType(Classifier type);
	OclMessageType buildOclMessageType();
	OrderedSetType buildOrderedSetType(Classifier elementType);
	RealType buildRealType();
	SequenceType buildSequenceType(Classifier elementType);
	SetType buildSetType(Classifier elementType);
	StringType buildStringType();
	TupleType buildTupleType(String[] names, Classifier[] types);
	VoidType buildVoidType();
	Classifier getFlatType(Classifier type);
}
