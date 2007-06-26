package hub.sam.mof.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cmof.common.ReflectiveCollection;

public interface RemoteObject extends Remote {
  
    public RemoteObject getMetaClass() throws RemoteException;

    public RemoteObject container() throws RemoteException;

    public java.lang.Object get(RemoteObject property) throws RemoteException;

    public java.lang.Object get(RemoteObject property, java.lang.Object qualifier) throws RemoteException;

    public boolean remoteEquals(java.lang.Object element) throws RemoteException;
    
    public int remoteHashCode() throws RemoteException;

    public boolean isInstanceOfType(RemoteObject type, boolean includeSubTypes) throws RemoteException;

    public RemoteObject getOutermostContainer() throws RemoteException;

    public ReflectiveCollection<? extends RemoteObject> getComponents() throws RemoteException;

    public RemoteExtent getExtent() throws RemoteException;

    public java.lang.Object get(String property) throws RemoteException;

    public java.lang.Object get(String property, java.lang.Object qualifier) throws RemoteException;

    // TODO align with PropertyChangeListener stuff
    public void addObjectEventHandler(RemoteObjectChangeListener handler) throws RemoteException;
    
    public void removeObjectEventHandler(RemoteObjectChangeListener handler) throws RemoteException;

    public void addListener(RemotePropertyChangeListener listener) throws RemoteException;
    
    public void addListener(String propertyName, RemotePropertyChangeListener listener) throws RemoteException;

    public void removeListener(RemotePropertyChangeListener listener) throws RemoteException;
    
    public void removeListener(String propertyName, RemotePropertyChangeListener listener) throws RemoteException;
    
    public Class getConcreteInterface() throws RemoteException;
}
