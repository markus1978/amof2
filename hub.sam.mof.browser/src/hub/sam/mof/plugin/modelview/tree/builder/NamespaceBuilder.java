package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.ImageImageDescriptor;
import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.OverlayIcon;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import cmof.Constraint;
import cmof.ElementImport;
import cmof.Namespace;

public abstract class NamespaceBuilder extends ElementBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {	
		Namespace ns = (Namespace)obj;
		
		for (Constraint constraint: ns.getOwnedRule()) {
			mgr.addChild(constraint);
		}
		for (ElementImport elementImport: ns.getElementImport()) {			
			TreeObject to = mgr.addChild(elementImport.getImportedElement());
			Image baseImage = to.getImage();
			Rectangle bounds = baseImage.getBounds();
			ImageDescriptor newImage = new OverlayIcon(
					new ImageImageDescriptor(baseImage), 
					new ImageDescriptor[][] {new ImageDescriptor[] {new ImageImageDescriptor(Images.getDefault().getAssociation())}, null, null, null},
					new Point(bounds.height, bounds.width));
			to.setImage(Images.getDefault().get(newImage));
		}
		super.addChildren(obj, mgr, view);
	}
}
