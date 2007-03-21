package hub.sam.mof.emf;

import hub.sam.util.AbstractFluxBox;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;

import core.primitivetypes.Boolean;

import cmof.Association;
import cmof.Element;
import cmof.Enumeration;
import cmof.EnumerationLiteral;
import cmof.MultiplicityElement;
import cmof.Operation;
import cmof.Package;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.PrimitiveType;
import cmof.Property;
import cmof.Type;
import cmof.TypedElement;
import cmof.UmlClass;

public class EcoreGenerator {
	
	private final AbstractFluxBox<Element, EObject, EClass> fFluxBox = new AbstractFluxBox<Element, EObject, EClass>() {
		@Override
		protected EObject createValue(Element key, EClass metaClass) {
			return fFactory.create(metaClass);
		}		
	};
	
	private final Resource fResource;	
	private final EcoreFactory fFactory;
	
	public EcoreGenerator(final Resource resource) {
		super();
		fResource = resource;
		fFactory = EcoreFactoryImpl.eINSTANCE;
	}

	public void generateEcorModel(List<Package> mofPackages) {
		EList contents = fResource.getContents();
		for (Package mofPackage: mofPackages) {
			contents.add(addPackage(null, mofPackage));
		}
	}
	
	private EPackage addPackage(EPackage emfOwner, cmof.Package mofPackage) {
		EPackage newPackage = (EPackage)fFluxBox.getObject(mofPackage, EcorePackage.eINSTANCE.getEPackage());
		newPackage.setName(mofPackage.getName());
		if (emfOwner != null) {
			emfOwner.getESubpackages().add(newPackage);
		}
		for (cmof.Package mofSubPackage: mofPackage.getNestedPackage()) {
			addPackage(newPackage, mofSubPackage);
		}
		for (cmof.Type mofSubType: mofPackage.getOwnedType()) {
			if (mofSubType instanceof UmlClass) {
				addClass(newPackage, (UmlClass)mofSubType);
			} else if (mofSubType instanceof Enumeration) {
				addEnumeration(newPackage, (Enumeration)mofSubType);
			} else if (mofSubType instanceof PrimitiveType) {
				addDataType(newPackage, (PrimitiveType)mofSubType);
			} else if (mofSubType instanceof Association) {
				// we ignore assciation, only navigable attributes are put into the model
			} else {
				throw new RuntimeException("not implemented");
			}
		}
		return newPackage;
	}
	
	private EClass addClass(EPackage emfOwner, cmof.UmlClass mofClass) {
		EClass newClass = (EClass)fFluxBox.getObject(mofClass, EcorePackage.eINSTANCE.getEClass());
		newClass.setName(mofClass.getName());
		emfOwner.getEClassifiers().add(newClass);
		newClass.setAbstract(mofClass.isAbstract());		
		for (UmlClass superClass: mofClass.getSuperClass()) {
			newClass.getESuperTypes().add(fFluxBox.getObject(superClass, EcorePackage.eINSTANCE.getEClass()));
		}
		for (Property attribute: mofClass.getOwnedAttribute()) {
			addFeature(newClass, attribute);
		}
		for (Operation operation: mofClass.getOwnedOperation()) {
			addOperation(newClass, operation);
		}
		return newClass;
	}
	
	private EStructuralFeature addFeature(EClass emfOwner, Property mofProperty) {
		Property opposite = mofProperty.getOpposite();
		EStructuralFeature newFeature = null;
		if (opposite == null) {
			EAttribute newAttribute = (EAttribute)fFluxBox.getObject(mofProperty, EcorePackage.eINSTANCE.getEAttribute());
			newFeature = newAttribute;
		} else {
			EReference newReference = (EReference)fFluxBox.getObject(mofProperty, EcorePackage.eINSTANCE.getEReference());
			newFeature = newReference;			
			if (opposite.getOwner() instanceof UmlClass) {
				newReference.setEOpposite((EReference)fFluxBox.getObject(opposite, EcorePackage.eINSTANCE.getEReference()));
			}
			newReference.setContainment(mofProperty.isComposite());
		}	
		emfOwner.getEStructuralFeatures().add(newFeature);
		newFeature.setName(mofProperty.getName());		
		newFeature.setDerived(mofProperty.isDerived());
		setTypedElementFeatures(newFeature, mofProperty);
		
		return newFeature;
	}
	
	private EOperation addOperation(EClass emfOwner, Operation mofOperation) {
		EOperation newOperation = (EOperation)fFluxBox.getObject(mofOperation, EcorePackage.eINSTANCE.getEOperation());
		emfOwner.getEOperations().add(newOperation);
		newOperation.setName(mofOperation.getName());
		
		newOperation.setLowerBound(mofOperation.getLower());
		newOperation.setUpperBound((int)mofOperation.getUpper());		
		newOperation.setOrdered(mofOperation.isOrdered());
		newOperation.setUnique(mofOperation.isUnique());
		newOperation.setEType(getType(mofOperation.getType()));		
		
		for (Parameter parameter: mofOperation.getParameter()) {
			if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
				addParameter(newOperation, parameter);
			} else {
				setTypedElementFeatures(newOperation, parameter);
			}
		}		
		return newOperation;
	}
		
	private EParameter addParameter(EOperation emfOwner, Parameter mofParameter) {
		EParameter newParameter = (EParameter)fFluxBox.getObject(mofParameter, EcorePackage.eINSTANCE.getEParameter());
		emfOwner.getEParameters().add(newParameter);
		newParameter.setName(mofParameter.getName());
		setTypedElementFeatures(newParameter, mofParameter);
		return newParameter;
	}
	
	private EEnum addEnumeration(EPackage emfOwner, Enumeration mofEnumeration) {
		EEnum newEnum = (EEnum)fFluxBox.getObject(mofEnumeration, EcorePackage.eINSTANCE.getEEnum());
		emfOwner.getEClassifiers().add(newEnum);
		newEnum.setName(mofEnumeration.getName());
		int i = 0;
		for(EnumerationLiteral literal: mofEnumeration.getOwnedLiteral()) {
			addEnumerationLiteral(newEnum, literal, i++);
		}
		return newEnum;
	}
	
	private EEnumLiteral addEnumerationLiteral(EEnum emfOwner, EnumerationLiteral mofLiteral, int value) {
		EEnumLiteral newLiteral = (EEnumLiteral)fFluxBox.getObject(mofLiteral, EcorePackage.eINSTANCE.getEEnumLiteral());
		emfOwner.getELiterals().add(newLiteral);
		newLiteral.setName(mofLiteral.getName());
		newLiteral.setValue(value);
		return newLiteral;
	}
	
	private EDataType addDataType(EPackage emfOwner, PrimitiveType mofDataType) {
		EDataType newDataType = (EDataType)fFluxBox.getObject(mofDataType, EcorePackage.eINSTANCE.getEDataType());
		emfOwner.getEClassifiers().add(newDataType);
		newDataType.setName(mofDataType.getName());		
		return newDataType;
	}
	
	private void setTypedElementFeatures(ETypedElement emfTypedElement, Element mofObject) {
		emfTypedElement.setLowerBound(((MultiplicityElement)mofObject).getLower());
		emfTypedElement.setUpperBound((int)((MultiplicityElement)mofObject).getUpper());		
		emfTypedElement.setOrdered(((MultiplicityElement)mofObject).isOrdered());
		emfTypedElement.setUnique(((MultiplicityElement)mofObject).isUnique());
		emfTypedElement.setEType(getType(((TypedElement)mofObject).getType()));
	}
	
	private EClassifier getType(Type type) {
		if (type instanceof UmlClass) {
			return (EClassifier)fFluxBox.getObject(type, EcorePackage.eINSTANCE.getEClass());
		} else if (type instanceof Enumeration) {
			return (EClassifier)fFluxBox.getObject(type, EcorePackage.eINSTANCE.getEEnum());
		} else if (type instanceof PrimitiveType) {
			if (type.getName().equals(Boolean.class.getSimpleName())) {
				return EcorePackage.eINSTANCE.getEBoolean();
			} else if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
				return EcorePackage.eINSTANCE.getEString();
			} else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
				return EcorePackage.eINSTANCE.getEInt();
			} else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
				return EcorePackage.eINSTANCE.getEInt();
			} else {
				return (EClassifier)fFluxBox.getObject(type, EcorePackage.eINSTANCE.getEDataType());	
			}			
		} else if (type == null) { 
			return null;
		} else {
			throw new RuntimeException("not implemented");
		}
	}
}
