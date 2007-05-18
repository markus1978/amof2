package org.oslo.ocl20.semantics.bridge;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OclAnyType;
import org.oslo.ocl20.semantics.model.types.TypeFactory;
import org.oslo.ocl20.standard.lib.OclType;



public class ClassifierImpl 
implements 
	Classifier 
{
	/** Construct a Classifier */
	public ClassifierImpl(OclProcessor proc) {
		this.processor = proc;
	}

	protected OclProcessor processor;

	/** Properties field */
	protected List properties = new Vector();
	/** Get Properties field */
	public List getProperties() {
		return properties;
	}
	/** Set Properties field */
	public void setProperties(List properties) {
		this.properties = properties;
	}
	public void addProperty(Property p) {
		properties.add(p);
	}
	
	
	public Property localLookupProperty(String name) {
		Iterator i = getProperties().iterator();
		while (i.hasNext()) {
			Property p = (Property) i.next();
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}	
	
	/** Search for a property with a given name */
	public Property lookupProperty(String name) {
		Property pr = localLookupProperty(name);
		if (pr!= null) return pr;
		//if still not found, look in super types
		Iterator i = this.getAllSuperTypes().iterator();
		while (i.hasNext()) {
			Classifier c = (Classifier) i.next();
			Property p = c.localLookupProperty(name);
			if (p!=null)
				return p;
		}
		return null;
	}

	public List getAllSuperTypes() {
		List result = new Vector();
		List x = this.getSuperTypes();
		result.addAll(x);
		Iterator i = x.iterator();
		while (i.hasNext()) {
			Classifier c = (Classifier)i.next();
			Iterator j = c.getAllSuperTypes().iterator();
			while (j.hasNext()) {
				Classifier st = (Classifier)j.next();
				if (!result.contains(st)) {
					result.add(st);
				}
			}
		}
		return result;
	}
	
	public List getSuperTypes() {
		return new Vector();
	}
	public void createOperations(TypeFactory tf) {
	}
	
	/** Operations field */
	protected List operations = new Vector();
	/** Get Operations field */
	public final List getOperations() {
		return operations;
	}
	/** Set Operations field */
	public final void setOperations(List operations) {
		this.operations = operations;
	}
	public final void addOperation(Operation op) {
		operations.add(op);
	}
	/** Search for an operation with a given name and a list of parameter types */
	public Operation lookupOperation(String name, List types) {
		Operation op = lookupCachedOp(name, types);
		return op;
	}

	/** Search for an operation in local operations */
	protected Operation lookupCachedOp(String name, List types) {
		Iterator i = getOperations().iterator();
		while (i.hasNext()) {
			Operation op = (Operation) i.next();
			String opName = op.getName(); 
			if (opName.equals(name) &&
			        typesConform(op.getParameterTypes(), types))
				return op;
		}
		//if still not found, look in super types
		i = this.getSuperTypes().iterator();
		while (i.hasNext()) {
			Classifier c = (Classifier) i.next();
			Operation p = c.lookupOperation(name, types);
			if (p!=null)
				return p;
		}
		return null;
	}

	/** Check if a list of argument types conforms to a list of paramter types */ 
	protected boolean typesConform(List paramTypes, List argTypes) {
	    if (paramTypes==null) paramTypes=new Vector();
	    if (argTypes==null) argTypes = new Vector();
		if (paramTypes.size() != argTypes.size())
			return false;
		for (int i = 0; i < paramTypes.size(); i++) {
			Classifier paramType = (Classifier) paramTypes.get(i);
			Classifier argType = (Classifier) argTypes.get(i);
			if (argType == null)
				return false;
			if (argType.conformsTo(paramType) == Boolean.FALSE)
				return false;
		}
		return true;
	}

	/** Check if this (a Classifier) conforms to c */
	public Boolean conformsTo(Classifier c) {
		if (this.equals(c))
			return Boolean.TRUE;
		if (c.getClass() == OclAnyType.class && !(this instanceof CollectionType))
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	/** Search for a given signal */
	public Signal lookupSignal(String name, List types) {
		return null;
	}

	public String getFullName(String sep) {
		if (getNamespace() == null)
		    return getName();
		return getNamespace().getFullName(sep)+sep+getName();
	}

	/** Name field */
	protected String name = null;
	/** Get Name field */
	public String getName() {
		return name;
	}
	/** Set Name field */
	public void setName(String name) {
		this.name = name;
	}

	/** Namespace field */
	Namespace namespace;
	/** Get Namespace field */
	public Namespace getNamespace() {
		return namespace;
	}
	/** Set Namespace field */
	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}

	/** Get the current environment without parent */ 
	public Environment getEnvironmentWithoutParents() {
		Environment env = this.processor.getBridgeFactory().buildEnvironment();
		env.addNamespace(this);
		env.setParent(null);
		return env;
	}

	/** Get the current environment with parent */ 
	public Environment getEnvironmentWithParents() {
		if (this.getNamespace() == null) {
			return null;
		} else {
			Environment result = getEnvironmentWithoutParents();
			result.setParent(this.getNamespace().getEnvironmentWithParents());
			return result;
		}
	}

	/** Search for an owned element */ 
	public ModelElement lookupOwnedElement(String name) {
		return null;
	};

	/** Accept a Semantic visitor */
	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this,obj);
	}

	/** Clone */
	public Object clone() {
		return null;
	}
	public Object getDelegate() {
		return java.lang.Class.class;
	}
	public Class getImplClass() {
		return OclType.class;
	}
	
	/*
	public int hashCode() {return 0;}
	public boolean equals(Object o2) {
		if (o2 instanceof Classifier) {
			Classifier cls2 = (Classifier)o2;
			boolean name = (this.getName() == null) ? cls2.getName()==null : this.getName().equals( cls2.getName() );
			boolean namespace = (this.getNamespace() == null) ? cls2.getNamespace()==null : this.getNamespace().equals( cls2.getNamespace() );
			return  name && namespace;
		} else
			return false;
	}
	*/
}
