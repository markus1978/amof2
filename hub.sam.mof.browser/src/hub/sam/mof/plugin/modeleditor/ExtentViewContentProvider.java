package hub.sam.mof.plugin.modeleditor;

import hub.sam.mof.plugin.modelview.Filter;
import hub.sam.mof.plugin.modelview.tree.ExtentTreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeParent;

import java.util.ArrayList;
import java.util.Collection;

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
			if (invisibleRoot == null) initialize();
			return getChildren(invisibleRoot);
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

	private void initialize() {
		if (extent == null) {
			throw new RuntimeException("Extent must not be null");
		}
		invisibleRoot = new ExtentTreeObject(extent, "AnExtentForExiting", null, view);
	}
	
	public TreeParent getRoot() {
		return invisibleRoot;
	}

	public void dispose() {
		// empty
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// emtpy	
	}
}
