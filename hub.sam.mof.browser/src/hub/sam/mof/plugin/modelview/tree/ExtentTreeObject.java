package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.Images;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import cmof.reflection.Extent;
import cmof.reflection.ExtentChangeListener;
import cmof.reflection.Object;

public class ExtentTreeObject extends ManTreeObject {

	private String extentName;
	private final Extent extent;
	
	private static final ExtendableFactory factory = new ExtendableFactory();
	
    static {
		factory.addFactory(new StdBuilderFactory());
		factory.addFactory(new CMOFBuilderFactory());
	}
    
	private final ExtentChangeListener listener = new MyExtentChangeListener();
	
	class MyExtentChangeListener implements ExtentChangeListener {
		public void change() {		
			getView().getSelection();
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {			
						refresh();
						getView().refresh(ExtentTreeObject.this);					
				}				
			});
		}

		public void newObject(Object newObject) {
			// this if is nonsense because in the moment that the object is created and this event is
			// fired the newObject cant have a container.
			if (newObject.getOutermostContainer() == newObject) {
				change();
			}
		}

		public void removedObject(Object oldObject) {
			boolean change = false;
			loop: for (TreeObject obj: getChildren()) {
				if (obj.getElement() == oldObject) {
					change = true;
					break loop;
				}
			}
			if (change) {
				change();
			}
		}		
	}
	
	public ExtentTreeObject(Extent extent, String extentName, TreeParent parent, TreeViewer view) {
		super(extent, parent, factory, view);
		this.extent = extent;
		this.extentName = extentName;		
		
		extent.addExtentChangeListener(listener);		
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Collection<TreeObject> result = new Vector<TreeObject>();		
		Collection<cmof.reflection.Object> outermostComposites = new Vector<cmof.reflection.Object>();
		loop: for (cmof.reflection.Object aObject: extent.getObject()) {
			if (aObject == null) {
				continue loop;
			}
			if (aObject.container() == null) {
				outermostComposites.add(aObject);
			}
		}
		for (cmof.reflection.Object aObject: outermostComposites) {
			result.add(build(aObject));
		}
		return result;
	}
	
	@Override
	public String getText() {
		return extentName;
	}
	
	@Override
	public Image getImage() {
		return Images.getDefault().getExtent();
	}
	
	@Override
	protected void delete() {
		extent.removeExtentChangeListener(listener);
		super.delete();
	}	
}
