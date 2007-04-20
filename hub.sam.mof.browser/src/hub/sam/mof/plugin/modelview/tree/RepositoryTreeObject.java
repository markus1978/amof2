package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.Repository;
import hub.sam.mof.RepositoryChangeListener;
import hub.sam.mof.plugin.modelview.Images;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import cmof.reflection.Extent;

public class RepositoryTreeObject extends TreeParent {

	private final Repository repository;
	
	/**
	 * For the case an extent is removed, it has to be marked as removed because it will still exist when the
	 * view refreshes for the extent remove. So the extentAboutToBeRemoved event is caught, the extent marked as
	 * removed, and in the following refresh it is ignored.
	 */
	private String removed = null;
	
	private final RepositoryChangeListener fChangeListener = new RepositoryChangeListener() {
		@Override
		public void extendAdded(Extent extent) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {				
				private void refresh() {
					RepositoryTreeObject.this.refresh();						
					getView().refresh(RepositoryTreeObject.this);
				}			
				public void run() {			
					refresh();
				}				
			});
		}		
		
		@Override
		public void extendAboutToBeRemoved(final String name, Extent extent) {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {				
				private void refresh() {
					removed = name;
					RepositoryTreeObject.this.refresh();						
					getView().refresh(RepositoryTreeObject.this);
					removed = null;
				}			
				public void run() {			
					refresh();
				}				
			});
		}
	};
		
	public RepositoryTreeObject(Repository repository, TreeParent parent, TreeViewer view) {
		super(repository, parent, view);
		this.repository = repository;
		repository.addRepositoryChangeListener(fChangeListener);
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Collection<String> extentNames = repository.getExtentNames();
		Collection<TreeObject> result = new ArrayList<TreeObject>(extentNames.size());
		
		for (String extentName: extentNames) {
			if (!extentName.equals(removed)) {
				result.add(new ExtentTreeObject(repository.getExtent(extentName), extentName, this, getView()));
			}
		}
		return result;
	}

	@Override
	public String getText() {
		return "LocalRepository";
	}	
	
	@Override
	public Repository getElement() {		
		return repository;
	}

	@Override
	public Image getImage() {
		return Images.getDefault().getRepository();
	}
    
    public Extent addStaticModel(String className) {
        try {
            Extent extent = getElement().addStaticModel( Thread.currentThread().getContextClassLoader().loadClass(className) );
            refresh();
            return extent;
        }
        catch (Exception e) {
            MessageDialog.openError(
                    getView().getControl().getShell(),
                    "Could not create ...",
                    "Could not create static model: " + e.getMessage());
        }
        return null;
    }
    
    public Extent addXmiModel(String xmiFileName) {
        Extent extent = null;
        try {
            if (xmiFileName.endsWith(".xml")) {
                extent = getElement().addXmiModel(new FileInputStream(xmiFileName), xmiFileName, Repository.XMI2);
            }
            else if (xmiFileName.endsWith(".mdxml")) {
                extent = getElement().addXmiModel(new FileInputStream(xmiFileName), xmiFileName, Repository.MD);
            }
        }
        catch (Exception e) {
            MessageDialog.openError(
                    getView().getControl().getShell(),
                    "Could not create ...",
                    "Could not create model: " + e.getMessage());
        }
        refresh();
        return extent;
    }

	@Override
	public void delete() {
		repository.removeRepositoryChangeListener(fChangeListener);
		super.delete();
	}
    
}
