package hub.sam.mof.plugin.modelview;

import java.util.*;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.tree.InvisibleTreeRoot;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeParent;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ModelViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

	private final ModelView view;
	private final Filter filter;

	private InvisibleTreeRoot invisibleRoot;
	
	public ModelViewContentProvider(ModelView view) {
		this.view = view;
		filter = new Filter();
	}
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		// empty
	}
	
	public void dispose() {
		// empty
	}
	
	public Object[] getElements(Object parent) {
		if (parent.equals(view.getViewSite())) {
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
		invisibleRoot = new InvisibleTreeRoot(view.getViewer());
	}
	
	public TreeParent getRoot() {
		return invisibleRoot;
	}
	
	public void addRepository(Repository repository) {
		if (invisibleRoot == null) {
			initialize();
		}
		invisibleRoot.addChild(new RepositoryTreeObject(repository, invisibleRoot, view.getViewer()));
		view.getViewer().refresh();
	}
	
	public void addClassToFilter(Class filter) {
		this.filter.addClassToFilter(filter);
	}
}