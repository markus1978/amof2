/*
 * Created on 08-Aug-2003
 *
 */
package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclType;


/**
 * @author dha
 *
 */
public class OclTypeImpl extends OclAnyImpl implements OclType {

	public Classifier cls;

	public OclTypeImpl(Classifier cls, StdLibGenerationAdapterImpl adapter) {
		super(adapter);
		this.cls = cls;
	}

	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildTypeType(cls);
		OclTypeImpl ot = (OclTypeImpl)adapter.Type(type);
		ot.setInitialisation( this.getInitialisation() + ot.getInitialisation());
		return ot;
	}

	public Object clone() {
		return new OclTypeImpl(this.cls, super.adapter);
	}

	public boolean equals(Object o) {
		if (o instanceof OclTypeImpl)
			return this.cls.equals(  ((OclTypeImpl)o).cls );
		return false;
	}

	public Object asJavaObject() {
		return cls; //.getDelegate();
	}

	public String toString() {
		return cls.getFullName(".");
	}

}
