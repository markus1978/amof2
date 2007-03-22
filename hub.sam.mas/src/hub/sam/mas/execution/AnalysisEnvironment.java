package hub.sam.mas.execution;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.ocl.OclProcessor;
import hub.sam.mof.ocl.oslobridge.MofEnumerationImpl;
import hub.sam.mof.ocl.oslobridge.MofOclModelElementTypeImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.semantics.model.contexts.ClassifierContextDecl;
import org.oslo.ocl20.semantics.model.types.BagType;
import org.oslo.ocl20.semantics.model.types.BooleanType;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OrderedSetType;
import org.oslo.ocl20.semantics.model.types.RealType;
import org.oslo.ocl20.semantics.model.types.SequenceType;
import org.oslo.ocl20.semantics.model.types.SetType;
import org.oslo.ocl20.semantics.model.types.StringType;
import org.oslo.ocl20.standard.types.PrimitiveImpl;

import cmof.MultiplicityElement;
import cmof.NamedElement;
import cmof.Package;
import cmof.PrimitiveType;
import cmof.Type;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import core.primitivetypes.Boolean;
import core.primitivetypes.Integer;
import core.primitivetypes.UnlimitedNatural;

public class AnalysisEnvironment {

	private String additionalContextAttribute = null;
	private Type additionalContextType = null;
	private boolean additionalContext = false;
	
	private final Extent m2Extent;
	private final Environment oclEnvironment;
	private final Collection<Package> topLevelPackages;
	private final cmofFactory factory;
	
	public AnalysisEnvironment(Extent e, Repository repo) {
		this.m2Extent = e;		
		topLevelPackages = new Vector<Package>();		
		for (cmof.reflection.Object outermost: m2Extent.outermostComposites()) {
			if (outermost instanceof Package) {
				topLevelPackages.add((Package)outermost);
			}
		}
		Package cmofPackage = (cmof.Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		this.factory = (cmofFactory)repo.createFactory(m2Extent, cmofPackage);
		oclEnvironment = OclProcessor.createEnvironment(topLevelPackages);
	}
	
	public void checkOclConstraint(String expression, NamedElement context, Type requiredType, boolean isCollection, boolean isUnique, boolean isOrdered) throws SemanticException {
		String errorPrefix = "Ocl expression [" + expression + "] ";
		List result = null;
		try {
			result = OclProcessor.analyzeInvariant(oclEnvironment, expression, context);
		} catch (OclException ex) {
			throw new SemanticException(ex);
		}
		
		if (result != null && requiredType != null) {
			for(Object o1: result) {
				ClassifierContextDecl ccd = (ClassifierContextDecl)o1;
				for (Object o2: ccd.getConstraint()) {
					org.oslo.ocl20.semantics.model.contexts.Constraint constraint = (org.oslo.ocl20.semantics.model.contexts.Constraint)o2;
					Classifier exprType = constraint.getBodyExpression().getType();
					Classifier exprClassifierType = null;
					if (exprType instanceof MofOclModelElementTypeImpl || exprType instanceof MofEnumerationImpl || exprType instanceof PrimitiveImpl) {
						exprClassifierType = exprType;
						if (!(!(context instanceof MultiplicityElement) || ((MultiplicityElement)context).getUpper() == 1)) {
							throw new SemanticException(errorPrefix + "has collection type for non collection feature.");							
						}
					} else if (exprType instanceof CollectionType) {
						if (!isCollection) {
							throw new SemanticException(errorPrefix + "has non collection type for collection feature.");						
						}
						exprClassifierType = ((CollectionType)exprType).getBaseElementType();
						if (exprType instanceof BagType) {
							if (isOrdered) {
								throw new SemanticException(errorPrefix + "has non ordered collection type for an ordered feature.");								
							}
							if (isUnique) {
								throw new SemanticException(errorPrefix + "has non unique collection type for an unique feature.");								
							}
						} else if (exprType instanceof SequenceType) {							
							if (isUnique) {
								throw new SemanticException(errorPrefix + "has non unique collection type for an unique feature.");
							}
						} else if (exprType instanceof SetType) {
							if (!(exprType instanceof OrderedSetType)) {
								if (isOrdered) {
									throw new SemanticException(errorPrefix + "has non ordered collection type for an ordered feature.");									
								}								
							}
						} 
					} else {
						throw new SemanticException(errorPrefix + "has unknown static type.");
					}
					
					if (exprClassifierType instanceof MofOclModelElementTypeImpl || exprClassifierType instanceof MofEnumerationImpl) {
						Object mofDelegate = null;
						if (exprClassifierType instanceof MofOclModelElementTypeImpl) {
							mofDelegate = ((MofOclModelElementTypeImpl)exprClassifierType).getMofDelegate();
						} else {
							mofDelegate = ((MofEnumerationImpl)exprClassifierType).getMofDelegate();
						}
						if (!requiredType.equals(mofDelegate)) {
							throw new SemanticException(errorPrefix + "has wrong type or base type.");
						}											
					} else if (exprClassifierType instanceof PrimitiveImpl) {
						if (exprClassifierType instanceof BooleanType) {
							if (!requiredType.getName().equals(Boolean.class.getSimpleName())) {
								throw new SemanticException(errorPrefix + "has wrong type or base type.");
							}
						} else if (exprClassifierType instanceof StringType) {
							if (!requiredType.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
								throw new SemanticException(errorPrefix + "has wrong type or base type.");
							}
						} else if (exprClassifierType instanceof RealType) {
							if (!requiredType.getName().equals(Integer.class.getSimpleName()) && !requiredType.getName().equals(UnlimitedNatural.class.getSimpleName())) {
								throw new SemanticException(errorPrefix + "has wrong type or base type.");
							}
						}
					} else {
						throw new SemanticException(errorPrefix + "has wrong base type.");
					}
				}
			}
		}
	}
	
	public void addAdditionalContextAttribute(String name, Object value, Type attributeType, Type contextType) {
		additionalContextType = contextType;
		additionalContext = true;
		additionalContextAttribute = name;
		List<String> contextName = Arrays.asList(contextType.getQualifiedName().split("\\."));		
		MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)oclEnvironment.lookupPathName(contextName);
		if (contextOclModelElementType == null) {
			throw new SemanticException("Cannot resolve context (" + contextName + ") of action " + this.toString());
		}
						
		contextOclModelElementType.addAdditionalProperty(additionalContextAttribute, value, attributeType);		
	}
	
	public void removeAdditionalAttribute() {
		if (additionalContext) {
			List<String> contextName = Arrays.asList(additionalContextType.getQualifiedName().split("\\."));		
			MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)oclEnvironment.lookupPathName(contextName);
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

	public Collection<Package> getTopLevelPackages() {
		return topLevelPackages;
	}

	public Environment getOclEnvironment() {
		return oclEnvironment;
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
