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

import hub.sam.mof.reflection.client.ClientExtent;
import hub.sam.mof.reflection.client.ClientObject;
import hub.sam.mof.reflection.server.ServerExtent;
import cmof.Association;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.exception.QueryParseException;
import cmof.reflection.Link;
import cmof.reflection.Object;

public class ClientExtentImpl extends AbstractBridge implements ClientExtent {

	private final ServerExtent remoteExtent;
	
	protected ClientExtentImpl(ServerExtent remote) {		
		remoteExtent = remote;
	}
	
	protected ServerExtent getRemoteExtent() {
		return remoteExtent;
	}
	
	public ReflectiveCollection<? extends cmof.reflection.Object> getObject() {
		try {
			return new ClientReflectiveCollectionCollectionImpl<cmof.reflection.Object>(remoteExtent.getObject());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}		
	}

	public ReflectiveCollection<? extends Object> objectsOfType(UmlClass type,
			boolean includeSubtypes) {
		try {
			return new ClientReflectiveCollectionCollectionImpl<Object>(remoteExtent.
					objectsOfType(getServerObjectFromLocalValue((ClientObject)type), includeSubtypes));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public ReflectiveCollection<? extends Link> linksOfType(Association type) {
		// TODO Auto-generated method stub
		return null;
	}

	public ReflectiveCollection<? extends Object> linkedObjects(
			Association association, Object endObject, boolean end1to2direction) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean linkExists(Association association, Object firstObject,
			Object secondObject) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object query(String queryString) throws QueryParseException {
		try {
			return getLocalValueFromServerObject(remoteExtent.query(queryString));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public ReflectiveCollection<? extends Object> outermostComposites() {		
		return null;
	}

}
