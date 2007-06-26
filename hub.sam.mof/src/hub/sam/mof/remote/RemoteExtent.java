package hub.sam.mof.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cmof.common.ReflectiveCollection;
import cmof.exception.QueryParseException;

public interface RemoteExtent extends Remote {

	public ReflectiveCollection<? extends RemoteObject> getObject() throws RemoteException;

    public ReflectiveCollection<? extends RemoteObject> objectsOfType(RemoteObject type,
            boolean includeSubtypes) throws RemoteException;
    
    public ReflectiveCollection<? extends RemoteObject> outermostComposites() throws RemoteException;

    public RemoteObject query(java.lang.String queryString) throws QueryParseException, RemoteException;
    
    public void addExtentChangeListener(RemoteExtentChangeListener listener) throws RemoteException;
    
    public void removeExtentChangeListener(RemoteExtentChangeListener listener) throws RemoteException;
    
	public boolean remoteEquals(Object other) throws RemoteException;
	
	public int remoteHashCode() throws RemoteException;
}
