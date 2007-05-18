/*
 * Created on Mar 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.oslo.ocl20.semantics.model.contexts;

import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;


/**
 * @author dha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedPropertyImpl implements DefinedProperty {

    public DefinedPropertyImpl() {
    }

	public DefinedPropertyImpl(String name, Classifier type, Constraint def) {
		this._name = name;
		this._type = type;
		this._definition = def;
	}

	Constraint _definition = null;
    public Constraint getDefinition() {
        return _definition;
    }

    public void setDefinition(Constraint c) {
		_definition = c;
    }

	Classifier _type = null;
    public Classifier getType() {
        return _type;
    }

    public void setType(Classifier type) {
    	_type = type;
    }

	String _name="";
    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Object getDelegate() {
        return null;
    }

    public Object accept(SemanticsVisitor v, Object obj) {
        return v.visit(this, obj);
    }

	public Object clone() {
		return new DefinedPropertyImpl(_name, _type, _definition);
	}

	public String toString() {
	    return getName();
	}

}
