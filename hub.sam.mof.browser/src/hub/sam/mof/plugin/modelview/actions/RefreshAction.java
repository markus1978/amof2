package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelViewContentProvider;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class RefreshAction extends Action {
		
	private final TreeViewer view;

	public RefreshAction(TreeViewer view) {
		this.view = view;
		setText("Refresh");	
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_REDO));
	}

	@Override
	public void run() {
		((ModelViewContentProvider)view.getContentProvider()).getRoot().refresh();
		view.refresh();
	}
}
