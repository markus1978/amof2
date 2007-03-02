package hub.sam.mof.plugin.properties;

import org.eclipse.jface.viewers.LabelProvider;

public class MOF2LabelProvider extends LabelProvider {

	private final static MOF2LabelProvider instance = new MOF2LabelProvider();
	
	public static MOF2LabelProvider getDefault() {
		return instance;
	}
	
	private MOF2LabelProvider() {
		// empty
	}
	
	@Override
	public String getText(Object element) {
		return element.toString();
	}

}
