package hub.sam.mof.plugin.modelview;

import hub.sam.mof.Repository;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ModelView extends ViewPart {
	
	private final ModelViewActionManager actions = new ModelViewActionManager();

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class NameSorter extends ViewerSorter {
		// empty
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		actions.createPartControl(parent, new IModelTreeContentContentProvider() {
			public IStructuredContentProvider getContentProvider(TreeViewer viewer) {
				ModelViewContentProvider result = new ModelViewContentProvider(getViewer());
				result.addRepository(Repository.getLocalRepository());
				return result;
			}			
		}, getViewSite(), getSite());		
		actions.contributeToActionBars(getViewSite());
	}

	public TreeViewer getViewer() {
		return actions.getViewer();
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		getViewer().getControl().setFocus();
	}
}