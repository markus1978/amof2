package hub.sam.mof.plugin.modelview.actions;

import org.eclipse.jface.viewers.TreeViewer;

import hub.sam.mof.plugin.modelview.ModelView;

public class ShowInheritedFeaturesAction extends ShowOtherFeaturesActions {

	public ShowInheritedFeaturesAction(TreeViewer view) {
		super(view, "Show all inherited features");
	}

	@Override
	protected boolean isShowing(IShowOtherFeaturesContext context, Object obj) {
		return context.isShowingInheritedFeatures(obj);		
	}

	@Override
	protected void switchTo(IShowOtherFeaturesContext context, Object obj) {
		context.switchToInheritedFeatures(obj);
	}

}
