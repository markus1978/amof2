package hub.sam.mof.mofinstancemodel.events;

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.ValueSpecification;

public class InsertEvent extends PropertyChangeEvent {
	private final int fPosition;
	private final ValueSpecification<UmlClass, Property, Object> fValue;
	
	public InsertEvent(Property property, final int position, final ValueSpecification<UmlClass, Property, Object> value) {
		super(property);
		fPosition = position;
		fValue = value;
	}

	public int getPosition() {
		return fPosition;
	}

	public ValueSpecification<UmlClass, Property, Object> getValue() {
		return fValue;
	}
	
	
}
