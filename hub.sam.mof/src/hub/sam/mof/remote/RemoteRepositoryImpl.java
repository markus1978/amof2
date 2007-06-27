package hub.sam.mof.remote;

import hub.sam.mof.Repository;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import cmof.reflection.Extent;

public class RemoteRepositoryImpl extends java.rmi.server.UnicastRemoteObject implements RemoteRepository {
	
	private final Repository localRepository;

	public RemoteRepositoryImpl(final Repository localRepository) throws RemoteException {
		super();
		this.localRepository = localRepository;
	}

	public RemoteExtent getExtent(String name) throws RemoteException {
		Extent localExtent = localRepository.getExtent(name);
		return localExtent == null ? null : new RemoteExtentImpl(localExtent);
	}

	public Collection<String> getExtentNames() throws RemoteException {
		return new Vector<String>(localRepository.getExtentNames());
	}	
	
	public void addRepositoryChangeListener(RemoteRepositoryChangeListener listener) throws RemoteException {
		localRepository.addRepositoryChangeListener(new LocalRepositoryChangeListenerImpl(listener));
	}

	public void removeRepositoryChangeListener(RemoteRepositoryChangeListener listener) throws RemoteException {
		localRepository.removeRepositoryChangeListener(new LocalRepositoryChangeListenerImpl(listener));
	}

	public int remoteHashCode() throws RemoteException {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((localRepository == null) ? 0 : localRepository.hashCode());
		return result;
	}

	public boolean remoteEquals(Object obj) throws RemoteException {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemoteRepositoryImpl other = (RemoteRepositoryImpl) obj;
		if (localRepository == null) {
			if (other.localRepository != null)
				return false;
		} else if (!localRepository.equals(other.localRepository))
			return false;
		return true;
	}
}
