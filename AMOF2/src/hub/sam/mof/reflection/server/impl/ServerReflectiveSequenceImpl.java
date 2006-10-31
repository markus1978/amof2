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

import cmof.common.ReflectiveSequence;
import hub.sam.mof.reflection.server.ServerReflectiveSequence;

public class ServerReflectiveSequenceImpl<E> extends
		ServerReflectiveCollectionImpl<E> implements ServerReflectiveSequence {

	private ReflectiveSequence<E> localSequence;
	
	@Override
	protected ReflectiveSequence<E> local() {
		return localSequence;
	}
	
	protected void create(ReflectiveSequence<E> local) {
		super.create(local);
		this.localSequence = local;
	}
	
	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public Object get(int index) {
		return deserverizeRemoteValue(local().get(index));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public Object set(int index, Object element) {
		return deserverizeRemoteValue(local().set(index, serverizeLocalValue(element)));		
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public void add(int index, Object element) {
		local().add(index, serverizeLocalValue(element));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public boolean addAll(int index, Iterable elements) {
        boolean result = false;
		for (Object element: elements) {
            result = local().add(serverizeLocalValue(element)) || result;
		}
        return result;
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public Object remove(int index) {
		return deserverizeRemoteValue(local().remove(index));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public ServerReflectiveSequence subList(int from, int to) {
		return getFactory().createReflectiveSequence(local().subList(from, to));
	}
}
