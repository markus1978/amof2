package hub.sam.mof.remote;

import java.rmi.RemoteException;

import cmof.reflection.ExtentChangeListener;

public class LocalExtentChangeListenerImpl implements ExtentChangeListener {
	
	private final RemoteExtentChangeListener remoteListener;
	
	public LocalExtentChangeListenerImpl(final RemoteExtentChangeListener remoteListener) {
		super();
		this.remoteListener = remoteListener;
	}

	public void extendAboutToBeRemoved() {
		try {
			remoteListener.extendAboutToBeRemoved();
		} catch (RemoteException e) {			
			throw new RuntimeException(e);
		}
	}

	public void newObject(cmof.reflection.Object newObject) {
		try {
			remoteListener.newObject(RemoteObjectImpl.createRemoteObjectFromLocalObject(newObject));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void removedObject(cmof.reflection.Object oldObject) {
		try {
			remoteListener.removedObject(RemoteObjectImpl.createRemoteObjectFromLocalObject(oldObject));
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
		final LocalExtentChangeListenerImpl other = (LocalExtentChangeListenerImpl) obj;
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
