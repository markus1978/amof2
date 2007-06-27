package hub.sam.mof.remote;

import java.rmi.RemoteException;

import cmof.reflection.Extent;
import hub.sam.mof.RepositoryChangeListener;

public class LocalRepositoryChangeListenerImpl extends RepositoryChangeListener {

	private final RemoteRepositoryChangeListener remoteListener;
	
	public LocalRepositoryChangeListenerImpl(final RemoteRepositoryChangeListener remoteListener) {
		super();
		this.remoteListener = remoteListener;
	}

	@Override
	public void extendAboutToBeRemoved(String name, Extent extent) {
		try {
			remoteListener.extendAboutToBeRemoved(name, new RemoteExtentImpl(extent));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void extendAdded(Extent extent) {
		try {
			remoteListener.extendAdded(new RemoteExtentImpl(extent));
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
		final LocalRepositoryChangeListenerImpl other = (LocalRepositoryChangeListenerImpl) obj;
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
