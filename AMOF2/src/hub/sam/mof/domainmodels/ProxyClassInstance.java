package hub.sam.mof.domainmodels;

import cmof.UmlClass;
import cmof.Tag;
import hub.sam.mof.mofinstancemodel.MofClassInstance;

public class ProxyClassInstance extends MofClassInstance {

	// this is a dirty hack to allow access to a constructor parameter needed in a superclass constructor.
	protected static UmlClass nextClassifier = null;
	
    private final UmlClass classifier;
    private Class theClass = null;

    protected ProxyClassInstance(UmlClass classifier, ProxyModelContext context) {    	
    	super(classifier, context.getModel());
    	this.classifier = classifier;
        for(Tag aTag: classifier.getTag()) {
            if (aTag.getName().equals(JavaModelGenerationDoclet.CLASS_PROXY_TAG)) {
                try {
                    theClass = Thread.currentThread().getContextClassLoader().loadClass(aTag.getValue());
                } catch (ClassNotFoundException e) {
                    throw new ProxyModelException("Class not found", e);
                }
            }
        }
        if (theClass == null) {
            throw new ProxyModelException("assert");
        }
    }

	@Override
    public boolean equals(Object obj) {		
		if (obj instanceof ProxyClassInstance) {
			if (classifier == null) {
				return ((ProxyClassInstance)obj).classifier.equals(nextClassifier);
			} else {
				return ((ProxyClassInstance)obj).classifier.equals(classifier);
			}
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
    	if (classifier == null) {
    		return nextClassifier.hashCode();
    	} else {
    		return classifier.hashCode();
    	}
    }

    @Override
    public String toString() {
        return "ClassClass of " + classifier.toString();
    }

    Class getTheClass() {
        return theClass;
    }
}
