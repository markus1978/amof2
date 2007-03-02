package hub.sam.mof.plugin.properties;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import cmof.Classifier;
import cmof.Property;

public class MOF2PropertyDescriptor implements IPropertyDescriptor {
	
	public static IPropertyDescriptor[] getPropertyDescriptors(Classifier forClassifier) {
		Collection<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		for (Object o: forClassifier.getMember()) {
			if (o instanceof Property) {
				descriptors.add(new MOF2PropertyDescriptor((Property)o));				
			}
		}
		return descriptors.toArray(new IPropertyDescriptor[] {});
	}
	
	private final Property property;
	
	private MOF2PropertyDescriptor(Property property) {
		this.property = property;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		return null;
	}

	public String getCategory() {
		return property.getNamespace().getQualifiedName();
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDisplayName() {
		return property.getName();
	}

	public String[] getFilterFlags() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getHelpContextIds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getId() {
		return property;
	}

	public ILabelProvider getLabelProvider() {
		return MOF2LabelProvider.getDefault();
	}

	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
		// TODO Auto-generated method stub
		return false;
	}

}
