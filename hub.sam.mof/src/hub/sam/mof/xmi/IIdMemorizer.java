package hub.sam.mof.xmi;

/**
 * IIdMemorizer can be used to follow an objects by ids, even if these ids
 * change with changing object representations. When, for example, an object is
 * loaded from xmi it is first represented as DOM-element than as an
 * ClassInstance of an XmiClassifier than as an MofClassInstance. Each
 * representation comes with its own id and the id of the MofClassInstance has
 * nothing to do with the original id.
 * 
 * This interface is used during xmi reading, transformations, and conversions.
 * Implementing clients can use it to keep track of the id changes and later on
 * reconstruct the original id of an object based on stored id changes.
 */
public interface IIdMemorizer {

	/**
	 * Is called whenever an object changes its id, while it changes its
	 * representation. When the object is created without having a further
	 * representation in the repository it is called with oldId=null;
	 */
	public void idChanges(Object oldId, Object newId);
	
}
