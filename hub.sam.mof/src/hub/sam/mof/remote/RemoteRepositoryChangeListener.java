package hub.sam.mof.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRepositoryChangeListener extends Remote{
	
	public void extendAdded(RemoteExtent extent) throws RemoteException;
	
	public void extendAboutToBeRemoved(String name, RemoteExtent extent) throws RemoteException;
	
	public int remoteHashCode() throws RemoteException;
	
	public boolean remoteEquals(Object obj) throws RemoteException;

}
