package hub.sam.mof.plugin.modelview.tree;

import java.util.List;
import java.util.Vector;

public class ExtendableFactory implements IBuilderFactory {

	private final List<IBuilderFactory> factories = new Vector<IBuilderFactory>();
	
	public IBuilder getBuilder(Object obj) {
		for(int i = factories.size()-1; i >= 0; i--) {
			IBuilder result = factories.get(i).getBuilder(obj);
			if (result != null) {
				return result;
			}
		}		
		return null;
	}

	public void addFactory(IBuilderFactory factory) {
		factories.add(factory);
	}
}
