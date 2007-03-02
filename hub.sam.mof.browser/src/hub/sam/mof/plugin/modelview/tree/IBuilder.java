package hub.sam.mof.plugin.modelview.tree;

import org.eclipse.jface.viewers.TreeViewer;


public interface IBuilder {
	public TreeObject create(Object obj, TreeParent parent, IBuilderFactory factory, TreeViewer view);
	
	public void refresh(TreeObject treeObj);
		
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view);
}
