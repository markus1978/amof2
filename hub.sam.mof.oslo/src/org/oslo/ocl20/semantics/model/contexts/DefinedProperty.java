/*
 * Created on Mar 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.oslo.ocl20.semantics.model.contexts;

import org.oslo.ocl20.semantics.bridge.Property;

/**
 * @author dha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface DefinedProperty extends Property {
	public Constraint getDefinition();
	public void setDefinition(Constraint c); 
}
