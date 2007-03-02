package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import cmof.Enumeration;
import cmof.EnumerationLiteral;

public class EnumerationBuilder extends ClassifierBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {
		Enumeration enumeration = (Enumeration)obj;
		for (EnumerationLiteral element: enumeration.getOwnedLiteral()) {
			TreeObject to = new TreeObject(element, mgr.getParent(), view);
			to.setText(element.getName());
			to.setImage(Images.getDefault().getEnumvalue());
			mgr.addChild(to);
		}
		super.addChildren(obj, mgr, view);
	}

	@Override
	public int getCategory(Object obj) {
		return Categories.ENUMERATION;
	}

	@Override
	public Image getImage(Object element) {
		return Images.getDefault().getEnumeration();
	}

	@Override
	public String getText(Object element) {
		return ((Enumeration)element).getName();
	}

}
