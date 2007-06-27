package hub.sam.mof.remote;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemotePropertyChangeEventImpl extends UnicastRemoteObject
		implements RemotePropertyChangeEvent {
	
	private final PropertyChangeEvent localEvent;
	
	public RemotePropertyChangeEventImpl(final PropertyChangeEvent localEvent) throws RemoteException {
		super();
		this.localEvent = localEvent;
	}

	public String getName() throws RemoteException {
		return localEvent.getPropertyName();
	}

	public Object getNewValue() throws RemoteException {
		return RemoteObjectImpl.createRemoteJavaObjectFromLocalJavaObject(localEvent.getNewValue());
	}

	public Object getOldValue() throws RemoteException {
		return RemoteObjectImpl.createRemoteJavaObjectFromLocalJavaObject(localEvent.getOldValue());
	}

	public Object getSource() throws RemoteException {
		return RemoteObjectImpl.createRemoteJavaObjectFromLocalJavaObject(localEvent.getSource());
	}

}
