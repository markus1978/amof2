package hub.sam.mof.plugin.modelview.actions;

public interface IShowOtherFeaturesContext {
	public boolean isShowingInheritedFeatures(Object obj);
	
	public boolean isShowingOwnedFeatures(Object obj);
	
	public boolean isShowingFinalFeatures(Object obj);
	
	public void switchToOwnedFeatures(Object obj);
	
	public void switchToFinalFeatures(Object obj);
	
	public void switchToInheritedFeatures(Object obj);
}
