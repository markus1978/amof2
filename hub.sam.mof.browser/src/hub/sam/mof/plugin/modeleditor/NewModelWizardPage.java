/**
 * 
 */
package hub.sam.mof.plugin.modeleditor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * @author guwac
 *
 */
public class NewModelWizardPage extends WizardNewFileCreationPage {

	public NewModelWizardPage(String pageId, IStructuredSelection selection) {
		super(pageId, selection);
	}

	/**
	 * The framework calls this to see if the file is correct. 
	 */
	@Override
	protected boolean validatePage() {
		if (super.validatePage()) {
			
			String enteredExt = new Path(getFileName()).getFileExtension();
			if (enteredExt == null || !enteredExt.equals("xml")) {
				setErrorMessage("File name should end in .xml");
				return false;
			} else {
				setErrorMessage(null);
				setMessage(null);
				return true;
			}
		} else 
			return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
	 */
	@Override
	protected InputStream getInitialContents() {
		
		String content ="<XMI xmi:version='2.0\' xmlns:xmi='http://www.omg.org/XMI' xmlns:cmof='http://schema.omg.org/spec/mof/2.0/cmof.xmi'></XMI>";
		try{
			byte[] bytes = content.getBytes("utf-8");
			return new ByteArrayInputStream(bytes);
		} catch (Exception e) {
		}
		
		return null;
	}
}
