package hub.sam.mof.ocl.oslobridge;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Operation;

public class MofOperationImpl implements Operation {
	
	private cmof.Operation mofOperation;
	private OclProcessor processor;
	private Classifier returnType = null;
	private List<Classifier> parameterTypes = null;
	private List<String> parameterNames = null;
	private String name = null;
	
	/*
	 * If op == null then this has to be an OCL-operation.
	 */
	public MofOperationImpl(cmof.Operation op, OclProcessor proc) {
		mofOperation = op;
		this.processor = proc;
	}
	
	public Classifier getReturnType() {
		if (mofOperation==null) {
			return this.returnType;
		}
		if (mofOperation.getUpper() == -1 || mofOperation.getUpper() > 1) {
			if (mofOperation.isOrdered()) {
				if (mofOperation.isUnique()) {
					return this.processor.getTypeFactory().buildOrderedSetType(this.processor.getBridgeFactory().buildClassifier(mofOperation.getType()));
				} else {
					return this.processor.getTypeFactory().buildSequenceType(this.processor.getBridgeFactory().buildClassifier(mofOperation.getType()));
				}
			} else {
				if (mofOperation.isUnique()) {
					return this.processor.getTypeFactory().buildSetType(this.processor.getBridgeFactory().buildClassifier(mofOperation.getType()));
				} else {
					return this.processor.getTypeFactory().buildBagType(this.processor.getBridgeFactory().buildClassifier(mofOperation.getType()));
				}
			}
		} else {
			return this.processor.getBridgeFactory().buildClassifier(mofOperation.getType());
		}
	}

	public void setReturnType(Classifier cl) {
		this.returnType = cl;
	}

	
	public List getParameterTypes() {
		if (this.parameterTypes == null) {
			this.parameterTypes = new Vector<Classifier>();
			if (mofOperation != null) {
				Iterator i = mofOperation.getFormalParameter().iterator();
				while (i.hasNext()) {
					cmof.Parameter p = (cmof.Parameter) i.next();
					this.parameterTypes.add(this.processor.getBridgeFactory().buildClassifier(p.getType()));
				}
			}
		}
		return this.parameterTypes;
	}
	
	@SuppressWarnings("unchecked")
	public void setParameterTypes(List l) {
		this.parameterTypes = l;
	}

	public void setParameterNames(List l) {
		// emtpy
	}

	
	public List getParameterNames() {
		if (this.parameterNames == null) {
			this.parameterNames = new Vector<String>();
			if (mofOperation != null) {
				Iterator i = mofOperation.getFormalParameter().iterator();
				while (i.hasNext()) {
					cmof.Parameter p = (cmof.Parameter) i.next();
					this.parameterNames.add(p.getName());
				}
			}
		}
		return this.parameterNames;
	}

	@Override
	public Object clone() {
		return null;
	}

	
	public String getName() {
		if (mofOperation == null) {
			return this.name;
		}
		return mofOperation.getName();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this,obj);
	}

	public Object getDelegate() {
		return mofOperation;
	}
	
	@Override
	public String toString() {
	    String s="";
	    s+=getName()+"(";
	    Iterator i = getParameterTypes().iterator();
	    while (i.hasNext()) {
	        Classifier c = (Classifier)i.next();
	        s+=c.getName();
	        if (i.hasNext()) s+= ", ";
	    }
	    s+=")";
	    return s;
	}
}
