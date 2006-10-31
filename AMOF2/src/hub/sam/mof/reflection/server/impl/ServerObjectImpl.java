/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package hub.sam.mof.reflection.server.impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.EntityBean;

import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Argument;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.reflection.server.*;
import hub.sam.mof.reflection.server.ejb.ServerObjectBean;
import hub.sam.mof.util.AssertionException;

public class ServerObjectImpl extends AbstractBridge implements ServerObject {

    protected cmof.reflection.Object localObject = null;

    private static final Map<cmof.reflection.Object, ServerObject> serverObjects = new HashMap<cmof.reflection.Object, ServerObject>();
    protected static ServerObject getServerObjectForLocalObject(cmof.reflection.Object localObject, AbstractBridge bridge) {
    	ServerObject result = serverObjects.get(localObject);
    	if (result == null) {
    		result = bridge.getFactory().createObject(localObject);
    		if (result == null) {
    			throw new AssertionException();
    		}
    		serverObjects.put(localObject, result);
    	}
    	return result;
    }

    protected final void create(cmof.reflection.Object local) {
    	this.localObject = local;
    }

    protected cmof.reflection.Object getLocalObject() {
    	return localObject;
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public ServerObject getMetaClass() {
    	return getServerObjectFromLocalValue((cmof.reflection.Object)localObject.getMetaClass());
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public ServerObject container() {
    	return getServerObjectFromLocalValue(localObject.container());
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public Object get(ServerObject property) throws IllegalArgumentException {
        Object localValue = localObject.get((cmof.Property)getLocalValueFromServerObject(property));
        return serverizeLocalValue(localValue);
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public Object get(ServerObject property, Object qualifier)
            throws IllegalArgumentException, RemoteException {
        Object localValue = localObject.get((cmof.Property)getLocalValueFromServerObject(property),
                deserverizeRemoteValue(qualifier));
        return serverizeLocalValue(localValue);
    }

    /**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public void set(ServerObject property, Object value)
            throws ClassCastException, IllegalArgumentException {
        localObject.set((cmof.Property)getLocalValueFromServerObject(property), deserverizeRemoteValue(value));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public void set(ServerObject property, Object qualifier, Object value)
            throws ClassCastException, IllegalArgumentException, RemoteException {
        localObject.set((cmof.Property)getLocalValueFromServerObject(property),
                deserverizeRemoteValue(qualifier), deserverizeRemoteValue(value));
    }

    /**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public boolean isSet(ServerObject property)
            throws java.lang.IllegalArgumentException {
        return localObject.isSet((cmof.Property)getLocalValueFromServerObject(property));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public boolean isSet(ServerObject property, Object qualifier) throws java.lang.IllegalArgumentException, RemoteException {
        return localObject.isSet((cmof.Property)getLocalValueFromServerObject(property),
                deserverizeRemoteValue(qualifier));
    }

    /**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public void unset(ServerObject property)
            throws java.lang.IllegalArgumentException {
        localObject.unset((cmof.Property)getLocalValueFromServerObject(property));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public void unset(ServerObject property, Object qualifier) throws java.lang.IllegalArgumentException, RemoteException {
        localObject.unset((cmof.Property)getLocalValueFromServerObject(property),
                deserverizeRemoteValue(qualifier));
    }

    /**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public void delete() {
        localObject.delete();
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public Object invokeOperation(ServerObject op,
            ReflectiveSequence<Argument> arguments) {
        return null; //TODO (complicated: clientize arguments)
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public boolean isInstanceOfType(ServerObject type, boolean includeSubTypes) {
        return localObject.isInstanceOfType((cmof.UmlClass)((ServerObjectImpl)type).localObject, includeSubTypes);
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public ServerObject getOutermostContainer() {
    	return getServerObjectForLocalObject(localObject.getOutermostContainer(), this);
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    @SuppressWarnings ("unchecked")
    public Collection getComponents() {
    	cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> localResult = localObject.getComponents();
    	Collection result = new Vector(localResult.size());
    	for (cmof.reflection.Object o: localResult) {
    		result.add(getServerObjectFromLocalValue(o));
    	}

        return result;
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public Object get(String propertyName) {
        return serverizeLocalValue(localObject.get(propertyName));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public Object get(String propertyName, Object qualifier) throws RemoteException {
        return serverizeLocalValue(localObject.get(propertyName,
                deserverizeRemoteValue(qualifier)));
    }

    /**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public Object invokeOperation(String opName, Object[] args) {
    	Object[] localArgs = new Object[args.length];
    	for(int i = 0; i < args.length; i++) {
			localArgs[i] = deserverizeRemoteValue(args[i]);
		}
    	return serverizeLocalValue(((hub.sam.mof.reflection.ObjectImpl)localObject).invokeOperation(opName, localArgs));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public void set(String propertyName, Object value) {
        localObject.set(propertyName, deserverizeRemoteValue(value));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
    public void set(String propertyName, Object qualifier, Object value) throws RemoteException {
        localObject.set(propertyName, deserverizeRemoteValue(qualifier), deserverizeRemoteValue(value));
    }

    /**
	 * @ejb.interface-method  view-type = "remote"
	 */
	public String getImplClassName() {
		return JavaMapping.mapping.getFullQualifiedJavaIdentifier(localObject.getMetaClass()) + "ClientImpl";
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
	public boolean remoteEquals(java.lang.Object obj) {
		if (obj instanceof ServerObject) {
			return localObject.equals(getLocalValueFromServerObject((ServerObject)obj));
		} else {
			return false;
		}
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
	public int remoteHashCode() {
		return localObject.hashCode();
	}

	public boolean ejbEquals(Object element) throws RemoteException {
		throw new AssertionException();
	}
}
