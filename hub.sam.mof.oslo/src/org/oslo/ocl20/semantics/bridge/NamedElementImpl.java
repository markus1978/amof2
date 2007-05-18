package org.oslo.ocl20.semantics.bridge;

import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.model.expressions.VariableDeclaration;


public class NamedElementImpl
	implements NamedElement 
{
	/** Constructor for NamedElement */
	public NamedElementImpl(String name, ModelElement referredElement, Boolean mayBeImplicit) {
		this.name = name;
		this.mayBeImplicit = mayBeImplicit;
		this.referredElement = referredElement;
	}

	/** Name field */
	protected String name;
	/** Get name */
	public String getName() {
		return name;
	}
	/** Set name */
	public void setName(String name) {
		this.name = name;
	}
	
	/** mayBeImplicit field */
	protected Boolean mayBeImplicit;
	/** Get mayBeImplicit */
	public Boolean getMayBeImplicit() {
		return mayBeImplicit;
	}
	/** Set mayBeImplicit */
	public void setMayBeImplicit(Boolean mayBeImplicit) {
		this.mayBeImplicit = mayBeImplicit;
	}

	/** Set referredElement */
	protected ModelElement referredElement;
	/** Get referredElement */
	public ModelElement getReferredElement() {
		return referredElement;
	}
	/** Set referredElement */
	public void setReferredElement(ModelElement referredElement) {
		this.referredElement = referredElement;
	}

	/** Get the type of the referredElement */
	public Classifier getType() {
		if (referredElement instanceof VariableDeclaration)
			return ((VariableDeclaration) referredElement).getType();
		if (referredElement instanceof Classifier)
			return (Classifier) referredElement;
		//if (referredElement instanceof IState)
		//    return ???
		return null;
	}

	/** clone() */
	public Object clone(){
		NamedElementImpl copy = new NamedElementImpl(name, referredElement,mayBeImplicit);
		return copy;
	}

	/** accept a semantic visitor */
	public Object accept(SemanticsVisitor v, Object obj) {
		return null;
	}
}
