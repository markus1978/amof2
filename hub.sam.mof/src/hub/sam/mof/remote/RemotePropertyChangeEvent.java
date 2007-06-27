package hub.sam.mof.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePropertyChangeEvent extends Remote {

	public String getName() throws RemoteException;
	
	public Object getSource() throws RemoteException;
	
	public Object getOldValue() throws RemoteException;
	
	public Object getNewValue() throws RemoteException;
}
