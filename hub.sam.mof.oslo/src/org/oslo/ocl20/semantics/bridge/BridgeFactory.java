package org.oslo.ocl20.semantics.bridge;

public interface BridgeFactory {
	/** Build a Property */
	Property buildProperty(Classifier ret, String op_name);

	/** Build an Operation */ 
	Operation buildOperation(Classifier ret, String op_name, Classifier params[]);

	/** Build a NamedElement */ 
	NamedElement buildNamedElement(String name, ModelElement referredElement, Boolean mayBeImplicit);

	/** Build an Environment */ 
	Environment buildEnvironment();
	
	OclModelElementType buildOclModelElementType(Object o);

	EnumerationType buildEnumeration(Object o);

	EnumLiteral buildEnumLiteral(Object o);

	Classifier buildClassifier(Object o);
	
	ModelElement buildModelElement(Object o);

	Namespace buildNamespace(Object o);

	Property buildProperty(Object o);
}
