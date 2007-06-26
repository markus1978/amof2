package hub.sam.mof.remote;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class RemotePropertyChangeListenerImpl extends java.rmi.server.RemoteObject implements
		RemotePropertyChangeListener {

	private final PropertyChangeListener localListener;
	
	public RemotePropertyChangeListenerImpl(final PropertyChangeListener localListener) {
		super();
		this.localListener = localListener;
	}

	public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
		localListener.propertyChange(evt);
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
