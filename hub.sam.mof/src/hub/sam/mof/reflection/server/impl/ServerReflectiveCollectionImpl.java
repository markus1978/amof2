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

import java.rmi.RemoteException;

import hub.sam.mof.reflection.server.*;
import cmof.common.ReflectiveCollection;

public class ServerReflectiveCollectionImpl<E> extends AbstractBridge 
		implements ServerReflectiveCollection {
	
	private ReflectiveCollection<E> localCollection;
	
	protected ReflectiveCollection<E> local() {
		return localCollection;
	}
	
	protected void create(ReflectiveCollection<E> local) {
		this.localCollection = local;
	}
	
	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public boolean add(Object element) {
		return local().add(deserverizeRemoteValue(element));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public boolean contains(Object element) {		
		return local().contains(deserverizeRemoteValue(element));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public boolean remove(Object element) {
		return local().remove(deserverizeRemoteValue(element));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public boolean addAll(Iterable elements) {
		boolean result = false;
		for (Object element: elements) {
			result |= local().add(deserverizeRemoteValue(element));
		}
		return result;
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public boolean containsAll(Iterable elements) {
		boolean result = true;		
		for (Object element: elements) {
			result &= local().contains(deserverizeRemoteValue(element));
		}
		return result;
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public boolean removeAll(Iterable elements) {
		boolean result = false;
		for (Object element: elements) {
			result |= local().remove(deserverizeRemoteValue(element));
		}
		return result;
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public int size() {
		return local().size();
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public Object[] iterator() {
		Object[] copy = new Object[localCollection.size()];
		int i = 0;
		for (Object element: localCollection) {
			copy[i++] = serverizeLocalValue(element);
		}
		return copy;			
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */
	public void clear() {
		localCollection.clear();		
	}
}
