/**
 *
 *  Class NamedElement.java
 *
 *  Generated by KMFStudio at 09 May 2003 17:49:06
 *  Visit http://www.cs.ukc.ac.uk/kmf
 *
 */

package org.oslo.ocl20.semantics.bridge;

import org.oslo.ocl20.semantics.SemanticsElement;

public interface NamedElement
extends
    SemanticsElement
{
	/** Get the 'mayBeImplicit' from 'NamedElement' */
	public Boolean getMayBeImplicit();
	/** Set the 'mayBeImplicit' from 'NamedElement' */
	public void setMayBeImplicit(Boolean mayBeImplicit);

	/** Get the 'name' from 'NamedElement' */
	public String getName();
	/** Set the 'name' from 'NamedElement' */
	public void setName(String name);

	/** Get the 'referredElement' from 'NamedElement' */
	public ModelElement getReferredElement();
	/** Set the 'referredElement' from 'NamedElement' */
	public void setReferredElement(ModelElement referredElement);

	/** Operation  'getType' from 'NamedElement' */
	public Classifier getType();

	/** Override the toString */
	public String toString();

	/** Clone the object */
	public Object clone();
}