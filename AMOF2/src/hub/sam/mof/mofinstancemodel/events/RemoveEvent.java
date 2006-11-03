package hub.sam.mof.mofinstancemodel.events;

import hub.sam.mof.instancemodel.ValueSpecification;
import cmof.Property;
import cmof.UmlClass;

public class RemoveEvent extends PropertyChangeEvent {
	
	private final int fPosition;
	private final ValueSpecification<UmlClass,Property,Object> removedValue;
	
	public RemoveEvent(Property property, final int position, final ValueSpecification<UmlClass, Property, Object> removedValue) {
		super(property);
		fPosition = position;
		this.removedValue = removedValue;
	}

	public int getPosition() {
		return fPosition;
	}

	public ValueSpecification<UmlClass, Property, Object> getValue() {
		return removedValue;
	}
	
}
