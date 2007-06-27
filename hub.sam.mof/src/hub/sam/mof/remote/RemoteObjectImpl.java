package hub.sam.mof.remote;

import hub.sam.mof.util.ListImpl;
import hub.sam.util.Identity;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.ha.jndi.HAJNDI_Stub;

import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;

public class RemoteObjectImpl extends java.rmi.server.UnicastRemoteObject implements RemoteObject {
	
	protected static Object createRemoteJavaObjectFromLocalJavaObject(Object localObject) {
		if (localObject instanceof cmof.reflection.Object) {
			try {
				return createRemoteObjectFromLocalObject((cmof.reflection.Object)localObject);
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		} else if (localObject instanceof ReflectiveCollection) {
			ReflectiveCollection result = new ListImpl();
			for (Object obj: (ReflectiveCollection)localObject) {
				result.add(createRemoteJavaObjectFromLocalJavaObject(obj));
			}
			return result;
		} else {
			return localObject;
		}
	}
	
	private static Map<Object, cmof.reflection.Object> identities = new HashMap<Object, cmof.reflection.Object>();
	
	protected static RemoteObject createRemoteObjectFromLocalObject(cmof.reflection.Object localObject) throws RemoteException {
		if (localObject != null) {
			Object id = ((Identity)localObject).getId();
			cmof.reflection.Object existingIdentity = identities.get(id);
			if (existingIdentity != null && existingIdentity != localObject) {
				throw new RuntimeException("identity concept failed");
			}
			identities.put(id, localObject);
		}
		return localObject == null ? null : new RemoteObjectImpl(localObject);
	}
	
	protected static cmof.reflection.Object createLocalObjectFromRemoteObject(RemoteObject remoteObject)  {
		cmof.reflection.Object localObject;
		try {
			localObject = identities.get(remoteObject.getId());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		if (localObject == null) {
			throw new RuntimeException("unknown remote object.");
		}
		return localObject;
	}
	
	protected static Object createLocalJavaObjectFromRemoteJavaObject(Object remoteObject) {
		if (remoteObject instanceof RemoteObject) {		
			return createLocalObjectFromRemoteObject((RemoteObject)remoteObject);
		} else if (remoteObject instanceof ReflectiveCollection) {
			ReflectiveCollection result = new ListImpl();
			for (Object remoteElem: (ReflectiveCollection)remoteObject) {
				result.add(createLocalJavaObjectFromRemoteJavaObject(remoteElem));
			}
			return result;
		} else {
			return remoteObject;
		}
	}
	
	private final cmof.reflection.Object localObject;

	public RemoteObjectImpl(final cmof.reflection.Object localObject) throws RemoteException {
		super();
		this.localObject = localObject;
	}

	public void addListener(RemotePropertyChangeListener listener)
			throws RemoteException {
		localObject.addListener(new LocalPropertyChangeListenerImpl(listener));
	}

	public void addListener(String propertyName,
			RemotePropertyChangeListener listener) throws RemoteException {
		localObject.addListener(propertyName, new LocalPropertyChangeListenerImpl(listener));
	}

	public void addObjectEventHandler(RemoteObjectChangeListener handler)
			throws RemoteException {
		localObject.addObjectEventHandler(new LocalObjectChangeListener(handler));
	}

	public RemoteObject container() throws RemoteException {
		return createRemoteObjectFromLocalObject(localObject.container());
	}

	public Object get(RemoteObject property) throws RemoteException {
		return createRemoteJavaObjectFromLocalJavaObject(localObject.get((Property)createLocalObjectFromRemoteObject(property)));
	}

	public Object get(RemoteObject property, Object qualifier)
			throws RemoteException {
		return createRemoteJavaObjectFromLocalJavaObject(
				localObject.get((Property)createLocalObjectFromRemoteObject(property),
						createLocalJavaObjectFromRemoteJavaObject(qualifier)));
	}

	public Object get(String property) throws RemoteException {
		return createRemoteJavaObjectFromLocalJavaObject(localObject.get(property));
	}

	public Object get(String property, Object qualifier) throws RemoteException {
		return createRemoteJavaObjectFromLocalJavaObject(localObject.get(property,
				createLocalJavaObjectFromRemoteJavaObject(qualifier)));
	}

	public ReflectiveCollection<? extends RemoteObject> getComponents()
			throws RemoteException {
		return (ReflectiveCollection)createRemoteJavaObjectFromLocalJavaObject(localObject.getComponents());
	}

	public RemoteExtent getExtent() throws RemoteException {
		return new RemoteExtentImpl(localObject.getExtent());
	}

	public RemoteObject getMetaClass() throws RemoteException {
		return createRemoteObjectFromLocalObject((cmof.reflection.Object)localObject.getMetaClass());
	}

	public RemoteObject getOutermostContainer() throws RemoteException {
		return createRemoteObjectFromLocalObject(localObject.getOutermostContainer());
	}

	public boolean isInstanceOfType(RemoteObject type, boolean includeSubTypes)
			throws RemoteException {
		return localObject.isInstanceOfType((UmlClass)createLocalObjectFromRemoteObject(type), includeSubTypes);
	}

	public void removeListener(RemotePropertyChangeListener listener)
			throws RemoteException {
		localObject.removeListener(new LocalPropertyChangeListenerImpl(listener));
	}

	public void removeListener(String propertyName,
			RemotePropertyChangeListener listener) throws RemoteException {
		localObject.removeListener(propertyName, new LocalPropertyChangeListenerImpl(listener));
	}

	public void removeObjectEventHandler(RemoteObjectChangeListener handler)
			throws RemoteException {
		localObject.removeObjectEventHandler(new LocalObjectChangeListener(handler));
	}

	public Class getConcreteInterface() throws RemoteException {
		return localObject.getClass();
	}

	public Object getId() throws RemoteException {
		return ((Identity)localObject).getId();
	}

	public int remoteHashCode() throws RemoteException {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((localObject == null) ? 0 : localObject.hashCode());
		return result;
	}

	public boolean remoteEquals(Object obj) throws RemoteException {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RemoteObjectImpl other = (RemoteObjectImpl) obj;
		if (localObject == null) {
			if (other.localObject != null)
				return false;
		} else if (!localObject.equals(other.localObject))
			return false;
		return true;
	}
	
	
		
}
