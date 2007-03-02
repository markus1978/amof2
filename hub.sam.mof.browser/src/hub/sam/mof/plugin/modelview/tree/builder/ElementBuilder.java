package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.ModelViewLabelProvider;
import hub.sam.mof.plugin.modelview.actions.IShowDetailsContext;
import hub.sam.mof.plugin.modelview.tree.AutomatedBuilder;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.ObjectBuilder;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import cmof.reflection.Object;

public abstract class ElementBuilder extends AutomatedBuilder implements IShowDetailsContext {
			
	public void addChildren(java.lang.Object obj, IChildManager mgr, TreeViewer view) {			
		if (mgr.getParent().optionIsSet(SHOW_DETAILS)) {
			TreeObject to = new ObjectBuilder().create(obj, mgr.getParent(), mgr.getFactory(), view);
			to.setImage(Images.getDefault().getInfos());
			to.setCategory(Categories.INFO);
			to.setText("<details>");
			mgr.addChild(to);		
		}
	}

	@Override
	public Image getImage(java.lang.Object element) {
		return ModelViewLabelProvider.getDefault().getImage(element);		
	}

	@Override
	public String getText(java.lang.Object element) {	
		if (element instanceof cmof.reflection.Object) {
			Object theObject = (Object)element;		
			String result = null;
			try {
				result = (String)theObject.get("qualifiedName");
			} catch (Exception e) {
				// empty			
			}
			if (result == null) {
				try {
					result = "[" + theObject.getMetaClass().getQualifiedName() + "]";
				} catch (Exception e) {
					// empty			
				}	
			}
			if (result == null) {
				result = "UNKNOWN";
			}
			return result;
		} else {
			return "UNEXPECTED";
		}
	}

	private final static int SHOW_DETAILS = 1;
	
	public void switchShowDetails(java.lang.Object obj) {
		TreeObject to = (TreeObject)obj;
		if (isShowingDetails(obj)) {
			to.unsetOption(SHOW_DETAILS);
		} else {
			to.setOption(SHOW_DETAILS);
		}
	}

	public boolean isShowingDetails(java.lang.Object obj) {
		return ((TreeObject)obj).optionIsSet(SHOW_DETAILS);
	}
	
	
}
