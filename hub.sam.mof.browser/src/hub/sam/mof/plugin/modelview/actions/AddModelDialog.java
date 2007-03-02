package hub.sam.mof.plugin.modelview.actions;

import hub.sam.mof.plugin.modelview.ModelView;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddModelDialog extends Dialog {

	Text classNameField;
	Text xmiFileField;
	private Button staticModelClassRadio;
	private Button xmiFileRadio;
	Button xmiBrowse;
	private String className = null;
    private final TreeViewer view;
	
	protected AddModelDialog(Shell parentShell, TreeViewer view) {
		super(parentShell);
		parentShell.setText("Add Model");
		this.className = "";
        this.view = view;
	}
	
	@Override
	public void create() {
		super.create();
		classNameField.setFocus();
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite main = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		main.setLayout(layout);
		main.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label messageLabel = new Label(main, SWT.WRAP);
		messageLabel.setText("Add a model to the repository.");
		GridData data = new GridData();
		data.horizontalSpan = 2;				
		messageLabel.setLayoutData(data);
		
		staticModelClassRadio = new Button(main, SWT.RADIO);
		staticModelClassRadio.setText("static model");
		data = new GridData();
		data.horizontalSpan = 2;				
		staticModelClassRadio.setLayoutData(data);	
		
		new Label(main, SWT.NONE).setText("class name:");
		classNameField = new Text(main, SWT.BORDER);
		classNameField.setText("");
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.ENTRY_FIELD_WIDTH);
		classNameField.setLayoutData(data);
		
		xmiFileRadio = new Button(main, SWT.RADIO);
		xmiFileRadio.setText("load xmi file");
		data = new GridData();
		data.horizontalSpan = 2;				
		xmiFileRadio.setLayoutData(data);
		
		new Label(main, SWT.NONE).setText("XMI file:");
		xmiFileField = new Text(main, SWT.BORDER);
		xmiFileField.setText("");
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.ENTRY_FIELD_WIDTH);
		xmiFileField.setLayoutData(data);
		
		xmiBrowse = new Button(main, SWT.BUTTON1);
		xmiBrowse.setText("browse");
		data = new GridData();
		data.horizontalSpan = 2;				
		xmiFileRadio.setLayoutData(data);		
		
		xmiFileRadio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				xmiFileField.setEnabled(true);
				xmiBrowse.setEnabled(true);
				classNameField.setEnabled(false);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);				
			}		
		});
		
		staticModelClassRadio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				classNameField.setEnabled(true);
				xmiFileField.setEnabled(false);	
				xmiBrowse.setEnabled(false);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);				
			}		
		});
		
		xmiBrowse.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {				
				FileDialog dialog = new FileDialog(getShell(), SWT.SINGLE);
				dialog.setFilterExtensions(new String[] { "*.xml;*.mdxml" }); //$NON-NLS-1$;				

				String result = dialog.open();
				if (result == null) {
					return;
				}
				IPath filterPath= new Path(dialog.getFilterPath());
				String buildFileName= dialog.getFileName();
				IPath path= filterPath.append(buildFileName).makeAbsolute();	
								
				xmiFileField.setText(path.toOSString());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);				
			}			
		});
		
		xmiFileRadio.setSelection(true);
		classNameField.setEnabled(false);
		xmiFileField.setEnabled(true);
		xmiBrowse.setEnabled(true);
		return main;
	}
	
	protected void createRepositoryUrlField(Composite main) {
		new Label(main, SWT.NONE).setText("URL:");
		classNameField = new Text(main, SWT.BORDER);
		classNameField.setText(className);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.ENTRY_FIELD_WIDTH);
		classNameField.setLayoutData(data);		
	}

	public String getClassName() {
		return className;
	}
	
	public boolean isLocalRepositorySelected() {
		return staticModelClassRadio.getSelection();
	}

	@Override
	protected void okPressed() {
        IStructuredSelection selection = (IStructuredSelection) view.getSelection();
        RepositoryTreeObject repositoryTreeObject = (RepositoryTreeObject) selection.getFirstElement();

        if (isLocalRepositorySelected()) {			
			String className = classNameField.getText();
			if (className == null || className.length() == 0) {
				return;
			}
			this.className = className;
            repositoryTreeObject.addStaticModel(className);
		}
        else {
			String xmiFileName = xmiFileField.getText();
			if (xmiFileName == null || xmiFileName.length() == 0) {
				return;
			}
			this.className = xmiFileName;
            repositoryTreeObject.addXmiModel(xmiFileName);
		}		
        view.refresh();
        super.okPressed();
	}
    
    @Override
	protected void cancelPressed() {
		className = null;
		super.cancelPressed();
	}	

}
