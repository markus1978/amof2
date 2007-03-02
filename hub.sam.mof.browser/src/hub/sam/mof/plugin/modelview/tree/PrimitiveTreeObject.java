package hub.sam.mof.plugin.modelview.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

public class PrimitiveTreeObject extends TreeObject {

	private final Object element;
	
	protected PrimitiveTreeObject(Object element, TreeParent parent, TreeViewer view) {
		super(element, parent, view);
		this.element = element;
	}
	
	@Override
	public Image getImage() {
		return null;
	}
	
	@Override
	public String getText() {
		return element.toString();
	}

}
