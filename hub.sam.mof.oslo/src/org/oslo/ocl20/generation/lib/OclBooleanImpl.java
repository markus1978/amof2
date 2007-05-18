package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclVisitor;


public class OclBooleanImpl extends OclAnyImpl implements OclBoolean {

	protected OclBooleanImpl(String b, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
		super(adapter);
		if (newVariable) {
			_implementation = StdLibGenerationAdapterImpl.newTempVar("bool");
			_init = "java.lang.Boolean "+_implementation+"="+b+";\n";
		} else {
		  _implementation = b;
		}
		
	}

	String _implementation;
	
	static OclBoolean TRUE ;
	static OclBoolean FALSE ;

	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildBooleanType();
		OclTypeImpl ot = (OclTypeImpl)adapter.Type(type);
		ot.setInitialisation( this.getInitialisation() + ot.getInitialisation());
		return ot;
	}

	//--- IOclBoolean ---
	public OclBoolean equalTo(OclBoolean b2) {
		return super.equalTo(b2);
		/*
		OclBooleanImpl res;
		if (b2 instanceof OclUndefined)
			res = (OclBooleanImpl)b2;
		else
			res = (OclBooleanImpl)adapter.Boolean("("+this + " == " + b2+")");
		res.setInit(this.initialisation());
		res.setInit(this.initialisation()+ ((OclBooleanImpl)b2).initialisation());
		return res;
		*/
	}

	public OclBoolean notEqualTo(OclBoolean b2) {
		return super.notEqualTo(b2);
		/*OclBooleanImpl res;
		res = (OclBooleanImpl)adapter.Boolean("new java.lang.Boolean("+this + " != " + b2+")",true);
		res.setInit(res.initialisation()+this.initialisation());
		res.setInit(this.initialisation()+ ((OclBooleanImpl)b2).initialisation());
		return res;*/
	}
	
	public OclBoolean or(OclBoolean b2) {
		Impl bb1 = (Impl)this.wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());
		Impl bb2 = (Impl)((Impl)b2).wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());
		
		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("( ("+bb1+"==null ||" + bb2+"==null) " +
																 " ? ((java.lang.Boolean.TRUE.equals("+bb1+") || java.lang.Boolean.TRUE.equals("+bb2+"))" +
																 	" ? java.lang.Boolean.TRUE" +
																 	" : null)" +
																 " : new java.lang.Boolean("+bb1+".booleanValue() || "+bb2+".booleanValue()))",true);

		String c = "// "+this+" or "+b2+"\n";
		res._init = c+bb1.getInitialisation() + bb2.getInitialisation() + res.getInitialisation();
		return res;
	}

	public OclBoolean xor(OclBoolean b2) {
	    Impl bb1 = (Impl)this.wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());
	    Impl bb2 = (Impl)((Impl)b2).wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());

		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("( ("+bb1+"==null ||" + bb2+"==null) " +
				 " ? null" +
				 " : new java.lang.Boolean("+bb1+".booleanValue() ^ "+bb2+".booleanValue()))",true);

		String c = "// "+this+" xor "+b2+"\n";
		res._init = c+bb1.getInitialisation() + bb2.getInitialisation() + res.getInitialisation();
		return res;
	}

	public OclBoolean and(OclBoolean b2) {
	    Impl bb1 = (Impl)this.wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());
	    Impl bb2 = (Impl)((Impl)b2).wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());
		
		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("( ("+bb1+"==null ||" + bb2+"==null)" +
			                                                              " ? ( (java.lang.Boolean.FALSE.equals("+bb1+") || java.lang.Boolean.FALSE.equals("+bb2+"))" +
			                                                              "      ? java.lang.Boolean.FALSE" +
			                                                              "      : null" +
			                                                              "   )" +
			                                                              " : new java.lang.Boolean("+bb1+".booleanValue() && "+bb2+".booleanValue()))",true);
		String c = "// "+this+" and "+b2+"\n";
		res._init = c+bb1.getInitialisation() + bb2.getInitialisation() + res.getInitialisation();
		return res;
	}

	public OclBoolean not() {
	    Impl res;
		res = (Impl)adapter.Boolean("new java.lang.Boolean("+"!" + this+".booleanValue())",true);
		res.setInitialisation(this.getInitialisation() + res.getInitialisation());
		return (OclBoolean)res;
	}

	public OclBoolean implies(OclBoolean b2) {
	    Impl bb1 = (Impl)this.wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());
	    Impl bb2 = (Impl)((Impl)b2).wrappedWithExceptionHandler(adapter.getProcessor().getTypeFactory().buildBooleanType());
		
		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("( ("+bb1+"==null || "+bb2+"==null)" +
			                                                  " ? ( ("+bb1+"==null && "+bb2+"==null)" +
			                                                  "     ? null" +
			                                                  "     : ( ("+bb1+"==null && "+bb2+"!=null)" +
			                                                  "         ? ("+bb2+".booleanValue() ? "+bb2+" : null)" +
			                                                  "         : ("+bb1+".booleanValue() ? java.lang.Boolean.TRUE : "+bb1+")" +
			                                                  "       )" +
			                                                  "    ) "+
			                                                  " : new java.lang.Boolean(!"+bb1+".booleanValue() || "+bb2+".booleanValue())" +
			                                                  ")"
			                                                 ,true);
		String c = "// "+this+" implies "+b2+"\n";
		res._init = c+bb1.getInitialisation() + bb2.getInitialisation() + res.getInitialisation();
		return res;
	}
/*
	//expr1.evaluationType ifThenElse(IOclExpression expr1, IOclExpression expr2);
	public IOclAny ifThenElse(IOclExpression expr1, IOclExpression expr2) {
		if (this == TRUE) {
			return expr1.evaluate();
		}
		if (this == FALSE) {
			return expr2.evaluate();
		}
		return UNDEFINED;
	}

	public IOclAny ifThenElse(IOclAny val1, IOclAny val2) {
		if (this == TRUE) {
			return val1;
		}
		if (this == FALSE) {
			return val2;
		}
		return UNDEFINED;
	}
*/
	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit(this, obj);
	}

	//--- Object ---
	public String toString() {
		return _implementation;
	}

	public Object asJavaObject() {
		return _implementation;
	}

	public boolean equals(Object b) {
	    return this==b;
	}
	public int hashCode() {
		return this == FALSE ? 0 : 1;
	}
	public Object clone() {
		return this;
	}
}
