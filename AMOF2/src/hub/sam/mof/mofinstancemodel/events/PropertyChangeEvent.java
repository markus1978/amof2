package hub.sam.mof.mofinstancemodel.events;

import cmof.Property;

public class PropertyChangeEvent {
	
	private final Property property;

	public PropertyChangeEvent(final Property property) {
		super();
		this.property = property;
	}
	
	public Property getProperty() {
		return property;
	}
	
}
