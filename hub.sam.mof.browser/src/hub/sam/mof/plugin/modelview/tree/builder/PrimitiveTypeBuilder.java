package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.tree.IChildManager;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import cmof.PrimitiveType;

public class PrimitiveTypeBuilder extends ClassifierBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {
		super.addChildren(obj, mgr, view);
	}

	@Override
	public int getCategory(Object obj) {
		return Categories.PRIMITIVETYPE;
	}

	@Override
	public Image getImage(Object element) {
		return Images.getDefault().getPrimitivetype();
	}

	@Override
	public String getText(Object element) {
		return ((PrimitiveType)element).getName();
	}
}
