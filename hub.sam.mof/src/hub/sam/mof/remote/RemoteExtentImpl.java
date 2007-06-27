package hub.sam.mof.remote;

import hub.sam.mof.util.SetImpl;

import java.rmi.RemoteException;

import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.exception.QueryParseException;
import cmof.reflection.Extent;

public class RemoteExtentImpl extends java.rmi.server.UnicastRemoteObject implements RemoteExtent {
	
	private final Extent localExtent;

	public RemoteExtentImpl(final Extent localExtent) throws RemoteException {
		super();
		this.localExtent = localExtent;
	}

	public void addExtentChangeListener(RemoteExtentChangeListener listener)
			throws RemoteException {
		localExtent.addExtentChangeListener(new LocalExtentChangeListenerImpl(listener));
	}

	public ReflectiveCollection<? extends RemoteObject> getObject()
			throws RemoteException {
		ReflectiveCollection<? extends RemoteObject> result = new SetImpl<RemoteObject>();
		for (cmof.reflection.Object localObject: localExtent.getObject()) {
			result.add(RemoteObjectImpl.createRemoteObjectFromLocalObject(localObject));
		}
		return result;
	}

	public ReflectiveCollection<? extends RemoteObject> objectsOfType(
			RemoteObject type, boolean includeSubtypes) throws RemoteException {
		ReflectiveCollection<? extends RemoteObject> result = new SetImpl<RemoteObject>();
		for (cmof.reflection.Object localObject: localExtent.objectsOfType(
				(UmlClass)LocalObjectImpl.createLocalObjectFromRemoteObject(type), includeSubtypes)) {
			result.add(RemoteObjectImpl.createRemoteObjectFromLocalObject(localObject));
		}
		return result;
	}

	public ReflectiveCollection<? extends RemoteObject> outermostComposites()
			throws RemoteException {
		ReflectiveCollection<? extends RemoteObject> result = new SetImpl<RemoteObject>();
		for (cmof.reflection.Object localObject: localExtent.outermostComposites()) {
			result.add(RemoteObjectImpl.createRemoteObjectFromLocalObject(localObject));
		}
		return result;
	}

	public RemoteObject query(String queryString) throws QueryParseException,
			RemoteException {
		cmof.reflection.Object localObject = localExtent.query(queryString);		
		return RemoteObjectImpl.createRemoteObjectFromLocalObject(localObject);
	}

	public void removeExtentChangeListener(RemoteExtentChangeListener listener)
			throws RemoteException {
		localExtent.removeExtentChangeListener(new LocalExtentChangeListenerImpl(listener));
	}

	public int remoteHashCode() throws RemoteException {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((localExtent == null) ? 0 : localExtent.hashCode());
		return result;
	}

	public boolean remoteEquals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemoteExtentImpl other = (RemoteExtentImpl) obj;
		if (localExtent == null) {
			if (other.localExtent != null)
				return false;
		} else if (!localExtent.equals(other.localExtent))
			return false;
		return true;
	}
	
}
