package hub.sam.mof.ocl;

import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.util.AssertionException;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.EnumLiteral;
import org.oslo.ocl20.semantics.bridge.EnumerationType;
import org.oslo.ocl20.semantics.bridge.ModelElement;
import org.oslo.ocl20.semantics.bridge.Property;
import org.oslo.ocl20.standard.types.OclAnyTypeImpl;

import cmof.Enumeration;
import cmof.EnumerationLiteral;
import cmof.Package;

public class MofEnumerationImpl extends OclAnyTypeImpl implements EnumerationType {

	protected Enumeration mofEnumeration;
	
	@SuppressWarnings("unchecked")
	public MofEnumerationImpl(Enumeration eenum, OclProcessor proc) {
		super(proc);
		mofEnumeration = eenum;
		
		super.createOperations(processor.getTypeFactory());
		super.getOperations().add(proc.getBridgeFactory().buildOperation(super.processor.getTypeFactory().buildBooleanType(), "=", new Classifier[] { this }));
		super.getOperations().add(proc.getBridgeFactory().buildOperation(super.processor.getTypeFactory().buildBooleanType(), "<>", new Classifier[] { this }));
	}

	String name = null;
	@Override
	public String getName() {
		if (mofEnumeration != null) {
			name = "";
			Package pkg = (Package)mofEnumeration.getPackage();
			while (pkg != null) {
				if (!name.equals("")) name = "."+name;
				name = pkg.getName()+name;
				pkg = pkg.getNestingPackage();
			}
			if (!name.equals("")) name += ".";
			name +=  mofEnumeration.getName();
		} 
		return name;
	}

	@Override
	public Boolean conformsTo(Classifier t2) {
		if (t2 instanceof EnumerationType) {
			return (getName().equals(t2.getName())) ? Boolean.TRUE : Boolean.FALSE;
		}
		return Boolean.FALSE;
	}

	public EnumLiteral lookupEnumLiteral(String name) {
		for (EnumerationLiteral literal: mofEnumeration.getOwnedLiteral()) {
			if (name.equals(literal.getName())) {
				return ((MofBridgeFactory)super.processor.getBridgeFactory()).buildEnumLiteral(literal, this);		
			}
		}
		return null;
	}

	@Override
	public Property lookupProperty(String name) {
		return lookupEnumLiteral(name);
	}

	@Override
	public ModelElement lookupOwnedElement(String name) {
		return lookupEnumLiteral(name);
	}
	
	List literals = null;
	@SuppressWarnings("unchecked")
	public List getLiteral() {
		if (mofEnumeration != null) {
			literals = new Vector();
			Iterator<? extends EnumerationLiteral> i = mofEnumeration.getOwnedLiteral().iterator();
			while (i.hasNext()) {				
				literals.add(((MofBridgeFactory)super.processor.getBridgeFactory()).buildEnumLiteral(i.next(), this));
			}
		}
		return literals;
	}

	public void setLiteral(List literals) {
		// emtpy
	}

	/** Accept a Sematics Visitor */
	@Override
	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this,obj);
	}
	
	@Override
	public String toString(){
		return mofEnumeration.getName();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof EnumerationType) {
			EnumerationType enumerationType = (EnumerationType)o;
			return getName().equals(enumerationType.getName());
		}
		return false;
	}
	
	public Enumeration getMofDelegate() {
		return mofEnumeration;
	}
	
	@Override
	public Object getDelegate() {
		try {
			Class result = Thread.currentThread().getContextClassLoader().loadClass(JavaMapping.mapping.getFullQualifiedJavaIdentifier(mofEnumeration));
			((MofOclProcessor)processor).getEnumerations().put(result, mofEnumeration);
			return result;
		} catch (ClassNotFoundException ex) {
			throw new AssertionException(ex);
		}
	}
}
