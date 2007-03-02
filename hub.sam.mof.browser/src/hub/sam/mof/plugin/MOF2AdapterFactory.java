package hub.sam.mof.plugin;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

public class MOF2AdapterFactory implements IAdapterFactory {

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (IPropertySource.class == adapterType) {
			return ((IAdaptable)adaptableObject).getAdapter(adapterType);
		}
		return null;
	}

	public Class[] getAdapterList() {
		return new Class[] {IPropertySource.class};
	}

}
