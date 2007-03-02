package hub.sam.mof.plugin.modelview.tree;


public interface IChildManager {
	public TreeObject addChild(Object child);	
	
	public void addChild(TreeObject to);
	
	public TreeParent getParent();
	
	public IBuilderFactory getFactory();
}
