package hub.sam.mof.plugin.modelview.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;


public abstract class AutomatedBuilder implements IBuilder {

	public TreeObject create(Object obj, TreeParent parent,
			IBuilderFactory factory, TreeViewer view) {
		TreeObject result = new BuildTreeObject(obj, parent, this, factory, view);	
		configureTreeObject(result, obj);
		return result;
	}
	
	protected void configureTreeObject(TreeObject to, Object obj) {
		to.setText(getText(obj));
		to.setImage(getImage(obj));
		to.setCategory(getCategory(obj));
	}
	
	public final  void refresh(TreeObject to) {
		configureTreeObject(to, to.getElement());
	}

	public abstract String getText(Object obj);
	
	public abstract Image getImage(Object obj);
	
	public abstract int getCategory(Object obj);
}
