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

import java.rmi.RemoteException;

import cmof.Association;
import cmof.DataType;
import cmof.UmlClass;
import cmof.reflection.Link;
import cmof.reflection.Object;
import hub.sam.mof.PlugInActivator;
import hub.sam.mof.reflection.client.*;
import hub.sam.mof.reflection.server.*;

public class ClientFactoryImpl extends AbstractBridge implements ClientFactory {

	protected static ClientFactory createClientFactory(ServerFactory remote) {
		String className;
		try {
			className = remote.getImplClassName();
		} catch (RemoteException e1) {
			throw new RuntimeException(e1);
		}		
		try {
			java.lang.reflect.Constructor implementation = null;
            implementation = PlugInActivator.getClassLoader().loadClass(className).getConstructor(new java.lang.Class[] {ServerFactory.class});            	
            return (ClientFactory)implementation.newInstance(remote);
		} catch (Exception e){
			throw new RuntimeException(e);
		}				
	}
	
	private final ServerFactory remoteFactory;
		
	protected ClientFactoryImpl(ServerFactory remote) {
		this.remoteFactory = remote;
	}
	
	public Object create(String metaClassName) {
		try {
			return getLocalValueFromServerObject(remoteFactory.create(metaClassName));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}		
	}

	public java.lang.Object createFromString(DataType dataType, String string) {
		try {
			return deserverizeRemoteValue(remoteFactory.createFromString(getServerObjectFromLocalValue((ClientObject)dataType), string));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public String convertToString(DataType dataType, java.lang.Object object) {
		try {
			return remoteFactory.convertToString(getServerObjectFromLocalValue((ClientObject)dataType), serverizeLocalValue(object));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public Object create(UmlClass metaClass) {
		try {
			return getLocalValueFromServerObject(remoteFactory.create(getServerObjectFromLocalValue((ClientObject)metaClass)));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public Link createLink(Association association, Object firstObject,
			Object secondObject) {
		// TODO Auto-generated method stub
		return null;
	}
}
