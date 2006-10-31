/**
 * 
 */
package hub.sam.tools.packagemerge;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cmof.Association;
import cmof.BehavioralFeature;
import cmof.Classifier;
import cmof.Feature;
import cmof.Operation;
import cmof.Package;
import cmof.Parameter;
import cmof.Property;
import cmof.StructuralFeature;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.common.ReflectiveCollection;

/**
 * @author Konrad Voigt
 * 
 * Default implementation of the selector interface. Chooses first class and first features for inhertiance. 
 *
 */
public class ElementSelectorImpl implements ElementSelector {
	
	private cmofFactory factory;
	
	/**
	 * @param factory the generating factory
	 */
	public ElementSelectorImpl(cmofFactory factory) {
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getSelectedUmlClass(cmof.common.ReflectiveCollection, cmof.UmlClass, hub.sam.util.AbstractFluxBox)
	 */
	public UmlClass getSelectedUmlClass(ReflectiveCollection<UmlClass> classes,	UmlClass umlClass) {
		return classes.iterator().next();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getSelectedAssociation(cmof.common.ReflectiveCollection, cmof.Association, hub.sam.util.AbstractFluxBox)
	 */
	public Association getSelectedAssociation(ReflectiveCollection<Association> associations,	Association association) {
		return associations.iterator().next();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getSelectedAssociation(cmof.common.ReflectiveCollection, cmof.Association)
	 */
	public Classifier getSelectedSuperAssociation(ReflectiveCollection<Classifier> associations,	Association association) {
		return associations.iterator().next();
	}


	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getAllFeatures(cmof.common.ReflectiveCollection, cmof.Association)
	 */
	public Collection<Feature> getAllFeatures(ReflectiveCollection<Association> associations,		Association association) {
		HashMap<String, Feature> dominatingFeatures = new HashMap<String, Feature>();
		for (Association tempAss : associations) {
			for (Feature feature: tempAss.getFeature()) {
				if (!dominatingFeatures.containsKey(feature.getName())) 
					dominatingFeatures.put(feature.getName(), feature);
			}
		}
		return dominatingFeatures.values();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getAllFeatures(cmof.common.ReflectiveCollection, cmof.UmlClass)
	 */
	public Collection<Feature> getAllFeatures(ReflectiveCollection<UmlClass> classes,	UmlClass umlClass) {
		HashMap<String, Feature> dominatingFeatures = new HashMap<String, Feature>();
		for (UmlClass umlClasses : classes) {
			for (Feature feature: umlClasses.getFeature()) {
				if (!dominatingFeatures.containsKey(feature.getName())) 
					dominatingFeatures.put(feature.getName(), feature);
			}
		}
		return dominatingFeatures.values();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getUmlClass()
	 */
	public UmlClass getUmlClass() {
		return factory.createUmlClass();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getAssociation()
	 */
	public Association getAssociation() {
		return factory.createAssociation();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getPackage()
	 */
	public Package getPackage() {
		return factory.createPackage();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getOperation()
	 */
	public Operation getOperation() {
		return factory.createOperation();
	}

	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getProperty()
	 */
	public Property getProperty() {
		return factory.createProperty();
	}
	
	/* (non-Javadoc)
	 * @see hub.sam.mof.util.ElementSelector#getParameter()
	 */
	public Parameter getParameter() {
		return factory.createParameter();
	}

}
