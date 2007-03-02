package hub.sam.mof.plugin.modelview.tree.builder;

import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.viewers.ViewerSorter;

public class Categories extends ViewerSorter {
	
	public static final int ELEMENT = 1;
	public static final int COMPONENTS = 2;
	public static final int METACLASS = 3;
	
	public static final int INFO = 1000;
	
	public static final int PACKAGE = 2;
	public static final int CLASS = 3;
	public static final int PRIMITIVETYPE = 4;
	public static final int ENUMERATION = 5;
	public static final int ASSOCIATION = 6;
	public static final int MERGED = 1;
	
	public static final int PROPERTY = 1;
	public static final int OPERATION = 2;
	public static final int SUPERCLASS = 3;
	
	public static final int CONSTRAINT = 100;
	
	public static final int TYPE = 1;
	public static final int REDEFINITION = 2;
	public static final int SUBSETS = 3;
	public static final int OPPOSITE = 4;
	
	public static final int COMMENT = 1;
	public static final int DEPENDS = 2;
	
	@Override
	public int category(Object element) {
		return ((TreeObject)element).getCategory();
	}	
}
