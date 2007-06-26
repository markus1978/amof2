package hub.sam.mof.remote;

import java.rmi.RemoteException;

import cmof.reflection.ObjectChangeListener;

public class LocalObjectChangeListener implements ObjectChangeListener {

	private final RemoteObjectChangeListener remoteListener;
	
	public LocalObjectChangeListener(final RemoteObjectChangeListener remoteListener) {
		super();
		this.remoteListener = remoteListener;
	}

	public void handleDelete(cmof.reflection.Object object) {
		try {
			remoteListener.handleDelete(RemoteObjectImpl.createRemoteObjectFromLocalObject(object));
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
		final LocalObjectChangeListener other = (LocalObjectChangeListener) obj;
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
