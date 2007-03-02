package hub.sam.mof.plugin.properties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import core.abstractions.namespaces.NamedElement;

import cmof.Property;
import cmof.common.ReflectiveCollection;

public class MOF2PropertySource implements IPropertySource {
	
	private final java.lang.Object element;
	
	public MOF2PropertySource(Object element) {
		this.element = element;
	}
	
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {				
		if (element != null && element instanceof cmof.reflection.Object) {
			return MOF2PropertyDescriptor.getPropertyDescriptors(((cmof.reflection.Object)element).getMetaClass());
		} else {
			return new IPropertyDescriptor[] {};
		}
	}
	
	private String getValueForValue(Object value) {
		if (value == null) {
			return "";
		}
		if (value instanceof ReflectiveCollection) {
			StringBuffer result = new StringBuffer("{");
			boolean first = true;
			for (Object singleValue: (ReflectiveCollection)value) {
				if (first) {
					first = false;
				} else {
					result.append(" ,");
				}
				result.append(getValueForValue(singleValue));				
			}
			result.append("}");
			return result.toString();
		} else if (value instanceof NamedElement){
			return ((NamedElement)value).getQualifiedName();
		} else {
			return value.toString();
		}	
	}

	public Object getPropertyValue(Object id) {
		if (element != null && element instanceof cmof.reflection.Object) {
			Object value = ((cmof.reflection.Object)element).get((Property)id);
			return getValueForValue(value);
		} else {
			return null;
		}
	}

	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}
}
