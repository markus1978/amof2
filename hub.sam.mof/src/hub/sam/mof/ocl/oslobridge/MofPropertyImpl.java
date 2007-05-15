package hub.sam.mof.ocl.oslobridge;

import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Property;

import cmof.UmlClass;

public class MofPropertyImpl implements Property {

	private final cmof.Property property;
	private final OclProcessor processor;
	private Classifier propertyType = null;
	
	public MofPropertyImpl(cmof.Property sf, OclProcessor proc) {
		property = sf;
		this.processor = proc;
	}
	
	public Classifier getType() {
		if (propertyType == null) {
			if (property.getUpper() == -1 || property.getUpper() > 1) {
				if (property.isOrdered()) {
					if (property.isUnique()) {
						propertyType = this.processor.getTypeFactory().buildOrderedSetType(this.processor.getBridgeFactory().buildClassifier(property.getType()));
					} else {
						propertyType = this.processor.getTypeFactory().buildSequenceType(this.processor.getBridgeFactory().buildClassifier(property.getType()));
					}
				} else {
					if (property.isUnique()) {
						propertyType = this.processor.getTypeFactory().buildSetType(this.processor.getBridgeFactory().buildClassifier(property.getType()));
					} else {
						propertyType = this.processor.getTypeFactory().buildBagType(this.processor.getBridgeFactory().buildClassifier(property.getType()));
					}
				}
			} else {
				propertyType = this.processor.getBridgeFactory().buildClassifier(property.getType());
			}
		}
		return propertyType; 
	}

	public void setType(Classifier type) {
		propertyType = type;
	}

	@Override
	public Object clone() {
		return new MofPropertyImpl(property, this.processor);
	}

	private String _name;
	public String getName() {
		if (property != null)
			return property.getName();
		else
			return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this,obj);
	}

	public Object getDelegate() {
		return property;
	}

	@Override
	public String toString() {
	    return getName();
	}
	
    protected Boolean hasExtraGetter() {
    	UmlClass theClass = property.getUmlClass();
		if (theClass == null) {
			return Boolean.FALSE;
		} else {
			if ((property.getUpper() == 1 || property.getUpper() == 0) && MofClassifierSemantics.createClassClassifierForUmlClass(theClass).isCollectionProperty(property)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
    }
}
