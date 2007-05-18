/*
 * Created on 07-Aug-2003
 *
 */
package org.oslo.ocl20.generation.lib;

import java.util.Set;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclAnyModelElement;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclType;


/**
 * @author dha
 *
 */
public class OclAnyModelElementImpl extends OclAnyImpl implements OclAnyModelElement {

	java.lang.String _implementation;
	public OclAnyModelElementImpl(Classifier type, java.lang.String modelElem, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
		super(adapter);
		this.type=type;
		if (newVariable) {
			_implementation = StdLibGenerationAdapterImpl.newTempVar("elem");
			String typeName = ((Class)type.getDelegate()).getName();
			_init = typeName+" "+_implementation+"="+modelElem+";\n";
		} else {
			this._implementation = modelElem;
		}
	}
	
	Classifier type;
	public OclType oclType() {
		OclTypeImpl ot = (OclTypeImpl)adapter.Type(type);
		ot.setInitialisation( this.getInitialisation() + ot.getInitialisation());
		return ot;
	}


	public OclBoolean equalTo(OclAny o2){
		return adapter.getProcessor().getModelGenerationAdapter().OclModelElement_equalTo(this,o2);
	}
	
	public OclBoolean oclIsNew(){
		return adapter.getProcessor().getModelGenerationAdapter().OclModelElement_oclIsNew(this);
	}

	public OclBoolean oclIsUndefined(){
		return adapter.getProcessor().getModelGenerationAdapter().OclModelElement_oclIsUndefined(this);
	}

	public OclAny oclAsType(OclType type){
		Object o = adapter.getProcessor().getModelGenerationAdapter().OclModelElement_oclAsType(this, type);
		return adapter.OclAny(o);
	}
	
	public OclBoolean oclIsTypeOf(OclType type){
		return adapter.getProcessor().getModelGenerationAdapter().OclModelElement_oclIsTypeOf(this,type);
	}
	
	public OclBoolean oclIsKindOf(OclType type){
		return adapter.getProcessor().getModelGenerationAdapter().OclModelElement_oclIsKindOf(this,type);
	}
	
	public OclSet allInstances(){
		Set s = adapter.getProcessor().getModelGenerationAdapter().OclModelElement_allInstances(this);
		return null;//adapter.Set(this,s);
	}


	public Object clone() {
		return null;
	}

	public boolean equals(Object o) {
		if (o instanceof OclAnyModelElementImpl)
			return _implementation == ((OclAnyModelElementImpl)o)._implementation;
		return false;
	}

	public Object asJavaObject() {
		return _implementation;
	}

	public String toString() {
		return _implementation;
	}

}
