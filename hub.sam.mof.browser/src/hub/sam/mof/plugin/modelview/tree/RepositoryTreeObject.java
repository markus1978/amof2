package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.Images;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import cmof.reflection.Extent;

public class RepositoryTreeObject extends TreeParent {

	private final Repository repository;	
	
	public RepositoryTreeObject(Repository repository, TreeParent parent, TreeViewer view) {
		super(repository, parent, view);
		this.repository = repository;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Collection<String> extentNames = repository.getExtentNames();
		Collection<TreeObject> result = new ArrayList<TreeObject>(extentNames.size());
		
		for (String extentName: extentNames) {
			result.add(new ExtentTreeObject(repository.getExtent(extentName), extentName, this, getView()));
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
    
}
