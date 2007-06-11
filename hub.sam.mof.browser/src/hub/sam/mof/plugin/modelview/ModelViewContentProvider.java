package hub.sam.mof.plugin.modelview;

import java.util.*;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.tree.InvisibleTreeRoot;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeParent;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.internal.ViewSite;

public class ModelViewContentProvider implements IStructuredContentProvider, ITreeContentProvider {

	private TreeViewer treeViewer;
	private final Filter filter;

	private InvisibleTreeRoot invisibleRoot;
	
	public ModelViewContentProvider(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
		filter = new Filter();
	}

    public ModelViewContentProvider() {
        this(null);
    }
    
    public void setTreeViewer(TreeViewer treeViewer) {
        this.treeViewer = treeViewer;
    }
	
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		// empty
	}
	
	public void dispose() {
		invisibleRoot.delete();
	}
	
	public Object[] getElements(Object parent) {
		/*if (parent.equals(view.getViewSite())) {
			if (invisibleRoot == null) initialize();
			return getChildren(invisibleRoot);
		}*/
        if (parent instanceof ViewSite || parent == null) {
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
		invisibleRoot = new InvisibleTreeRoot(treeViewer);
	}
	
	public TreeParent getRoot() {
		return invisibleRoot;
	}
	
	public void addRepository(Repository repository) {
		if (invisibleRoot == null) {
			initialize();
		}
		invisibleRoot.addChild(new RepositoryTreeObject(repository, invisibleRoot, treeViewer));
        if (treeViewer != null) {
            //treeViewer.refresh();
        }
	}
    
	public void addClassToFilter(Class filter) {
		this.filter.addClassToFilter(filter);
	}
}