package hub.sam.mof.ocl.oslobridge;

import hub.sam.mof.PlugInActivator;
import hub.sam.mof.javamapping.JavaMapping;

import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.EnumLiteral;
import org.oslo.ocl20.semantics.bridge.EnumerationType;

import cmof.EnumerationLiteral;

public class MofEnumerationLiteralImpl implements EnumLiteral {

	protected EnumerationLiteral mofEnumerationLiteral;
	private EnumerationType mofEnumeration = null;
	private String name = null;
	
	public MofEnumerationLiteralImpl(EnumerationLiteral eenumLit, EnumerationType parent) {
		mofEnumerationLiteral = eenumLit;
		mofEnumeration=parent;
	}

	public EnumerationType getEnumeration() {
		return mofEnumeration;
	}

	public void setEnumeration(EnumerationType enumerationType) {
		mofEnumeration = enumerationType;
	}

	
	public String getName() {
		if (name == null) name = mofEnumerationLiteral.getName();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	Classifier type = null;
	public Classifier getType() {
		return getEnumeration(); 
	}

	public void setType(Classifier type) {
		// emtpy
	}
	
	public Object accept(SemanticsVisitor v, Object data) {
		return  v.visit(this, data);
	}

	@Override
	public Object clone() {
		return this;
	}
	
	public Object getDelegate() {                            
         try {
             java.lang.Class implementation = null;                        
             implementation = PlugInActivator.getClassLoader().loadClass(
                 JavaMapping.mapping.getFullQualifiedJavaIdentifier(((MofEnumerationImpl)mofEnumeration).getMofDelegate()));
             for (java.lang.Object enumConstant: implementation.getEnumConstants()) {
                 if (enumConstant.toString().equals(JavaMapping.mapping.getJavaEnumConstantForLiteral(mofEnumerationLiteral))) {                                
                     return enumConstant;
                 }
             }
         } catch (Exception e) {
             throw new RuntimeException("cannot create enum value");
         }
         throw new RuntimeException("Unrecognized enum value");
	}
}
