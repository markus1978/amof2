package hub.sam.mof.plugin.modelview.actions;

import org.eclipse.jface.viewers.TreeViewer;

public class ShowOwnedFeaturesAction extends ShowOtherFeaturesActions {

	public ShowOwnedFeaturesAction(TreeViewer view) {
		super(view, "Show only owned features");
	}

	@Override
	protected boolean isShowing(IShowOtherFeaturesContext context, Object obj) {
		return context.isShowingOwnedFeatures(obj);
	}

	@Override
	protected void switchTo(IShowOtherFeaturesContext context, Object obj) {
		context.switchToOwnedFeatures(obj);
	}

}
