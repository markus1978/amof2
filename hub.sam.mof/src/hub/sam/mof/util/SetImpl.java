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

import cmof.exception.IllegalAccessException;

public class SetImpl<E> implements cmof.common.ReflectiveCollection<E> {
    private final Collection<E> values;
    private boolean modifyable;
    
    public SetImpl() {
    	modifyable = true;
        this.values = new HashSet<E>();        
    }
    
    public SetImpl(int intialSize) {
    	modifyable = true;
        this.values = new HashSet<E>(intialSize);        
    }
    
    public SetImpl(Iterable<? extends E> copy) {
        this();        
        for (E elem: copy) {
            values.add(elem);
        }
    }
    
    public SetImpl(Collection<E> values) {
    	modifyable = true;
        this.values = values;
    }
    
    public void makeUnmodifyable() {
    	modifyable = false;
    }
    
    @SuppressWarnings("unchecked")
	public boolean add(Object element) {       
    	if (modifyable) {
    		return values.add((E)element);
    	} else {
    		throw new IllegalAccessException("add to a unmodifyable set");
    	}
    }

    @SuppressWarnings("unchecked")
	public boolean contains(Object element) {
        return values.contains(element);
    }

    @SuppressWarnings("unchecked")
	public boolean remove(Object element) {
    	if (modifyable) {
    		return values.remove(element);
    	} else {
    		throw new IllegalAccessException("remove to a unmodifyable set");
    	}
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

    public int size() {
        return values.size();
    }

    @Override
	public String toString() {
        return values.toString();
    }
    
    public Collection<E> toCollection() {
        Collection<E> result = new Vector<E>();
        for (E o: this) {
            result.add(o);
        }
        return result;
    }

	public void clear() {
		values.clear();		
	}
}
