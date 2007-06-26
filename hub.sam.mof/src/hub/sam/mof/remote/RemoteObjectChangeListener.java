package hub.sam.mof.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObjectChangeListener extends Remote {
	public void handleDelete(RemoteObject object) throws RemoteException;
	
	public boolean remoteEquals(Object other) throws RemoteException;
	
	public int remoteHashCode() throws RemoteException;
}
