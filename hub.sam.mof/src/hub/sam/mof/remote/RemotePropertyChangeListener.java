package hub.sam.mof.remote;

import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePropertyChangeListener extends Remote {
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException;
    
	public boolean remoteEquals(Object other) throws RemoteException;
	
	public int remoteHashCode() throws RemoteException;
    
}
