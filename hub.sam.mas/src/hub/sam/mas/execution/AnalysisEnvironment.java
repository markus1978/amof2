package hub.sam.mas.execution;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclEnvironment;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.ocl.oslobridge.MofOclModelElementTypeImpl;

import java.util.Arrays;
import java.util.List;

import org.oslo.ocl20.semantics.bridge.Environment;

import cmof.NamedElement;
import cmof.Package;
import cmof.PrimitiveType;
import cmof.Type;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class AnalysisEnvironment {

	private String additionalContextAttribute = null;
	private Type additionalContextType = null;
	private boolean additionalContext = false;
	
	private final Extent m2Extent;
	private final OclEnvironment oclEnvironment;
	private final cmofFactory factory;
	
	public AnalysisEnvironment(Extent m2Extent, Extent m1Extent, Repository repo) {
		this.m2Extent = m2Extent;		
		Package cmofPackage = (cmof.Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		this.factory = (cmofFactory)repo.createFactory(m2Extent, cmofPackage);
		
		OclEnvironment oclEnvironment = m2Extent.getAdaptor(OclEnvironment.class);
		if (oclEnvironment == null) {
			repo.configureExtent(m1Extent, m2Extent);
			oclEnvironment = m1Extent.getAdaptor(OclEnvironment.class);
		}
		if (oclEnvironment == null) {
			throw new RuntimeException("assert");
		}
 		this.oclEnvironment = oclEnvironment;
	}
	
	public void checkOclConstraint(String expression, NamedElement context, Type requiredType, boolean isCollection, boolean isUnique, boolean isOrdered) throws SemanticException {
		try {
			oclEnvironment.analyseOclExpression(expression, context, requiredType, isCollection, isUnique, isOrdered);			
		} catch (OclException ex) {
			throw new SemanticException("Ocl error: " + ex.getMessage());
		}
	}
	
	public void addAdditionalContextAttribute(String name, Object value, Type attributeType, Type contextType) {
		additionalContextType = contextType;
		additionalContext = true;
		additionalContextAttribute = name;
		List<String> contextName = Arrays.asList(contextType.getQualifiedName().split("\\."));		
		MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)oclEnvironment.getEnvironment().lookupPathName(contextName);
		if (contextOclModelElementType == null) {
			throw new SemanticException("Cannot resolve context (" + contextName + ") of action " + this.toString());
		}
						
		contextOclModelElementType.addAdditionalProperty(additionalContextAttribute, value, attributeType);		
	}
	
	public void removeAdditionalAttribute() {
		if (additionalContext) {
			List<String> contextName = Arrays.asList(additionalContextType.getQualifiedName().split("\\."));		
			MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)oclEnvironment.getEnvironment().lookupPathName(contextName);
			if (contextOclModelElementType == null) {
				throw new SemanticException("Cannot resolve context of action " + this.toString());
			}			
			contextOclModelElementType.removeAdditionalProperty(additionalContextAttribute);
			additionalContext = false;
		}
	}

	public cmofFactory getFactory() {
		return factory;
	}

	public Environment getOclEnvironment() {
		return oclEnvironment.getEnvironment();
	}
	
	public PrimitiveType getStringType() {
		return (PrimitiveType)m2Extent.query("Package:core/Package:primitivetypes/PrimitiveType:String");
	}
	
	public PrimitiveType getIntegerType() {
		return (PrimitiveType)m2Extent.query("Package:core/Package:primitivetypes/PrimitiveType:Integer");
	}

	public PrimitiveType getBooleanType() {
		return (PrimitiveType)m2Extent.query("Package:core/Package:primitivetypes/PrimitiveType:Boolean");
	}

	public PrimitiveType getUnlimitedNaturalType() {
		return (PrimitiveType)m2Extent.query("Package:core/Package:primitivetypes/PrimitiveType:UnlimitedNatural");	
	}
}
