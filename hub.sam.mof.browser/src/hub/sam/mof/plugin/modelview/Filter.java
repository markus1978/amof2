package hub.sam.mof.plugin.modelview;

import java.util.*;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

public class Filter {

	private Collection<Class> filterClasses = new HashSet<Class>();
	
	public boolean isFiltered(TreeObject obj) {
		if (filterClasses.contains(obj.getElement().getClass())) {
			return true;
		} else {
			return false;
		}		
	}
	
	public void addClassToFilter(Class filter) {
		filterClasses.add(filter);
	}
}
