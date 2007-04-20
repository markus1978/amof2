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

import cmof.PackageMerge;

public class PackageBuilder extends NamespaceBuilder {

	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {
		cmof.Package pkg = (cmof.Package)obj;		
		for (Object ownedMember: pkg.getOwnedMember()) {			
			if (ownedMember instanceof PackageMerge) {
				PackageMerge merges = (PackageMerge)ownedMember;
				TreeObject to = mgr.addChild(merges.getMergedPackage());
				Image baseImage = to.getImage();
				Rectangle bounds = baseImage.getBounds();
				ImageDescriptor newImage = new OverlayIcon(
						new ImageImageDescriptor(baseImage), 
						new ImageDescriptor[][] {new ImageDescriptor[] {new ImageImageDescriptor(Images.getDefault().getMerged_deco())}, null, null, null},
						new Point(bounds.height, bounds.width));
				to.setImage(Images.getDefault().get(newImage));
				to.setCategory(Categories.MERGED);
			} else if (ownedMember != null) {
				mgr.addChild(ownedMember);
			}
		}
		/*
		for (Package nestedPkg: pkg.getNestedPackage()) {
			mgr.addChild(nestedPkg);	
		}
		for (PackageMerge merges: pkg.getPackageMerge()) {			
			TreeObject to = mgr.addChild(merges.getMergedPackage());
			Image baseImage = to.getImage();
			Rectangle bounds = baseImage.getBounds();
			ImageDescriptor newImage = new OverlayIcon(
					new ImageImageDescriptor(baseImage), 
					new ImageDescriptor[][] {new ImageDescriptor[] {new ImageImageDescriptor(Images.getDefault().getMerged_deco())}, null, null, null},
					new Point(bounds.height, bounds.width));
			to.setImage(Images.getDefault().get(newImage));
			to.setCategory(Categories.MERGED);
		}*/
		
		super.addChildren(obj, mgr, view);
	}

	@Override
	public Image getImage(Object element) {
		return Images.getDefault().getPackage();		
	}

	@Override
	public int getCategory(Object obj) {
		return Categories.PACKAGE;
	}	
}
