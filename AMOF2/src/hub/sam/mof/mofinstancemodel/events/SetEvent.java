package hub.sam.mof.mofinstancemodel.events;

import hub.sam.mof.instancemodel.ValueSpecification;
import cmof.Property;
import cmof.UmlClass;

public class SetEvent extends PropertyChangeEvent {
	private final int fPosition;
	private final ValueSpecification<UmlClass,Property,Object> oldValue;
	private final ValueSpecification<UmlClass,Property,Object> newValue;
	public SetEvent(Property property, final int position, final ValueSpecification<UmlClass, Property, Object> oldValue, final ValueSpecification<UmlClass, Property, Object> newValue) {
		super(property);
		fPosition = position;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	public int getPosition() {
		return fPosition;
	}
	public ValueSpecification<UmlClass, Property, Object> getNewValue() {
		return newValue;
	}
	public ValueSpecification<UmlClass, Property, Object> getOldValue() {
		return oldValue;
	}
	
	
}
