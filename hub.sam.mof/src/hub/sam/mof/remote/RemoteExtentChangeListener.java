package hub.sam.mof.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteExtentChangeListener extends Remote {

	public void newObject(RemoteObject newObject) throws RemoteException;
	
	public void removedObject(RemoteObject oldObject)  throws RemoteException;
	
	public void extendAboutToBeRemoved()  throws RemoteException;
	
    public boolean remoteEquals(Object other) throws RemoteException;
	
	public int remoteHashCode() throws RemoteException;
	
}
