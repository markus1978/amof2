package hub.sam.mof.remote;

import hub.sam.mof.PlugInActivator;
import hub.sam.mof.jocl.standardlib.OclModelElement;
import hub.sam.mof.util.ListImpl;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Argument;
import cmof.reflection.Extent;
import cmof.reflection.ObjectChangeListener;

public class LocalObjectImpl implements cmof.reflection.Object {
	
	protected static cmof.reflection.Object createLocalObjectFromRemoteObject(RemoteObject remoteObject) {
		if (remoteObject == null) {
			return null;
		}
		// normalImplClass = <package>.<mclassname>Impl;
		String normalImplClass = null;
		try {
			normalImplClass = remoteObject.getConcreteInterface().getCanonicalName();
		} catch (RemoteException e) {
			if (e.getCause().getClass().equals(ClassNotFoundException.class)) {
				normalImplClass = null;
			} else {
				throw new RuntimeException(e);
			}
		}		

		if (normalImplClass == null) {
			return new LocalObjectImpl(remoteObject);
		} else {
			// localImplClass = <package>.<mclassname>LocalImpl;		
			String localImplClassName = normalImplClass.substring(0, normalImplClass.length() - 4) + "LocalImpl";
			Class localImplClass;
			try {			
				localImplClass = PlugInActivator.getClassLoader().loadClass(localImplClassName);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
			Constructor constructor;
			try {
				constructor = localImplClass.getDeclaredConstructor(new Class[] {RemoteObject.class});
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
			try {
				return (cmof.reflection.Object)constructor.newInstance(new Object[] { remoteObject });
			} catch (java.lang.IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}	
	
	protected static Object createLocalJavaObjectFromRemoteJavaObject(Object remoteObject) {
		if (remoteObject instanceof RemoteObject) {
			return createLocalObjectFromRemoteObject((RemoteObject)remoteObject);
		} else if (remoteObject instanceof ReflectiveCollection) {
			ReflectiveCollection result = new ListImpl();
			for (Object object: (ReflectiveCollection)remoteObject) {
				result.add(createLocalJavaObjectFromRemoteJavaObject(object));
			}
			return result;
		} else {
			return remoteObject;
		}
	}
	
	protected static Object createRemoteJavaObjectFromLocalJavaObject(Object localObject) {
		if (localObject instanceof cmof.reflection.Object) {
			return createRemoteObjectFromLocalObject((cmof.reflection.Object)localObject);
		} else {
			return localObject;
		}
	}
	
	protected static RemoteObject createRemoteObjectFromLocalObject(cmof.reflection.Object localObject) {
		if (localObject instanceof LocalObjectImpl) {
			return ((LocalObjectImpl)localObject).getRemoteObject();
		} else {
			throw new RemoteRepositoryException("invalid remote call");
		}
	}
	
	private final RemoteObject remoteObject;
	
	protected RemoteObject getRemoteObject() {
		return remoteObject;
	}

	protected LocalObjectImpl(final RemoteObject remoteObject) {
		super();
		this.remoteObject = remoteObject;
	}

	public void addListener(PropertyChangeListener listener) {
		try {
			remoteObject.addListener(new RemotePropertyChangeListenerImpl(listener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void addListener(String propertyName, PropertyChangeListener listener) {
		try {
			remoteObject.addListener(propertyName, new RemotePropertyChangeListenerImpl(listener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

	}

	public void addObjectEventHandler(ObjectChangeListener handler) {
		try {
			remoteObject.addObjectEventHandler(new RemoteObjectChangeListenerImpl(handler));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public cmof.reflection.Object container() {
		RemoteObject result;
		try {
			result = remoteObject.container();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		return result == null ? null : new LocalObjectImpl(result);
	}

	public void delete() {
		throw new RuntimeException("not implemented");
	}

	public java.lang.Object get(Property property) {
		try {
			return createLocalJavaObjectFromRemoteJavaObject(
					remoteObject.get(createRemoteObjectFromLocalObject((cmof.reflection.Object)property)));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public java.lang.Object get(Property property, java.lang.Object qualifier) {
		try {
			return createLocalJavaObjectFromRemoteJavaObject(
					remoteObject.get(createRemoteObjectFromLocalObject((cmof.reflection.Object)property), 
					createRemoteJavaObjectFromLocalJavaObject(qualifier)));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public java.lang.Object get(String property) {
		try {
			return createLocalJavaObjectFromRemoteJavaObject(remoteObject.get(property));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public java.lang.Object get(String property, java.lang.Object qualifier) {
		try {
			return createLocalJavaObjectFromRemoteJavaObject(remoteObject.get(property,
					createRemoteJavaObjectFromLocalJavaObject(qualifier)));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public ReflectiveCollection<? extends  cmof.reflection.Object> getComponents() {
		try {
			return (ReflectiveCollection)createLocalJavaObjectFromRemoteJavaObject(remoteObject.getComponents());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public Extent getExtent() {
		try {
			return new LocalExtentImpl(remoteObject.getExtent());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public UmlClass getMetaClass() {
		try {
			return (UmlClass)createLocalObjectFromRemoteObject(remoteObject.getMetaClass());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public  cmof.reflection.Object getOutermostContainer() {
		try {
			return createLocalObjectFromRemoteObject(remoteObject.getOutermostContainer());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public java.lang.Object invokeOperation(Operation op,
			ReflectiveSequence<Argument> arguments) {
		throw new RuntimeException("not implemented");
	}

	public java.lang.Object invokeOperation(String opName,
			java.lang.Object[] args) {
		throw new RuntimeException("not implemented");
	}

	public boolean isInstanceOfType(UmlClass type, boolean includeSubTypes) {
		try {
			return remoteObject.isInstanceOfType(createRemoteObjectFromLocalObject((cmof.reflection.Object)type), includeSubTypes);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isSet(Property property) throws IllegalArgumentException {
		throw new RuntimeException("not implemented");
	}

	public boolean isSet(Property property, java.lang.Object qualifier)
			throws IllegalArgumentException {
		throw new RuntimeException("not implemented");
	}

	public OclModelElement ocl() {
		throw new RuntimeException("not implemented");
	}

	public void removeListener(PropertyChangeListener listener) {
		try {
			remoteObject.removeListener(new RemotePropertyChangeListenerImpl(listener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeListener(String propertyName,
			PropertyChangeListener listener) {
		try {
			remoteObject.removeListener(propertyName, new RemotePropertyChangeListenerImpl(listener));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void removeObjectEventHandler(ObjectChangeListener handler) {
		try {
			remoteObject.removeObjectEventHandler(new RemoteObjectChangeListenerImpl(handler));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void set(Property property, java.lang.Object value) {
		throw new RuntimeException("not implemented");
	}

	public void set(Property property, java.lang.Object qualifier,
			java.lang.Object value) {
		throw new RuntimeException("not implemented");
	}

	public void set(String property, java.lang.Object value) {
		throw new RuntimeException("not implemented");
	}

	public void set(String property, java.lang.Object qualifier,
			java.lang.Object value) {
		throw new RuntimeException("not implemented");
	}

	public void unset(Property property) {
		throw new RuntimeException("not implemented");
	}

	public void unset(Property property, java.lang.Object qualifier) {
		throw new RuntimeException("not implemented");
	}

	public <T> T getAdaptor(Class<T> adaptorClass) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		try {
			result = PRIME * result + ((remoteObject == null) ? 0 : remoteObject.remoteHashCode());
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
		final LocalObjectImpl other = (LocalObjectImpl) obj;
		if (remoteObject == null) {
			if (other.remoteObject != null)
				return false;
		} else
			try {
				if (!remoteObject.remoteEquals(other.remoteObject))
					return false;
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			}
		return true;
	}

	
}
