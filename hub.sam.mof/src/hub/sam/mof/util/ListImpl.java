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

package hub.sam.mof.util;

import java.util.*;

public class ListImpl<E> implements cmof.common.ReflectiveSequence<E> {
	protected List<E> values;
	
	protected List<E> createList() {
		return new Vector<E>();
	}
	
	public ListImpl() {
		values = createList();
	}
	
	public ListImpl(Iterable<? extends E> copy) {		
		values = createList();
		for (E elem: copy) {
			values.add(elem);
		}
	}
	
	public ListImpl(List<E> values) {
		this.values = values;
	}
	
	public static <E> cmof.common.ReflectiveSequence<E> asList(Iterable<E> values) {
		ListImpl<E> result = new ListImpl<E>();
		for (E value: values) {
			result.values.add(value);
		}
		return result;
	}
	
	public static <E> cmof.common.ReflectiveSequence<E> asList(E[] values) {
		ListImpl<E> result = new ListImpl<E>();
		for (E value: values) {
			result.values.add(value);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(Object element) {        
		return values.add((E)element);
	}
	
	@SuppressWarnings("unchecked")
	public boolean contains(Object element) {
		return values.contains(element);
	}
	
	@SuppressWarnings("unchecked")
	public boolean remove(Object element) {
		return values.remove(element);
	}
	
	public boolean addAll(Iterable<? extends Object> elements) {
		boolean result = false;
		for (Object element: elements) {
			result = add(element) || result;
		}
		return result;
	}
	
	public boolean containsAll(Iterable<? extends Object> elements) {
		boolean result = true;
		for (Object element: elements) {
			result = result && contains(element);
		}
		return result;        
	}
	
	public boolean removeAll(Iterable<? extends Object> elements) {        
		boolean result = false;
		for (Object element: elements) {
			result = remove(element) || result;
		}
		return result;
	}
	
	public Iterator<E> iterator() {
		return values.iterator();
	}
	
	public E get(int index) {
		return values.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public E set(int index, Object element) {
		return values.set(index, (E)element);
	}
	
	@SuppressWarnings("unchecked")
	public void add(int index, Object element) {
		values.add(index, (E)element);
	}
	
	public boolean addAll(int index, Iterable<? extends Object> elements) {
        boolean modified = false;
		for (Object object: elements) {
			add(index++, object);
            modified = true;
		}
        return modified;
	}
	
	public E remove(int index) {
		return values.remove(index);
	}
	
	public int size() {
		return values.size();        
	}
	
	public cmof.common.ReflectiveSequence<E> subList(int from, int to) {
		ListImpl<E> newList = new ListImpl<E>();
		if (to < 0) // from end of list
		    to = values.size() + to; // to is negative
		newList.values = values.subList(from, to);
		return newList;
	}
	
	@Override
	public String toString() {
		return values.toString();
	}
	
	public boolean equals(Object elements) {
		if (elements instanceof Iterable) {
			Iterator iter = ((Iterable) elements).iterator();
			for (E e: values) {
				if (!e.equals(iter.next())) {
					return false;
				}
			}
		}
		return true;
	}

	public void clear() {
		values.clear();		
	}
}
