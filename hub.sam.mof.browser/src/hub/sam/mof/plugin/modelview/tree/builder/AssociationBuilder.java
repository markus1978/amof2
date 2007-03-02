package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import cmof.Association;
import cmof.Property;

public class AssociationBuilder extends ClassifierBuilder {
	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {
		for(Property property: ((Association)obj).getMemberEnd()) {
			TreeObject to = mgr.addChild(property);
			if (property.getOwningAssociation() != null) {
				to.setText(to.getText() + ", ownedEnd");
			}
		}
		
		super.addChildren(obj, mgr, view);
	}

	
	
	@Override
	public String getText(Object element) {
		Association asso = (Association)element;
		String result = asso.getName();
		if (result == null) {
			return "<association>";
		} else  {		
			return result;
		}
	}

	@Override
	public Image getImage(Object element) {
		return Images.getDefault().getAssociation();
	}



	@Override
	public int getCategory(Object obj) {
		return Categories.ASSOCIATION;
	}
}
