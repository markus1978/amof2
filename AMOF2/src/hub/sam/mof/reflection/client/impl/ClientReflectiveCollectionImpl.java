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

import hub.sam.mof.reflection.server.ServerReflectiveCollection;

import java.rmi.RemoteException;
import java.util.Iterator;

import cmof.common.ReflectiveCollection;

public class ClientReflectiveCollectionImpl<E> extends AbstractBridge implements
		ReflectiveCollection<E> {

	private final ServerReflectiveCollection remote;
	
	protected ServerReflectiveCollection remoteCollection() {
		return remote;
	}
	
	protected ClientReflectiveCollectionImpl(ServerReflectiveCollection remote) {
		this.remote = remote;
	}

	public boolean add(Object element) {
		try {
			return remoteCollection().add(serverizeLocalValue(element));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean contains(Object element) {
		try {
			return remoteCollection().contains(serverizeLocalValue(element));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean remove(Object element) {
		try {
			return remoteCollection().remove(serverizeLocalValue(element));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean addAll(Iterable<? extends Object> elements) {
		try {
			return remoteCollection().addAll((Iterable)serverizeLocalValue(elements));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean containsAll(Iterable<? extends Object> elements) {
		try {
			return remoteCollection().containsAll((Iterable)serverizeLocalValue(elements));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean removeAll(Iterable<? extends Object> elements) {
		try {
			return remoteCollection().removeAll((Iterable)serverizeLocalValue(elements));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public int size() {
		try {
			return remoteCollection().size();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public Iterator<E> iterator() {
		try {
			return new ClientIteratorImpl<E>(remoteCollection().iterator());
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void clear() {
		try {
			remoteCollection().clear();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}		
	}	
}
