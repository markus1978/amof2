/**
 * 
 */
package hub.sam.tools.packagemerge;

import java.util.Collection;

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
import cmof.common.ReflectiveCollection;
/**
 * @author kon
 *
 */
/**
 * @author kon
 *
 */
public interface ElementSelector {

	
	/**
	 * Chooses from a list of given Classes the one which will be generalizing the according class.
	 * 
	 * @param classes the list of possible generalizers
	 * @param umlClass the class to be generalized
	 * @return the generalizing class
	 */
	public UmlClass getSelectedUmlClass(ReflectiveCollection<UmlClass> classes, UmlClass umlClass);
	
	/**
	 * Chooses from a list of given Associations the one which will be generalizing the according association.
	 * 
	 * @param associations the list of possible generalizers
	 * @param association the association to be generalized
	 * @return the generlaized association
	 */
	public Association getSelectedAssociation(ReflectiveCollection<Association> associations, Association association);
	
	/**
	 * Selects from the list of generalizing Associations the features to be redefined or inhereted.
	 * 
	 * @param associations the list of associations which contain the possible features
	 * @param association the generalized and features containing association
	 * @return the features to be redefined
	 */
	public Collection<Feature> getAllFeatures(ReflectiveCollection<Association> associations, Association association);
	/**
	 * Selects from the list of generalizing Classes the features to be redefined or inhereted.
	 * 
	 * @param classes the list of classes which contain the possible features
	 * @param umlClass the generalized and features containing class
	 * @return the generalized and features containing association
	 */
	public Collection<Feature> getAllFeatures(ReflectiveCollection<UmlClass> classes, UmlClass umlClass);
	
	/**
	 * @return an instance of a cmof Class without any properties set
	 */
	public UmlClass getUmlClass();
	/**
	 * @return an instance of a cmof Association without any properties set
	 */
	public Association getAssociation();
	/**
	 * @return an instance of a cmof Package without any properties set
	 */
	public Package getPackage();
	/**
	 * @return an instance of a cmof Operation without any properties set
	 */
	public Operation getOperation();
	/**
	 * @return an instance of a cmof Property without any properties set
	 */
	public Property getProperty();
	
	/**
	 * @return an instance of a cmof BehavioralFeature  without any properties set
	 */
	public Parameter getParameter();
}
