package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.ImageImageDescriptor;
import hub.sam.mof.plugin.modelview.Images;
import hub.sam.mof.plugin.modelview.OverlayIcon;
import hub.sam.mof.plugin.modelview.actions.IShowOtherFeaturesContext;
import hub.sam.mof.plugin.modelview.tree.IBuilderFactory;
import hub.sam.mof.plugin.modelview.tree.IChildManager;
import hub.sam.mof.plugin.modelview.tree.TreeObject;
import hub.sam.mof.plugin.modelview.tree.TreeParent;
import hub.sam.mof.util.ListImpl;
import hub.sam.mof.util.SetImpl;
import hub.sam.util.MultiMap;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import core.abstractions.namespaces.NamedElement;
import core.abstractions.redefinitions.RedefinableElement;

public class ClassBuilder extends ClassifierBuilder implements IShowOtherFeaturesContext {

	@Override
	public TreeObject create(Object obj, TreeParent parent, IBuilderFactory factory, TreeViewer view) {
		TreeObject to = super.create(obj, parent, factory, view);
		switchToOwnedFeatures(to);
		return to;
	}
	
	private void addRedefinition(MultiMap<RedefinableElement, RedefinableElement> redefinitions, ReflectiveCollection<RedefinableElement> keys, NamedElement member) {
		if (member instanceof RedefinableElement) {
			for (RedefinableElement redefined: ((RedefinableElement)member).getRedefinedElement()) {
				redefinitions.put(redefined, (RedefinableElement)member);	
			}
			keys.add(member);			
		}
	}
	
	private void addSubsetting(MultiMap<Property, Property> subsettings, NamedElement member) {
		if (member instanceof Property) {
			for (Property subsetted: ((Property)member).getSubsettedProperty()) {
				subsettings.put(subsetted, (Property)member);	
			}			
		}
	}
	
	private void addMemberChildren(NamedElement member, IChildManager mgr, boolean isInherited, boolean isRedefined, boolean isSubsetted) {
		TreeObject child = mgr.addChild(member);
				
		Image baseImage = null;
		if (member instanceof Property) {
			baseImage = Images.getDefault().getProperty();
		} else if (member instanceof Operation) {
			baseImage = Images.getDefault().getOperation();
		} else {
			baseImage = child.getImage();
		}
		
		
		Rectangle bounds = baseImage.getBounds();
		ImageDescriptor newImage = null;
		ImageDescriptor upperDeco = null;
		Collection<ImageDescriptor> lowerDecoList = new ArrayList<ImageDescriptor>(2);
		
		if (isInherited) {
			upperDeco = new ImageImageDescriptor(Images.getDefault().getImported_deco());
		}
		if (isRedefined) {
			lowerDecoList.add(new ImageImageDescriptor(Images.getDefault().getRedefined_deco()));
		}
		if (isSubsetted) {
			lowerDecoList.add(new ImageImageDescriptor(Images.getDefault().getMerged_deco()));
		}
		
		ImageDescriptor[] lowerDeco = lowerDecoList.toArray(new ImageDescriptor[]{});
		
		newImage = new OverlayIcon(			
				new ImageImageDescriptor(baseImage), 
				new ImageDescriptor[][] {new ImageDescriptor[] {upperDeco}, lowerDeco, null, null},
				new Point(bounds.height, bounds.width));
		child.setImage(Images.getDefault().get(newImage));		
		
		
		if (isInherited || isRedefined) {
			String text = child.getText();
			core.abstractions.namespaces.Namespace ns = member.getNamespace();
			if (ns != null && ns != mgr.getParent().getElement()) {
				child.setText(ns.getQualifiedName(), text);
			} else {
				child.setText("<unknown>", text);
			}	
		}		
	}

	@Override
	public void addChildren(Object obj, IChildManager mgr, TreeViewer view) {
		TreeObject to = mgr.getParent();
		UmlClass theClass = (UmlClass)obj;
		
		ReflectiveCollection<? extends NamedElement> inheritedMember = new SetImpl<NamedElement>();
		ReflectiveCollection<RedefinableElement> keys = new ListImpl<RedefinableElement>();

		MultiMap<RedefinableElement, RedefinableElement> redefinitions = new MultiMap<RedefinableElement, RedefinableElement>();
		MultiMap<Property, Property> subsettings = new MultiMap<Property, Property>();		
		
		if (to.optionIsSet(SHOW_FINAL_FEATURES) || to.optionIsSet(SHOW_INHERITED_FEATURES)) {
			inheritedMember.addAll(theClass.getInheritedMember());
			for (NamedElement member: inheritedMember) {
				addRedefinition(redefinitions, keys, member);
				addSubsetting(subsettings, member);
			}			
		}
		for (NamedElement member: theClass.getOwnedMember()) {
			addRedefinition(redefinitions, keys, member);
			addSubsetting(subsettings, member);
		}
		
		if (to.optionIsSet(SHOW_FINAL_FEATURES)) {
			for (RedefinableElement member: keys) {
				if (redefinitions.get(member).size() == 0) {
					if (member instanceof Property) {
						addMemberChildren(member, mgr, inheritedMember.contains(member), false, subsettings.get((Property)member).size()!=0);
					} else {
						addMemberChildren(member, mgr, inheritedMember.contains(member), false, false);
					}
				}
			}
		} else {
			for(Property property: theClass.getOwnedAttribute()) {				
				addMemberChildren(property, mgr, inheritedMember.contains(property), redefinitions.get(property).size() != 0, subsettings.get(property).size() != 0);				
			}
		
			for(Operation op: theClass.getOwnedOperation()) {				
				addMemberChildren(op, mgr, inheritedMember.contains(op), redefinitions.get(op).size() != 0, false);
			}
			
			if (to.optionIsSet(SHOW_INHERITED_FEATURES)) {
				for(NamedElement member: theClass.getInheritedMember()) {
					boolean isRedefined = false;
					boolean isSubsetted = false;
					if (member instanceof RedefinableElement) {
						isRedefined = redefinitions.get((RedefinableElement)member).size() != 0;					
					}
					if (member instanceof Property) {
						isSubsetted = subsettings.get((Property)member).size() != 0;						
					}
					
					addMemberChildren(member, mgr, inheritedMember.contains(member), isRedefined, isSubsetted);
				}
			} 
		}
			
		super.addChildren(obj, mgr, view);
	}
	
	@Override
	public String getText(Object element) {
		UmlClass theClass = (UmlClass)element;
		String result = theClass.getName();
		if (theClass.isAbstract()) {
			result += ", abstract";
		}
		return result;
	}

	@Override
	public Image getImage(Object element) {
		return Images.getDefault().getClassIcon();
	}
	
	private final static int SHOW_OWNED_FEATURES = 2;
	private final static int SHOW_INHERITED_FEATURES = 3;
	private final static int SHOW_FINAL_FEATURES = 4;
	
	public boolean isShowingFinalFeatures(Object obj) {
		return ((TreeObject)obj).optionIsSet(SHOW_FINAL_FEATURES);
	}

	public boolean isShowingInheritedFeatures(Object obj) {
		return ((TreeObject)obj).optionIsSet(SHOW_INHERITED_FEATURES);
	}

	public boolean isShowingOwnedFeatures(Object obj) {
		return ((TreeObject)obj).optionIsSet(SHOW_OWNED_FEATURES);
	}

	public void switchToFinalFeatures(Object obj) {
		TreeObject to = (TreeObject)obj;
		to.setOption(SHOW_FINAL_FEATURES);
		to.unsetOption(SHOW_INHERITED_FEATURES);
		to.unsetOption(SHOW_OWNED_FEATURES);		
	}

	public void switchToInheritedFeatures(Object obj) {
		TreeObject to = (TreeObject)obj;
		to.unsetOption(SHOW_FINAL_FEATURES);
		to.setOption(SHOW_INHERITED_FEATURES);
		to.unsetOption(SHOW_OWNED_FEATURES);
	}

	public void switchToOwnedFeatures(Object obj) {
		TreeObject to = (TreeObject)obj;
		to.unsetOption(SHOW_FINAL_FEATURES);
		to.unsetOption(SHOW_INHERITED_FEATURES);
		to.setOption(SHOW_OWNED_FEATURES);	
	}
}
