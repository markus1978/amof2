package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.tree.ExtentTreeObject;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class RemoveModelAction extends ContextAction {
	
	public RemoveModelAction(TreeViewer view) {
		super(view);		
		setText("Remove model ...");
		setToolTipText("Deletes the extent.");
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
                getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
        setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
                getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}
	
	@Override
	public void runFor(TreeObject toDelete) {
        RepositoryTreeObject repositoryTreeObject = (RepositoryTreeObject) toDelete.getParent();
        repositoryTreeObject.getChildren().remove(toDelete);
        repositoryTreeObject.getElement().deleteExtent( ((ExtentTreeObject) toDelete).getText() );
        view.refresh();
	}
	
	@Override
	public boolean isEnabledFor(TreeObject selection) {
		return selection instanceof hub.sam.mof.plugin.modelview.tree.ExtentTreeObject;		
	}
    
}
