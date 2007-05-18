package org.oslo.ocl20.semantics.bridge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration$Class;


public class EnvironmentImpl implements Environment {
	/** Constructor for Environment */
	public EnvironmentImpl(org.oslo.ocl20.semantics.bridge.BridgeFactory bf) {
		super();
		bridgeFactory = bf;
	}
	public OclProcessor getProcessor() {return ((BridgeFactoryImpl)bridgeFactory).getProcessor();}


	/** Create an emty environment */
	public Environment getEMPTY_ENV() {
		return new EnvironmentImpl(bridgeFactory);
	}

	/** NamedElements field */
	protected Map namedElements = new HashMap();
	/** Get NamedElements */
	public Set getNamedElements() {
		return new LinkedHashSet(namedElements.values());
	}
	/** Set NamedElements */
	public void setNamedElements(Set namedElements) {
		this.namedElements = new HashMap();
		Iterator i = namedElements.iterator();
		while (i.hasNext()) {
			NamedElement ne = (NamedElement)i.next();
			this.namedElements.put(ne.getName(), ne);
		}
	}

	/** Parent field */
	protected Environment parent = null;
	/** Get Parent field */
	public Environment getParent() {
		return parent;
	}
	/** Set Parent field */
	public void setParent(Environment parent) {
		this.parent = parent;
	}

	/** Search in current environment for a NamedElement with a given name */
	public NamedElement lookupLocal(String name) {
		NamedElement nel = (NamedElement) namedElements.get(name);
		if (nel == null) {
			Iterator i = this.namespaces.iterator();
			while (i.hasNext() && nel == null) {
				Namespace ns = (Namespace) i.next();
				ModelElement me = ns.lookupOwnedElement(name);
				if (me != null) {
					nel = bridgeFactory.buildNamedElement(name, me, Boolean.FALSE);
					if (nel != null) namedElements.put(name, nel);
				}
			}
		}
		return nel;
	}

	/** Search in all the environments for a NamedElement with a given name */
	public ModelElement lookup(String name) {
		NamedElement ne = lookupLocal(name);
		if (ne != null) {
			return ne.getReferredElement();
		} else {
			if (getParent() != null ) {
			   return getParent().lookup(name);
			} else {
				return null;
			}
		}
	}

	/** Search for a full pathname NamedElement */
	public ModelElement lookupPathName(List names) {
		if (names.size() == 1)
			return lookup((String) names.get(0));
		else {
			NamedElement nel = lookupLocal((String) names.get(0));
			//if (nel == null) return null;
			if (nel!=null && nel.getReferredElement() instanceof Namespace) {
				// indicates a sub namespace of the namespace in which self is present
				return nestedEnvironment().addNamespace((Namespace) nel.getReferredElement()).lookupPathName(names.subList(1, names.size()));
			} else {
				// search in surrounding namespace
				if (getParent() != null ) {
					return getParent().lookupPathName(names);
				} else {
					return null;
				}
			}
		}
	}

	/** Search for a full pathname NamedElement */
	public Operation lookupPathName(List names, List arguments) {
		if (names.size() == 2) {
			Classifier cl = (Classifier) lookup((String) names.get(0));
			String op_name = (String) names.get(1);
			return cl.lookupOperation(op_name, arguments);
		} else {
			ModelElement firstNamespace = lookupLocal((String) names.get(0)).getReferredElement();
			if (firstNamespace instanceof Namespace) {
				// indicates a sub namespace of the namespace in which self is present
				return nestedEnvironment().addNamespace((Namespace) firstNamespace).lookupPathName(names.subList(1, names.size()), arguments);
			} else {
				// search in surrounding namespace
				return getParent().lookupPathName(names, arguments);
			}
		}
	}

	/** Add an element in the current environment */
	public Environment addElement(String name, ModelElement elem, Boolean imp) {
		if (name == null) return this;
		// the name must not clash with names already existing in this environment
		if (namedElements.get(name) != null) {
			return this;
		}

		EnvironmentImpl result = (EnvironmentImpl) this.clone();
		result.namedElements.put(name, new NamedElementImpl(name, elem, imp));
		return result;
	}

	/** Add a variable into the environment */
	public Environment addVariableDeclaration(String name, Classifier type, Boolean imp) {
		if (name == null) return this;
		if (type == null) return this;
		// the name must not clash with names already existing in this environment
		if (namedElements.get(name) != null) return this;

		VariableDeclaration vd = new VariableDeclaration$Class(name);
		vd.setType(type);

		EnvironmentImpl result = (EnvironmentImpl) this.clone();
		result.namedElements.put(name, new NamedElementImpl(name, vd, imp));
		return result;
		
	}

	/** Add an environment to the current environment */
	public Environment addEnvironment(Environment env) {
		// the names must not clash with names already existing in this environment
		Iterator i = ((EnvironmentImpl) env).namedElements.keySet().iterator();
		while (i.hasNext()) {
			String s = (String) i.next();
			if (this.namedElements.containsKey(s)) {
				throw new RuntimeException("In Environment.addEnvironment: Name already exists in environemnt - " + s);
			}
		}

		EnvironmentImpl result = (EnvironmentImpl) this.clone();

		result.namespaces.addAll(((EnvironmentImpl) env).namespaces);
		result.namedElements.putAll(((EnvironmentImpl) env).namedElements);
		return result;
	}


	/** Namespaces field */
	protected List namespaces = new Vector();
	/** Add a namespace */
	public Environment addNamespace(Namespace ns) {
		EnvironmentImpl result = (EnvironmentImpl) this.clone();
		//result.namedElements = new HashMap(ns.getEnvironmentWithoutParents().namedElements);
		result.namespaces.add(ns);
		return result.addElement(ns.getName(), ns, Boolean.FALSE);
	}

	/** Create a nested environment */	
	public Environment nestedEnvironment() {
		Environment result = new EnvironmentImpl(bridgeFactory);
		result.setParent(this);
		return result;
	}

	/** Search for an implicit property */
	public Property lookupImplicitProperty(String name) {
		NamedElement entry = lookupImplicitSourceForProperty(name);
		if (entry != null) {
			org.oslo.ocl20.semantics.bridge.ModelElement source = entry.getReferredElement();
			if (source != null && source instanceof VariableDeclaration) {
				source = ((VariableDeclaration)source).getType();
			}
			if (source != null && source instanceof org.oslo.ocl20.semantics.bridge.Classifier) {
				Property result = ((org.oslo.ocl20.semantics.bridge.Classifier)source).lookupProperty(name);
				return result;
			}
		}
		return null;
	}

	/** Search for the the source of an implicit property */
	public NamedElement lookupImplicitSourceForProperty(String name) {
		// For each element
		NamedElement foundElement = null;
		Iterator i = getNamedElements().iterator();
		while (i.hasNext()) {
			NamedElement entry = (NamedElement)i.next();
			// Implicit element
			if (entry != null && entry.getMayBeImplicit() != null && entry.getMayBeImplicit().booleanValue()) {
				// foundElement = entry.getType().lookupAttribute(name)
				org.oslo.ocl20.semantics.bridge.Classifier type = entry.getType();
				if (type != null) {
					Property foundProperty = type.lookupProperty(name);
					if (foundProperty != null)
						foundElement = entry;
				}
			}
		}
		// Search up
		if (foundElement == null) {
			Environment parent = getParent();
			if (parent != null) return parent.lookupImplicitSourceForProperty(name);
			return null;
		} else {
			return foundElement;
		}
	}

	/** Search for the source of an implicit operation */
	public NamedElement lookupImplicitSourceForOperation(String name, List types) {
		// For each element
		NamedElement foundElement = null;
		Iterator i = getNamedElements().iterator();
		while (i.hasNext()) {
			NamedElement entry = (NamedElement)i.next();
			// Implicit element
			if (entry.getMayBeImplicit().booleanValue()) {
				// foundElement = entry.getType().lookupOperation(name, params)
				org.oslo.ocl20.semantics.bridge.Operation foundOperation = entry.getType().lookupOperation(name, types);
				if (foundOperation != null)
					foundElement = entry;
			}
		}
		// Search up
		if (foundElement == null) {
			Environment parent = getParent();
			if (parent != null) return parent.lookupImplicitSourceForOperation(name, types);
			else return null;
		// Return the found element
		} else {
			return foundElement;
		}
	}

	/** Search for an implicit operation */
	public Operation lookupImplicitOperation(String name, List params) {
	    NamedElement ne = lookupImplicitSourceForOperation(name, params);
	    if (ne== null) {
	        bridgeFactory.buildClassifier(this);
	        //ErrorManager.reportError(null,"Environment, Can't Find "+name+params);
	    } else {
	        org.oslo.ocl20.semantics.bridge.ModelElement source = ne.getReferredElement();
			if (source instanceof VariableDeclaration) {
				source = ((VariableDeclaration)source).getType();
			}
			if (source instanceof org.oslo.ocl20.semantics.bridge.Classifier) {
				org.oslo.ocl20.semantics.bridge.Operation result = ((org.oslo.ocl20.semantics.bridge.Classifier)source).lookupOperation(name, params);
				return result;
			}
	    }
		return null;
	}

	public String toString() {
	    String s = "Environment {\n";
	    s += " namedElements : \n";
	    Iterator i = namedElements.entrySet().iterator();
	    while (i.hasNext()) {
	        s+= "  "+i.next() + "\n";
	    }
	    s += " namespaces : \n";
	    i = namespaces.iterator();
	    while (i.hasNext()) {
	        s+= "  "+i.next() + "\n";
	    }
	    s+= "  parent : " + this.parent+"\n";
	    s += "}";
	    return s;
	}
	
	/** Clone */
	public Object clone() {
		EnvironmentImpl copy = new EnvironmentImpl(bridgeFactory);
		copy.namedElements = new HashMap(this.namedElements);
		copy.namespaces = new Vector(this.namespaces);
		copy.parent = this.parent;
		return copy;
	}

	/** Accept a Semantic Visitor */
	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this, obj);
	}

	/** a BridgeFactory */
	protected org.oslo.ocl20.semantics.bridge.BridgeFactory bridgeFactory = null;
}
