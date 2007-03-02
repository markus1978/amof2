package hub.sam.mof.plugin.modelview.tree;


public class StdBuilderFactory implements IBuilderFactory {

	public IBuilder getBuilder(Object obj) {
		if (obj instanceof cmof.reflection.Object) {
			return new ObjectBuilder();
		}				
		return null;
	}

}
