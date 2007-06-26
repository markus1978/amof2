package hub.sam.mof.remote;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class LocalPropertyChangeListenerImpl implements PropertyChangeListener {

	private final RemotePropertyChangeListener remoteListener;
	
	public LocalPropertyChangeListenerImpl(final RemotePropertyChangeListener remoteListener) {
		super();
		this.remoteListener = remoteListener;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		try {
			remoteListener.propertyChange(evt);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		try {
			result = PRIME * result + ((remoteListener == null) ? 0 : remoteListener.remoteHashCode());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LocalPropertyChangeListenerImpl other = (LocalPropertyChangeListenerImpl) obj;
		if (remoteListener == null) {
			if (other.remoteListener != null)
				return false;
		} else
			try {
				if (!remoteListener.remoteEquals(other.remoteListener))
					return false;
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		return true;
	}

}
