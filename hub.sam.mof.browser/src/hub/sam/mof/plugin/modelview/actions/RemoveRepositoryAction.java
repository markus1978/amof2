package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.tree.InvisibleTreeRoot;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class RemoveRepositoryAction extends ContextAction {
		
	public RemoveRepositoryAction(TreeViewer view) {
		super(view);		
		setText("Remove");
		setToolTipText("Removes the repository from the view");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}
	
	@Override
	public void runFor(TreeObject toDelete) {			
		((InvisibleTreeRoot)toDelete.getParent()).removeChild(toDelete);
		view.refresh();
	}
	
	@Override
	public boolean isEnabledFor(TreeObject selection) {
		return selection instanceof hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;		
	}
}
