package hub.sam.mof.plugin.modelview.tree;

import org.eclipse.jface.viewers.TreeViewer;


public class ManTreeObject extends TreeParent {

	private final IBuilderFactory factory;
	
	public ManTreeObject(java.lang.Object obj, TreeParent parent, IBuilderFactory factory, TreeViewer view) {
		super(obj, parent, view);
		this.factory = factory;
	}
	
	protected TreeObject build(java.lang.Object obj) {
		return factory.getBuilder(obj).create(obj, this, factory, getView());
	}
}
