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

import hub.sam.mof.reflection.server.ServerReflectiveSequence;

import java.rmi.RemoteException;
import java.util.Iterator;

import cmof.common.ReflectiveSequence;

public class ClientRelfectiveSequenceImpl<E> extends ClientReflectiveCollectionImpl<E> implements
		ReflectiveSequence<E> {

	private final ServerReflectiveSequence remote;
	
	protected ServerReflectiveSequence remoteSequence() {
		return remote;
	}
	
	protected ClientRelfectiveSequenceImpl(ServerReflectiveSequence remote) {
		super(remote);
		this.remote = remote;
	}
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		try {
			return (E)deserverizeRemoteValue(remoteSequence().get(index));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		} 
	}

    // TODO: dummy
	public E set(int index, Object element) {
		try {
			remoteSequence().set(index, serverizeLocalValue(element));
            return null;
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}		
	}

	public void add(int index, Object element) {
		try {
			remoteSequence().add(index, serverizeLocalValue(element));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

    // TODO: dummy
	@SuppressWarnings("unchecked")
	public boolean addAll(int index, Iterable<? extends Object> elements) {
		try {
			remoteSequence().addAll(index, (Iterable)serverizeLocalValue(elements));
            return true;
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

    // TODO: dummy
	public E remove(int index) {
		try {
			remoteSequence().remove(index);
            return null;
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}
	
	public ReflectiveSequence<E> subList(int from, int to) {
		try {
			return new ClientRelfectiveSequenceImpl<E>(remoteSequence().subList(from, to));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}			
}
