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

package hub.sam.mof.reflection.client.impl;

import cmof.Operation;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Argument;
import cmof.reflection.Extent;
import cmof.reflection.ObjectChangeListener;
import hub.sam.mof.PlugInActivator;
import hub.sam.mof.reflection.client.ClientObject;
import hub.sam.mof.reflection.server.ServerObject;
import hub.sam.mof.jocl.standardlib.OclModelElement;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class ClientObjectImpl extends AbstractBridge implements ClientObject {

    protected static ClientObject getClientObject(ServerObject serverObject) {
        if (serverObject == null) {
            return null;
        }
        String className;
        try {
            className = serverObject.getImplClassName();
        } catch (RemoteException e1) {
            throw new RuntimeException(e1);
        }
        try {
            java.lang.reflect.Constructor implementation = null;
            implementation = PlugInActivator.getClassLoader().loadClass(className).getConstructor(new java.lang.Class[] {ServerObject.class});
            return (ClientObject)implementation.newInstance(serverObject);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    protected ServerObject getServerObject() {
        return remoteObject;
    }

    private final ServerObject remoteObject;

    protected ClientObjectImpl(ServerObject remote) {
        this.remoteObject = remote;
    }

    public Object get(String propertyName) {
        try {
            return deserverizeRemoteValue(remoteObject.get(propertyName));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(String propertyName, Object qualifier) {
        try {
            return deserverizeRemoteValue(remoteObject.get(propertyName, serverizeLocalValue(qualifier)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Object invokeOperation(String opName, Object[] args) {
        Object[] remoteArgs = new Object[args.length];
        for(int i = 0; i < args.length; i++) {
            remoteArgs[i] = serverizeLocalValue(args[i]);
        }
        try {
            return deserverizeRemoteValue(remoteObject.invokeOperation(opName, remoteArgs));
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addObjectEventHandler(ObjectChangeListener handler) {
        throw new RuntimeException("not implemented");
    }

    public void set(String propertyName, Object value) {
        try {
            remoteObject.set(propertyName, serverizeLocalValue(value));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String propertyName, Object qualifier, Object value) {
        try {
            remoteObject.set(propertyName, serverizeLocalValue(qualifier), serverizeLocalValue(value));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientObject container() {
        try {
            return getLocalValueFromServerObject(remoteObject.container());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientObject getOutermostContainer() {
        try {
            return getLocalValueFromServerObject(remoteObject.getOutermostContainer());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ReflectiveCollection<? extends ClientObject> getComponents() {
        try {
            return new ClientReflectiveCollectionCollectionImpl<ClientObject>(remoteObject.getComponents());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public UmlClass getMetaClass() {
        try {
            return (cmof.UmlClass)getLocalValueFromServerObject(remoteObject.getMetaClass());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(Property property) throws IllegalArgumentException {
        try {
            return deserverizeRemoteValue(remoteObject.get(getServerObjectFromLocalValue((ClientObject)property)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Object get(Property property, Object qualifier) {
        try {
            return deserverizeRemoteValue(remoteObject.get(
                    getServerObjectFromLocalValue((ClientObject)property), serverizeLocalValue(qualifier)));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Property property, Object value) throws ClassCastException,
            IllegalArgumentException {
        try {
            remoteObject.set(getServerObjectFromLocalValue((ClientObject)property), serverizeLocalValue(value));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Property property, Object qualifier, Object value) {
        try {
            remoteObject.set(getServerObjectFromLocalValue((ClientObject)property),
                    serverizeLocalValue(qualifier), serverizeLocalValue(value));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSet(Property property) throws IllegalArgumentException {
        try {
            return remoteObject.isSet(getServerObjectFromLocalValue((ClientObject)property));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSet(Property property, Object qualifier) throws IllegalArgumentException {
        try {
            return remoteObject.isSet(getServerObjectFromLocalValue((ClientObject)property),
                    serverizeLocalValue(qualifier));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void unset(Property property) throws IllegalArgumentException {
        try {
            remoteObject.unset(getServerObjectFromLocalValue((ClientObject)property));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void unset(Property property, Object qualifier) {
        try {
            remoteObject.unset(getServerObjectFromLocalValue((ClientObject)property),
                    serverizeLocalValue(qualifier));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        try {
            remoteObject.delete();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Object invokeOperation(Operation op,
                                  ReflectiveSequence<Argument> arguments) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isInstanceOfType(UmlClass type, boolean includeSubTypes) {
        try {
            return remoteObject.isInstanceOfType(getServerObjectFromLocalValue((ClientObject)type), includeSubTypes);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public Extent getExtent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientObjectImpl) {
            try {
                if (remoteObject instanceof hub.sam.mof.reflection.server.ejb.ServerObject) {
                    return remoteObject.ejbEquals(getServerObjectFromLocalValue((ClientObject)obj));
                } else {
                    return remoteObject.remoteEquals(getServerObjectFromLocalValue((ClientObject)obj));
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }

    private int hashCode = -1;

    @Override
    public int hashCode() {
        if (hashCode == -1) {
            try {
                hashCode = remoteObject.remoteHashCode();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return hashCode;
    }

    // TODO: add implementation
    public void addListener(PropertyChangeListener listener) {
    }

    // TODO: add implementation
    public void removeListener(PropertyChangeListener listener) {
    }

    // TODO: add implementation
    public OclModelElement ocl() {
        return null;
    }

    public void addListener(String propertyName, PropertyChangeListener listener) {
        // TODO Auto-generated method stub
        
    }

    public void removeListener(String propertyName, PropertyChangeListener listener) {
        // TODO Auto-generated method stub
        
    }

}