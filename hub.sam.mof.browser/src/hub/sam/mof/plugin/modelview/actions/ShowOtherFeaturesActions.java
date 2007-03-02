package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TreeViewer;

public abstract class ShowOtherFeaturesActions extends ContextAction {
	public ShowOtherFeaturesActions(TreeViewer view, String text) {
		super(view, text, IAction.AS_RADIO_BUTTON);			
	}

	@Override
	protected boolean isEnabledFor(TreeObject obj) {
		boolean isEnabledFor = obj.getContext() instanceof IShowOtherFeaturesContext;				
		if (isEnabledFor) {
			setChecked(isShowing(((IShowOtherFeaturesContext)obj.getContext()), obj));
		}
		return isEnabledFor;
	}

	@Override
	protected void runFor(TreeObject obj) {
		IShowOtherFeaturesContext context = (IShowOtherFeaturesContext)obj.getContext();
		switchTo(context, obj);
		view.refresh(obj);
		
	}
	
	protected abstract boolean isShowing(IShowOtherFeaturesContext context, Object obj);
	
	protected abstract void switchTo(IShowOtherFeaturesContext context, Object obj);
}
