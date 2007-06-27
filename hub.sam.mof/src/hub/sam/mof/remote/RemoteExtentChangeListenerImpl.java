package hub.sam.mof.remote;

import java.rmi.RemoteException;

import cmof.reflection.ExtentChangeListener;

public class RemoteExtentChangeListenerImpl extends java.rmi.server.UnicastRemoteObject  implements
		RemoteExtentChangeListener {
	
	private final ExtentChangeListener localListener;

	public RemoteExtentChangeListenerImpl(final ExtentChangeListener localListener) throws RemoteException {
		super();
		this.localListener = localListener;
	}

	public void extendAboutToBeRemoved() throws RemoteException {
		localListener.extendAboutToBeRemoved();
	}

	public void newObject(RemoteObject newObject) throws RemoteException {
		localListener.newObject(new LocalObjectImpl(newObject));
	}

	public void removedObject(RemoteObject oldObject) throws RemoteException {
		localListener.removedObject(new LocalObjectImpl(oldObject));
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
		final RemoteExtentChangeListenerImpl other = (RemoteExtentChangeListenerImpl) obj;
		if (localListener == null) {
			if (other.localListener != null)
				return false;
		} else if (!localListener.equals(other.localListener))
			return false;
		return true;
	}

}
