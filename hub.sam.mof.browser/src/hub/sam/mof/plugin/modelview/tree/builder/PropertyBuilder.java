package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import cmof.Namespace;
import cmof.Property;

public class PropertyBuilder extends TypedElementBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {
		Property property = (Property)obj;
		TreeObject to = null;
		
		for (Property redef: property.getRedefinedProperty()) {
			to = mgr.addChild(redef);
			to.setImage(Images.getDefault().getRedefinition());
			to.setCategory(Categories.REDEFINITION);
			to.setText(redef.getNamespace().getQualifiedName(), to.getText());
		}
		for (Property subset: property.getSubsettedProperty()) {
			to = mgr.addChild(subset);
			to.setImage(Images.getDefault().getSubsets());
			to.setCategory(Categories.SUBSETS);
			Namespace ns = subset.getNamespace();
			if (ns == null) {
				ns = subset.getAssociation();
				if (ns == null) {
					to.setText("<global>", to.getText());
				} else {					
					if (ns.getName() != null ) {
						to.setText(ns.getQualifiedName(), to.getText());
					} else {
						to.setText("<association>", to.getText());
					}
				}
			} else {
				to.setText(subset.getNamespace().getQualifiedName(), to.getText());
			}
		}
		Property opposite = property.getOpposite();
		if (opposite != null) {
			to = mgr.addChild(opposite);
			to.setCategory(Categories.OPPOSITE);
			to.setImage(Images.getDefault().getAssociation());
			Namespace ns = opposite.getNamespace();
			if (ns != null) {
				to.setText(ns.getQualifiedName(), to.getText());
			} else {
				to.setText("null", to.getText());
			}
		}
		super.addChildren(obj, mgr, view);
	}
	
	@Override
	public String getText(Object obj) {
		Property property = (Property)obj;
		String result = super.getText(obj);
		if (property.isDerived()) {
			result = "/" + result;
		}
		if (property.isDerivedUnion()) {
			result += ", union";
		}
		
		return result;
	}
	
	@Override
	public Image getImage(Object obj) {
		return Images.getDefault().getProperty();
	}

	@Override
	public int getCategory(Object obj) {
		return Categories.PROPERTY;
	}

}
