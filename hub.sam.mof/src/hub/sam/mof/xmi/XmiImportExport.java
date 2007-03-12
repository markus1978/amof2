package hub.sam.mof.xmi;

import java.util.HashMap;
import java.util.Map;

import org.jdom.Element;
import org.jdom.Namespace;

public class XmiImportExport implements IIdMemorizer, IIdProvider {

	private final Map<Object, Object> ids = new HashMap<Object, Object>();
	private Element xmi;

	
	public void idChanges(Object oldId, Object newId) {
		Object veryOldId = ids.get(oldId);
		if (veryOldId != null) {			
			oldId = veryOldId;
		} 
		ids.put(newId, oldId);			
	}

	public Object getId(Object id) {
		Object result = ids.get(id);
		return (result != null) ? result : id;
	}
	
	
	protected void setXMI(Element xmi) {
		this.xmi = xmi;
		Element model = xmi.getChild("Model", (Namespace)xmi.getAdditionalNamespaces().get(0));
		model.detach();
	}
	
	protected Element getXMI() {
		return xmi;
	}
}
