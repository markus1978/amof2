package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class AddModelAction extends ContextAction {
	
	public AddModelAction(TreeViewer view) {
		super(view);		
		setText("Add model ...");
		setToolTipText("Add a extent with a new model to the repository.");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
	}
	
	@Override
	public void runFor(TreeObject obj) {	
		Display display=Display.getCurrent();
	    Shell shell= new Shell(display);
        AddModelDialog dialog = new AddModelDialog(shell, view);
	    dialog.open();
	    shell.dispose();	
	}
	
	@Override
	public boolean isEnabledFor(TreeObject selection) {
		return selection instanceof hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;		
	}
    
}
