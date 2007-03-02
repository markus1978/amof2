package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TreeViewer;

public class ShowDetailsAction extends ContextAction {

	public ShowDetailsAction(TreeViewer view) {
		super(view, "Show Details", IAction.AS_CHECK_BOX);			
	}

	@Override
	protected boolean isEnabledFor(TreeObject obj) {
		boolean isEnabledFor = obj.getContext() instanceof IShowDetailsContext;				
		if (isEnabledFor) {
			setChecked(((IShowDetailsContext)obj.getContext()).isShowingDetails(obj));
		}
		return isEnabledFor;
	}

	@Override
	protected void runFor(TreeObject obj) {
		IShowDetailsContext context = (IShowDetailsContext)obj.getContext();
		context.switchShowDetails(obj);		
		view.refresh(obj);
	}		
}
