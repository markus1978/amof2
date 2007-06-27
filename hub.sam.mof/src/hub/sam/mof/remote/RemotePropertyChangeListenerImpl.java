package hub.sam.mof.remote;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemotePropertyChangeListenerImpl extends UnicastRemoteObject implements
		RemotePropertyChangeListener {

	private final PropertyChangeListener localListener;
	
	public RemotePropertyChangeListenerImpl(final PropertyChangeListener localListener) throws RemoteException {
		super();
		this.localListener = localListener;
	}

	public void propertyChange(RemotePropertyChangeEvent evt) throws RemoteException {
		localListener.propertyChange(new PropertyChangeEvent(
				LocalObjectImpl.createLocalJavaObjectFromRemoteJavaObject(evt.getSource()),
				evt.getName(),
				LocalObjectImpl.createLocalJavaObjectFromRemoteJavaObject(evt.getOldValue()),
				LocalObjectImpl.createLocalJavaObjectFromRemoteJavaObject(evt.getNewValue())));
	}

	public int remoteHashCode() throws RemoteException {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((localListener == null) ? 0 : localListener.hashCode());
		return result;
	}
	
	public boolean remoteEquals(Object obj) throws RemoteException {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemotePropertyChangeListenerImpl other = (RemotePropertyChangeListenerImpl) obj;
		if (localListener == null) {
			if (other.localListener != null)
				return false;
		} else if (!localListener.equals(other.localListener))
			return false;
		return true;
	}

	
}
