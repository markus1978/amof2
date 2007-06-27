package hub.sam.mof.remote;

import hub.sam.mof.IRepository;
import hub.sam.mof.RepositoryChangeListener;

import java.rmi.RemoteException;
import java.util.Collection;

import cmof.reflection.Extent;

public class LocalRepositoryImpl implements IRepository {

	private final RemoteRepository remoteRepository;
	private final String name;
	
	public LocalRepositoryImpl(final RemoteRepository remoteRepository, String name) {
		super();
		this.remoteRepository = remoteRepository;
		this.name = name;
	}

	public Extent getExtent(String name) {
		RemoteExtent remoteExtent;
		try {
			remoteExtent = remoteRepository.getExtent(name);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		return remoteExtent == null ? null : new LocalExtentImpl(remoteExtent);
	}
	
	public Collection<String> getExtentNames() {
		try {
			return remoteRepository.getExtentNames();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}	

	public void addRepositoryChangeListener(RepositoryChangeListener localListener) {
		try {
			remoteRepository.addRepositoryChangeListener(new RemoteRepositoryChangeListenerImpl(localListener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeRepositoryChangeListener(RepositoryChangeListener localListener) {
		try {
			remoteRepository.removeRepositoryChangeListener(new RemoteRepositoryChangeListenerImpl(localListener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}		
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		try {
			result = PRIME * result + ((remoteRepository == null) ? 0 : remoteRepository.remoteHashCode());
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
		final LocalRepositoryImpl other = (LocalRepositoryImpl) obj;
		if (remoteRepository == null) {
			if (other.remoteRepository != null)
				return false;
		} else
			try {
				if (!remoteRepository.remoteEquals(other.remoteRepository))
					return false;
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		return true;
	}
	
	
}
