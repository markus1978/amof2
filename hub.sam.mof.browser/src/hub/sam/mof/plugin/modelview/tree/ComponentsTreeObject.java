package hub.sam.mof.plugin.modelview.tree;

import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.tree.builder.Categories;

import java.util.Collection;
import java.util.Vector;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

public class ComponentsTreeObject extends ManTreeObject {

	private final cmof.reflection.Object theObject;
	
	public ComponentsTreeObject(cmof.reflection.Object theObject, TreeParent parent, IBuilderFactory factory, TreeViewer view) {
		super(theObject, parent, factory, view);
		this.theObject = theObject;
	}

	@Override
	protected Collection<TreeObject> retrieveChildren() {
		super.retrieveChildren();
		Collection<TreeObject> result = new Vector<TreeObject>();
		for (cmof.reflection.Object component: theObject.getComponents()) {
			result.add(build(component));
		}
		return result;
	}
		
	@Override
	public String getText() {
		return "components";
	}

	@Override
	public Image getImage() {
		return Images.getDefault().getComponents();
	}

	@Override
	public int getCategory() {
		return Categories.COMPONENTS;
	}	
}
