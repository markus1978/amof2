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

import java.util.Iterator;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.reflection.client.impl.ClientObjectImpl;

public class TypeWrapperSetImpl<E> implements ReflectiveCollection<E> {
    
    private final ReflectiveCollection untypedSet;
    protected final ObjectImpl objectImpl;
    protected final String propertyName;
    
    public ReflectiveCollection getUnypedSet() {
    	return untypedSet;
    }
    
    public TypeWrapperSetImpl(ReflectiveCollection untypedSet) {
        this.untypedSet = untypedSet;
        this.objectImpl = null;
        this.propertyName = null;
    }

    public TypeWrapperSetImpl(ReflectiveCollection untypedSet, ObjectImpl objectImpl, String propertyName) {
        this.untypedSet = untypedSet;
        this.objectImpl = objectImpl;
        this.propertyName = propertyName;
    }    

    // dummy
    public TypeWrapperSetImpl(ReflectiveCollection untypedSet, ClientObjectImpl clientObjectImpl, String propertyName) {
        this.untypedSet = untypedSet;
        this.objectImpl = null;
        this.propertyName = null;
    }
    
    public boolean add(Object element) {
    	boolean result = untypedSet.add(element);
   		if (result && objectImpl != null && objectImpl.hasListeners()) {
   			objectImpl.firePropertyChange(propertyName, null, null);
   		}
        return result;
    }

    public boolean contains(Object element) {
        return untypedSet.contains(element);
    }

    public boolean remove(Object element) {
    	boolean result = untypedSet.remove(element);
   		if (result && objectImpl != null && objectImpl.hasListeners()) {
   			objectImpl.firePropertyChange(propertyName, null, null);
   		}
        return result;
    }

    @SuppressWarnings("unchecked")
	public boolean addAll(Iterable<? extends Object> elements) {
    	boolean result = untypedSet.addAll(elements);
   		if (result && objectImpl != null && objectImpl.hasListeners()) {
   			objectImpl.firePropertyChange(propertyName, null, null);
   		}
        return result;
    }

    @SuppressWarnings("unchecked")
	public boolean containsAll(Iterable<? extends Object> elements) {
        return untypedSet.containsAll(elements);
    }

    @SuppressWarnings("unchecked")
	public boolean removeAll(Iterable<? extends Object> elements) {
    	boolean result = untypedSet.removeAll(elements);
   		if (result && objectImpl != null && objectImpl.hasListeners()) {
   			objectImpl.firePropertyChange(propertyName, null, null);
   		}
        return result;
    }

    @SuppressWarnings("unchecked")
	public int size() {
        return untypedSet.size();
    }

    @SuppressWarnings("unchecked")
	public Iterator<E> iterator() {
        return (Iterator<E>)untypedSet.iterator();
    } 
 
    @Override
	public String toString() {
        return untypedSet.toString();
    }

	public void clear() {
        boolean notEmpty = untypedSet.size() > 0;
		untypedSet.clear();		
        if (notEmpty && objectImpl != null && objectImpl.hasListeners()) {
            objectImpl.firePropertyChange(propertyName, null, null);
        }
	}
}
