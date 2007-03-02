/**
 * 
 */
package hub.sam.mof.plugin.modeleditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * @author guwac
 *
 */
public class NewModelWizard extends Wizard implements INewWizard {

	private NewModelWizardPage page;
	private IStructuredSelection selection;
	
	/**
	 * 
	 */
	public NewModelWizard() {
		
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
	
		final IFile file = page.createNewFile();
		
		if (file == null)
			return false;
		
		final IFile file2 = file;
		
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file2, true);
				} catch (PartInitException e) {
		
				}
			}
		});
		
		return true;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		
		super.addPages();
		page = new NewModelWizardPage("New CMOF Model", selection);
		addPage(page);
	}
}
