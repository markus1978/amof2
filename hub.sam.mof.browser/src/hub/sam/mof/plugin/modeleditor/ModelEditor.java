package hub.sam.mof.plugin.modeleditor;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.IModelTreeContentContentProvider;
import hub.sam.mof.plugin.modelview.ModelViewActionManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import cmof.Package;
import cmof.reflection.Extent;

public class ModelEditor extends EditorPart {

	private static final Repository REPOSITORY = Repository.getLocalRepository();
	private static final Extent CMOF_EXTENT = REPOSITORY.getExtent(Repository.CMOF_EXTENT_NAME);
	private static final Package CMOF_PACKAGE = (Package) CMOF_EXTENT.query("Package:cmof");
	
	private final ModelViewActionManager actions = new ModelViewActionManager();

	private Extent extent;
	
	/**
	 * 
	 */
	public ModelEditor() {

		super();
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		
		setSite(site);
		setInput(input);
		
		if (input instanceof IPathEditorInput) {
			
			String path = ((IPathEditorInput) input).getPath().toOSString();
			
			extent = REPOSITORY.createExtent(path, CMOF_EXTENT);
			try{
				if (path.endsWith(".xml"))
					REPOSITORY.loadXmiIntoExtent(extent, CMOF_PACKAGE, path);
				else if (path.endsWith(".mdxml"))
					REPOSITORY.loadMagicDrawXmiIntoExtent(extent, CMOF_PACKAGE, path);
			} catch (Exception e) {
				throw new PartInitException("Could not load file into extent.", e);
			}
		} else
			throw new PartInitException("Wrong resource.");
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		actions.createPartControl(parent, new IModelTreeContentContentProvider() {
			public IStructuredContentProvider getContentProvider(TreeViewer viewer) {
				ExtentViewContentProvider result = new ExtentViewContentProvider(ModelEditor.this, viewer);
				result.setExtent(extent);
				return result;
			}			
		}, getEditorSite(), getSite());
	}

	/**
	 * 
	 */
	@Override
	public void setFocus() {
		
		actions.getViewer().getControl().setFocus();
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		
		return true;
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		
		IEditorInput output = getEditorInput();
		if(output instanceof IPathEditorInput)
			try{
				String path = ((IPathEditorInput) output).getPath().toOSString();
				if (path.endsWith(".xml"))
					REPOSITORY.writeExtentToXmi(path, CMOF_PACKAGE, extent);
				else if (path.endsWith(".mdxml"))
					REPOSITORY.writeExtentToMagicDrawXmi(path, CMOF_PACKAGE, extent);
			} catch (Exception e) {
				// TODO: handle exception
			}
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		
		return false;
	}

	/**
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {						
		super.dispose();
		REPOSITORY.deleteExtent(extent);
	}
}
