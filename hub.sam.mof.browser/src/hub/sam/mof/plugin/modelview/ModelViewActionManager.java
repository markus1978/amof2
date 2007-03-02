package hub.sam.mof.plugin.modelview;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.actions.AddModelAction;
import hub.sam.mof.plugin.modelview.actions.AddToFilteredClassesAction;
import hub.sam.mof.plugin.modelview.actions.RefreshAction;
import hub.sam.mof.plugin.modelview.actions.RemoveModelAction;
import hub.sam.mof.plugin.modelview.actions.ShowDetailsAction;
import hub.sam.mof.plugin.modelview.actions.ShowFinalFeaturesAction;
import hub.sam.mof.plugin.modelview.actions.ShowInheritedFeaturesAction;
import hub.sam.mof.plugin.modelview.actions.ShowOwnedFeaturesAction;
import hub.sam.mof.plugin.modelview.tree.builder.Categories;

import org.eclipse.core.internal.content.IContentConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.DrillDownAdapter;

public class ModelViewActionManager {

	private TreeViewer viewer;
	private IWorkbenchPartSite site;
	
	private DrillDownAdapter drillDownAdapter;
	private RefreshAction refreshAction;
	private AddModelAction addModel;
	private AddToFilteredClassesAction addToFilteredClasses;
	private ShowDetailsAction showDetails;
	private ShowInheritedFeaturesAction showInheritedFeatures;
	private ShowFinalFeaturesAction showFinalFeatures;
	private ShowOwnedFeaturesAction showOwnedFeatures;
	private Action setFilter;
    private RemoveModelAction removeModel;

	public void createPartControl(Composite parent, IModelTreeContentContentProvider contentContentProvider, Object input, IWorkbenchPartSite site) {				
		this.site = site;
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(contentContentProvider.getContentProvider(viewer));
        		
		viewer.setLabelProvider(ModelViewLabelProvider.getDefault());			
		viewer.setInput(input);
		viewer.setSorter(new Categories());
		site.setSelectionProvider(viewer);
		
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
	}

	public TreeViewer getViewer() {		
		return viewer;		
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#MOF2PluginPopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);						
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		site.registerContextMenu("hub.sam.mof.plugin.popupMenu", menuMgr, viewer);
	}
	
	public void contributeToActionBars(IViewSite viewSite) {
		IActionBars bars = viewSite.getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(refreshAction);
		manager.add(new Separator());
		manager.add(setFilter);
	}

	private void fillContextMenu(IMenuManager manager) {		
        manager.add(new GroupMarker("topAdditions"));       
        manager.add(new Separator());      
        manager.add(new GroupMarker("modelBrowser"));
        
		addModel.setEnabled(addModel.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.appendToGroup("modelBrowser", addModel);
		
		addToFilteredClasses.setEnabled(addToFilteredClasses.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.appendToGroup("modelBrowser", addToFilteredClasses);
		showDetails.setEnabled(showDetails.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.appendToGroup("modelBrowser", showDetails);        
        removeModel.setEnabled(removeModel.shouldEnable((IStructuredSelection)viewer.getSelection()));
        manager.appendToGroup("modelBrowser", removeModel);
		
		manager.add(new Separator());
		manager.add(new GroupMarker("modelFeatures"));
		
		showInheritedFeatures.setEnabled(showInheritedFeatures.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.appendToGroup("modelFeatures", showInheritedFeatures);
		showFinalFeatures.setEnabled(showFinalFeatures.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.appendToGroup("modelFeatures", showFinalFeatures);
		showOwnedFeatures.setEnabled(showOwnedFeatures.shouldEnable((IStructuredSelection)viewer.getSelection()));
		manager.appendToGroup("modelFeatures", showOwnedFeatures);		
		manager.add(new Separator());
		
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here

        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));        
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {                
		refreshAction = new RefreshAction(viewer);
		addModel = new AddModelAction(viewer);
		addToFilteredClasses = new AddToFilteredClassesAction(viewer);
		showDetails = new ShowDetailsAction(viewer);
		showInheritedFeatures = new ShowInheritedFeaturesAction(viewer);
		showOwnedFeatures = new ShowOwnedFeaturesAction(viewer);
		showFinalFeatures = new ShowFinalFeaturesAction(viewer);
        removeModel = new RemoveModelAction(viewer);
		
		setFilter = new Action() {
			@Override
			@SuppressWarnings("synthetic-access")
			public void run() {
				showMessage("setFilter executed");
			}
		};
		setFilter.setText("Filter ...");
		setFilter.setToolTipText("Configure the filter to constrain the kind model objects show in the tree.");
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@SuppressWarnings("synthetic-access")
			public void doubleClick(DoubleClickEvent event) {
				drillDownAdapter.goInto(((IStructuredSelection)event.getSelection()).getFirstElement());
			}
		});
	}
	
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Model View",
			message);
	}
}
