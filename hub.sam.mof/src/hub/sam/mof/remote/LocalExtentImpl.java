package hub.sam.mof.remote;

import hub.sam.mof.util.ListImpl;

import java.rmi.RemoteException;

import cmof.Association;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.exception.QueryParseException;
import cmof.reflection.Extent;
import cmof.reflection.ExtentChangeListener;
import cmof.reflection.Link;

public class LocalExtentImpl implements Extent {
	
	private final RemoteExtent remoteExtent;

	public LocalExtentImpl(final RemoteExtent remoteExtent) {
		super();
		this.remoteExtent = remoteExtent;
	}

	public void addExtentChangeListener(ExtentChangeListener listener) {
		try {
			remoteExtent.addExtentChangeListener(new RemoteExtentChangeListenerImpl(listener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public ReflectiveCollection<? extends cmof.reflection.Object> getObject() {
		try {
			return (ReflectiveCollection)LocalObjectImpl.createLocalJavaObjectFromRemoteJavaObject(remoteExtent.getObject());
		} catch (RemoteException e1) {
			throw new RuntimeException(e1);
		}		
	}

	public boolean linkExists(Association association, cmof.reflection.Object firstObject,
			cmof.reflection.Object secondObject) {
		throw new RemoteRepositoryException("not implemented");
	}

	public ReflectiveCollection<? extends cmof.reflection.Object> linkedObjects(
			Association association, cmof.reflection.Object endObject, boolean end1to2direction) {
		throw new RemoteRepositoryException("not implemented");
	}

	public ReflectiveCollection<? extends Link> linksOfType(Association type) {
		throw new RemoteRepositoryException("not implemented");
	}

	@SuppressWarnings("unchecked")
	public ReflectiveCollection<? extends  cmof.reflection.Object> objectsOfType(UmlClass type,
			boolean includeSubtypes) {
		try {
			return (ReflectiveCollection)LocalObjectImpl.createLocalJavaObjectFromRemoteJavaObject(
					remoteExtent.objectsOfType(LocalObjectImpl.createRemoteObjectFromLocalObject((cmof.reflection.Object)type), includeSubtypes));
		} catch (RemoteException e1) {
			throw new RuntimeException(e1);
		}		
	}

	@SuppressWarnings("unchecked")
	public ReflectiveCollection<? extends  cmof.reflection.Object> outermostComposites() {
		try {
			return (ReflectiveCollection)LocalObjectImpl.createLocalJavaObjectFromRemoteJavaObject(remoteExtent.outermostComposites());
		} catch (RemoteException e1) {
			throw new RuntimeException(e1);
		}		
	}

	public  cmof.reflection.Object query(String queryString) throws QueryParseException {
		try {
			return LocalObjectImpl.createLocalObjectFromRemoteObject(remoteExtent.query(queryString));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeExtentChangeListener(ExtentChangeListener listener) {
		try {
			remoteExtent.removeExtentChangeListener(new RemoteExtentChangeListenerImpl(listener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T getAdaptor(Class<T> adaptorClass) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		try {
			result = PRIME * result + ((remoteExtent == null) ? 0 : remoteExtent.remoteHashCode());
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
		final LocalExtentImpl other = (LocalExtentImpl) obj;
		if (remoteExtent == null) {
			if (other.remoteExtent != null)
				return false;
		} else
			try {
				if (!remoteExtent.remoteEquals(other.remoteExtent))
					return false;
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		return true;
	}	
}
