/**
 * 
 */
package hub.sam.mof.plugin.modelview;

import hub.sam.mof.plugin.modelview.tree.TreeObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ModelViewLabelProvider extends LabelProvider {

	private static final ModelViewLabelProvider defaultInstance = new ModelViewLabelProvider();
    
	public static ModelViewLabelProvider getDefault() {
		return defaultInstance;
	}
	
	private ModelViewLabelProvider() {
		// empty
	}
	
	@Override
	public String getText(java.lang.Object obj) {
		if (obj instanceof TreeObject) {
			return ((TreeObject)obj).getText();
		} else {
			return "UNKNOWN";
		}		
	}
	
	@Override
	public Image getImage(java.lang.Object obj) {
		if (obj instanceof TreeObject) {
			return ((TreeObject)obj).getImage();
		} else {				
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
	}
}