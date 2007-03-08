package hub.sam.mof.plugin.modeleditor;

import hub.sam.mof.plugin.modelview.Filter;
import hub.sam.mof.plugin.modelview.tree.ExtentTreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeParent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorPart;

import cmof.reflection.Extent;

public class ExtentViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

	private final IEditorPart editorPart;
	private final TreeViewer view;
	private final Filter filter;
	private ExtentTreeObject invisibleRoot;	
	private RootTreeParent realInvisibleRoot;
	private Extent extent;
	
	public ExtentViewContentProvider(IEditorPart editorPart, TreeViewer view) {		
		this.editorPart = editorPart;
		this.view = view;
		this.filter = new Filter();
	}
	
	public void setExtent(Extent extent) {
		this.extent = extent;
	}
	
	public Object[] getElements(Object parent) {
		if (parent.equals(editorPart.getEditorSite())) {
			if (realInvisibleRoot == null) initialize();
			return getChildren(realInvisibleRoot);
		}
		return getChildren(parent);
	}
    
	public Object getParent(Object child) {
		if (child instanceof TreeObject) {
			return ((TreeObject)child).getParent();
		}
		return null;
	}
    
	public Object [] getChildren(Object parent) {
		if (parent instanceof TreeParent) {
			Collection children = ((TreeParent)parent).getChildren();
			Collection<Object> filteredChildren = new ArrayList<Object>(children.size());
			for (Object child: children) {
				if (!filter.isFiltered((TreeObject)child)) {
					filteredChildren.add(child);
				}
			}
			return filteredChildren.toArray();
		}
		return new Object[0];
	}
    
	public boolean hasChildren(Object parent) {
		if (parent instanceof TreeParent)
			return ((TreeParent)parent).hasChildren();
		return false;
	}
	
	class RootTreeParent extends TreeParent {
		public RootTreeParent(TreeViewer view) {
			super(null, null, view);
		}

		private final Collection<TreeObject> children = new Vector<TreeObject>();
		
		@Override		
		protected Collection<TreeObject> retrieveChildren() {
			return children;
		}
		
		void addChild(TreeObject child) {
			children.add(child);
		}
		
	}

	private void initialize() {
		if (extent == null) {
			throw new RuntimeException("Extent must not be null");
		}
		realInvisibleRoot = new RootTreeParent(view);		
		invisibleRoot = new ExtentTreeObject(extent, "Model", realInvisibleRoot, view);
		realInvisibleRoot.addChild(invisibleRoot);		
	}
	
	public TreeParent getRoot() {
		return realInvisibleRoot;
	}

	public void dispose() {
		// empty
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// emtpy	
	}
}
