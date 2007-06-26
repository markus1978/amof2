package hub.sam.mof.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface RemoteRepository extends Remote {
	public RemoteExtent getExtent(String name) throws RemoteException;
	
	public Collection<String> getExtentNames() throws RemoteException;
	
	public boolean remoteEquals(Object other) throws RemoteException;
	
	public int remoteHashCode() throws RemoteException;
}
