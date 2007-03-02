package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelViewContentProvider;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;

public class AddToFilteredClassesAction extends ContextAction {

	public AddToFilteredClassesAction(TreeViewer view) {
		super(view);
		setText("Filter Objects of same class");		
	}

	@Override
	protected boolean isEnabledFor(TreeObject obj) {
		if (obj.getElement() instanceof cmof.reflection.Object) {
			return true;
		}
		return false;
	}

	@Override
	protected void runFor(TreeObject obj) {
		((ModelViewContentProvider)view.getContentProvider()).addClassToFilter(obj.getElement().getClass());
		view.refresh();		
	}
}
