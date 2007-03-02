/**
 * 
 */
package hub.sam.tools.packagemerge;
import hub.sam.mof.util.SetImpl;
import hub.sam.util.AbstractFluxBox;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import cmof.Association;
import cmof.Classifier;
import cmof.Feature;
import cmof.NamedElement;
import cmof.Operation;
import cmof.Package;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;


/**
 * @author kon
 *
 */
public class PackageMerge {
	
	public PackageMerge(boolean isCombine) {
		selector = null;
		this.isCombine = isCombine;
		classesByName = new Vector<HashMap<String,UmlClass>>();
		associationsByName = new Vector<HashMap<String,Association>>();
		operationsByName = new Vector<HashMap<String,Operation>>();
		propertiesByName = new Vector<HashMap<String,Property>>();
		packagesByName = new Vector<HashMap<String,Package>>();
	}
	
	private AbstractFluxBox<String, UmlClass, ElementSelector> classFluxBox = 
		new AbstractFluxBox<String, UmlClass, ElementSelector>() {
		@Override
		protected UmlClass createValue(String key, ElementSelector selector) {
			return selector.getUmlClass();
		}
	};
	
	private AbstractFluxBox<String, Association, ElementSelector> associationFluxBox = 
		new AbstractFluxBox<String, Association, ElementSelector>() {
		@Override
		protected Association createValue(String key, ElementSelector selector) {
			return selector.getAssociation();
		}
	};
	
	private List<HashMap<String, UmlClass>> classesByName;
	private List<HashMap<String, Association>> associationsByName;
	private List<HashMap<String, Operation>> operationsByName;
	private List<HashMap<String, Property>> propertiesByName;
	private List<HashMap<String, Package>> packagesByName;
	
	
	private ElementSelector selector;
	
	private boolean isCombine;
	
	public void merge(Package source, Package target, ElementSelector selector) {
		List<Package> packages = new Vector<Package>(1);
		packages.add(target);
		merge(packages, source, selector);
	}
	
	public void  merge(List<Package> packages, Package mergingPackage, ElementSelector selector) {
		this.selector = selector;
		for (Package mergedPackage : packages) {
			HashMap<String, UmlClass> tempClassesByName = new HashMap<String, UmlClass>();
			HashMap<String, Association> tempAssociationsByName = new HashMap<String, Association>();
			HashMap<String, Operation> tempOperationsByName = new HashMap<String, Operation>();
			HashMap<String, Property> tempPropertiesByName = new HashMap<String, Property>();
			HashMap<String, Package> tempPackagesByName = new HashMap<String, Package>();
			for (NamedElement member : mergedPackage.getMember()) {
				if (member instanceof UmlClass) {
					tempClassesByName.put(((UmlClass)member).getName(), (UmlClass)member);
				} else if (member instanceof Association) {
					tempAssociationsByName.put(((Association)member).getName(), (Association)member);
				} else if (member instanceof Operation) {
					tempOperationsByName.put(((Operation)member).getName(), (Operation)member);
				} else if (member instanceof Property) {
					tempPropertiesByName.put(((Property)member).getName(), (Property)member);
				} else if (member instanceof Package) {
					tempPackagesByName.put(((Package) member).getName(), (Package) member);
				}
			}
			classesByName.add(tempClassesByName);
			associationsByName.add(tempAssociationsByName);
			operationsByName.add(tempOperationsByName);
			propertiesByName.add(tempPropertiesByName);
			packagesByName.add(tempPackagesByName);
		}
		HashMap<String, UmlClass> mergingClasses = new HashMap<String, UmlClass>();
		HashMap<String, Association> mergingAssociations = new HashMap<String, Association>();
		HashMap<String, Package> mergingSubPackages =new HashMap<String, Package>();
		for (NamedElement element : mergingPackage.getMember()) {
			if (element instanceof UmlClass) {
				mergingClasses.put(((UmlClass) element).getName(), (UmlClass) element);
			} else if (element instanceof Association) {
				mergingAssociations.put(((Association) element).getName(), (Association) element);
			} else if (element instanceof Package) {
				mergingSubPackages.put(((Package) element).getName(), (Package) element);
			}
		}
		mergeSubPackage(mergingPackage, mergingSubPackages);
		mergeClasses(mergingPackage, mergingClasses);
		mergeAssociations(mergingPackage, mergingAssociations);
	}
	
	public void mergeSubPackage (Package source, HashMap<String, Package> subPackages)	{;
	for (HashMap<String, Package> packageMap : packagesByName ) {
		for (Package element: packageMap.values()) {
			List<Package> mergedPackages = new Vector<Package>();
			for (HashMap<String, Package> map : packagesByName ) {
				mergedPackages.add(map.get(element.getName()));
			}
			Package mergingPackage;
			if (subPackages.containsKey(element.getName())) {
				mergingPackage = subPackages.get(element.getName());
			} else {
				mergingPackage = selector.getPackage();
				mergingPackage.setNestingPackage(source);
				mergingPackage.setName(element.getName());
			}
			new PackageMerge(isCombine).merge(mergedPackages, mergingPackage, selector);
			/*
			 // remove all yet merged packages
			  for (HashMap<String, Package> map : packagesByName ) {
			  map.remove(element.getName());
			  }
			  */
		}
	}
	}
	
	
	public void mergeClasses(Package source,  HashMap<String, UmlClass> classes) {
		for (HashMap<String, UmlClass> classesMap : classesByName) {
			UmlClass selectedClassifier = null;
			for (UmlClass umlClass : classesMap.values()) {
				ReflectiveCollection<UmlClass> mergedClasses = new SetImpl<UmlClass>();
				for (HashMap<String, UmlClass> temp : classesByName) {
					mergedClasses.add(temp.get(umlClass.getName()));
				}
				UmlClass mergingClass;
				if (classes.containsKey(umlClass.getName())) {
					mergingClass = classes.get(umlClass.getName());
				} else {
					mergingClass = classFluxBox.getObject(umlClass.getName(), selector);
					//  TODO insert copy command
					mergingClass.setName(umlClass.getName());
					mergingClass.setPackage(source);
				}
				selectedClassifier = selector.getSelectedUmlClass(mergedClasses, umlClass);
				if (!isCombine) {
					umlClass.getGeneral().add(selectedClassifier);
					umlClass.getGeneral().add(selector.getSelectedUmlClass((ReflectiveCollection<UmlClass>) selectedClassifier.getSuperClass(), selectedClassifier));
					source.getPackageImport().add(selector.getSelectedUmlClass((ReflectiveCollection<UmlClass>) selectedClassifier.getSuperClass(), selectedClassifier).getPackage());
				}
				// redefine the features
				redefineFeatures(mergingClass, selector.getAllFeatures(mergedClasses, mergingClass));
				/*
				 for (HashMap<String, UmlClass> temp : classesByName) {
				 temp.remove(umlClass.getName());
				 }
				 */
			} 
		}
	}
	
	public void mergeAssociations(Package source,  HashMap<String, Association> associations) {
		for (HashMap<String, Association> associationsMap : associationsByName) {
			Association selectedClassifier = null;
			for (Association association : associationsMap.values()) {
				ReflectiveCollection<Association> mergedAssociations = new SetImpl<Association>();
				for (HashMap<String, Association> temp : associationsByName) {
					mergedAssociations.add(temp.get(association.getName()));
				}
				Association mergingAssociation;
				if (associations.containsKey(association.getName())) {
					mergingAssociation = associations.get(association.getName());
				} else {
					mergingAssociation = associationFluxBox.getObject(association.getName(), selector);
					//  TODO insert copy command
					mergingAssociation.setName(association.getName());
					mergingAssociation.setPackage(source);
				}
				selectedClassifier = selector.getSelectedAssociation(mergedAssociations, association);
				if (!isCombine) {
					association.getGeneral().add(selectedClassifier);
					association.getGeneral().add(selector.getSelectedAssociation((ReflectiveCollection<Association>) selectedClassifier.getGeneral(), selectedClassifier));
					source.getPackageImport().add(selector.getSelectedAssociation((ReflectiveCollection<Association>) selectedClassifier.getGeneral(), selectedClassifier).getPackage());
				}
				// redefine the features
				redefineFeatures(mergingAssociation, selector.getAllFeatures(mergedAssociations, mergingAssociation));
				/*
				 for (HashMap<String, Association> temp : associationsByName) {
				 temp.remove(association.getName());
				 }
				 */
			}
		}
	}
	
	
	
	
	public void redefineFeatures(Classifier classifier, Collection<Feature> features) {
		for (Feature feature: features) {
			if (feature instanceof  Operation) {
				Operation operation = (Operation) feature;
				// if the feature is an operation the classifier has to be a class
				UmlClass umlClass = (UmlClass) classifier;
				Operation redefiningOperation = selector.getOperation();
				// TODO insert copy command
				redefiningOperation.setType(classFluxBox.getObject(operation.getType().getName(), selector));
				redefiningOperation.getRedefinedElement().add(operation);
				redefiningOperation.setName(operation.getName());
				umlClass.allFeatures().add(redefiningOperation);
				
			} else if (feature instanceof Property) {
				Property property = (Property) feature;
				Property redefinigProperty = selector.getProperty();
				// TODO insert copy command
				redefinigProperty.getRedefinedElement().add(property);
				redefinigProperty.setName(property.getName());
				redefinigProperty.setType(classFluxBox.getObject(property.getType().getName(), selector));
			}
		}
	}
	
	
	
	
	
	
}
