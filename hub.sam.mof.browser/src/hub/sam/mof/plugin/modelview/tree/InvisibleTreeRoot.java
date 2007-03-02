package hub.sam.mof.plugin.modelview.tree;


import java.util.Collection;

import org.eclipse.jface.viewers.TreeViewer;

public class InvisibleTreeRoot extends TreeParent {

	private Collection<TreeObject> children;
	
	public InvisibleTreeRoot(TreeViewer view) {
		super(null, null, view);
		children = new java.util.ArrayList<TreeObject>();
	}
	
	public void removeChild(TreeObject child) {
		children.remove(child);
	}
	
	public void addChild(TreeObject child) {
		children.add(child);
	}
	
	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		return children;
	}

}
