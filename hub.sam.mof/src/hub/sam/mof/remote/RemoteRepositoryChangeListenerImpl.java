package hub.sam.mof.remote;

import hub.sam.mof.RepositoryChangeListener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteRepositoryChangeListenerImpl extends UnicastRemoteObject
		implements RemoteRepositoryChangeListener {
	
	private final RepositoryChangeListener localListener;

	public RemoteRepositoryChangeListenerImpl(final RepositoryChangeListener localListener) throws RemoteException {
		super();
		this.localListener = localListener;
	}

	public void extendAboutToBeRemoved(String name, RemoteExtent extent)
			throws RemoteException {
		localListener.extendAboutToBeRemoved(name, new LocalExtentImpl(extent));
	}

	public void extendAdded(RemoteExtent extent) throws RemoteException {
		localListener.extendAdded(new LocalExtentImpl(extent));
	}

	public int remoteHashCode() throws RemoteException {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((localListener == null) ? 0 : localListener.hashCode());
		return result;
	}

	public boolean remoteEquals(Object obj) throws RemoteException {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemoteRepositoryChangeListenerImpl other = (RemoteRepositoryChangeListenerImpl) obj;
		if (localListener == null) {
			if (other.localListener != null)
				return false;
		} else if (!localListener.equals(other.localListener))
			return false;
		return true;
	}

}
