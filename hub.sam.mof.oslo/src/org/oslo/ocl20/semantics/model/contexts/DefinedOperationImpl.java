/*
 * Created on Mar 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.oslo.ocl20.semantics.model.contexts;

import java.util.List;

import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;


/**
 * @author dha
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedOperationImpl implements DefinedOperation {

    public DefinedOperationImpl() {
    }

	public DefinedOperationImpl(String name, Classifier retType, List pTypes, List pNames, Constraint def) {
		this._name = name;
		this._retType = retType;
		this._pTypes = pTypes;
		this._pNames = pNames;
		this._definition = def;
	}

	Constraint _definition = null;
    public Constraint getDefinition() {
        return _definition;
    }

    public void setDefinition(Constraint c) {
		_definition = c;
    }

	Classifier _retType = null;
	public Classifier getReturnType() {
		return _retType;
	}

	public void setReturnType(Classifier returnType) {
		_retType = returnType;
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
		return new DefinedOperationImpl(_name, _retType, _pTypes, _pNames, _definition);
	}

	List _pTypes;
    public List getParameterTypes() {
        return _pTypes;
    }

    public void setParameterTypes(List parameterTypes) {
		_pTypes = parameterTypes;
    }

	List _pNames;
    public List getParameterNames() {
        return _pNames;
    }

    public void setParameterNames(List parameterNames) {
		_pNames = parameterNames;
	}


}
