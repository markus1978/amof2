package hub.sam.mof.plugin.modelview.tree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.PlatformUI;

import cmof.reflection.ObjectChangeListener;

public abstract class  TreeParent extends TreeObject {
	private Collection<TreeObject> children = null;
	private boolean isCacheValid = false;
	private final MyObjectChangeListener fObjectChangeListener = new MyObjectChangeListener();
	
	class MyObjectChangeListener implements ObjectChangeListener {

		public void handleDelete(cmof.reflection.Object object) {
			if (getParent() != null) {
				getParent().removeChild(TreeParent.this);
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {	
					public void run() {			
						getView().refresh(getParent());
					}				
				});
			}
		}		
	}
	
	public TreeParent(java.lang.Object element, TreeParent parent, TreeViewer view) {
		super(element, parent, view);		
		if (element instanceof cmof.reflection.Object) {
			((cmof.reflection.Object)element).addObjectEventHandler(fObjectChangeListener);
		} else {
			if (element != null && 
					element.getClass().getCanonicalName().equals(cmof.reflection.Object.class.getCanonicalName())) {
				throw new RuntimeException("Classpath problem");
			}			
		}
	}

	public Collection<TreeObject> getChildren() {
		if (children == null) {
			children = retrieveChildren();
		} else if (!isCacheValid()) {
			for(TreeObject child: children) {
				child.delete();
			}
			children = retrieveChildren();
		} 
		return children;
	}
	
	private void removeChild(TreeObject child) {
		children.remove(child);
	}
		
	private boolean isCacheValid() {
		return isCacheValid;
	}
	
	public void refresh() {
		if (isCacheValid) {			
			for (TreeObject to: getChildren()) {
				if (to instanceof TreeParent) {
					((TreeParent)to).refresh();
				}
			}			
			isCacheValid = false;
		}
	}
	
	public boolean hasChildren() {
		return getChildren().size() > 0;
	}
	
	protected  Collection<TreeObject> retrieveChildren() {
		isCacheValid = true;
		return null;
	}

	@Override
	public void delete() {
		Object element = getElement();
		if (element instanceof cmof.reflection.Object) {
            ((cmof.reflection.Object)element).removeObjectEventHandler(fObjectChangeListener);
		}
		if (children != null) {
			for (TreeObject child: children) {
				child.delete();
			}
		}
		super.delete();
	}	
}