package hub.sam.mof.remote;

import hub.sam.mof.Repository;

import java.rmi.RemoteException;
import java.util.Collection;

import cmof.reflection.Extent;

public class RemoteRepositoryImpl extends java.rmi.server.RemoteObject implements RemoteRepository {
	
	private final Repository localRepository;

	public RemoteRepositoryImpl(final Repository localRepository) {
		super();
		this.localRepository = localRepository;
	}

	public RemoteExtent getExtent(String name) throws RemoteException {
		Extent localExtent = localRepository.getExtent(name);
		return localExtent == null ? null : new RemoteExtentImpl(localExtent);
	}

	public Collection<String> getExtentNames() throws RemoteException {
		return localRepository.getExtentNames();
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
