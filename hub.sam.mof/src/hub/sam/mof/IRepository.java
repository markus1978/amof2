package hub.sam.mof;

import java.util.Collection;

import cmof.reflection.Extent;

public interface IRepository {

	public Extent getExtent(String name);
	
	public Collection<String> getExtentNames();
	
	public String getName();
	
	public void addRepositoryChangeListener(RepositoryChangeListener listener);	
	    
	public void removeRepositoryChangeListener(RepositoryChangeListener listener);
	
}
