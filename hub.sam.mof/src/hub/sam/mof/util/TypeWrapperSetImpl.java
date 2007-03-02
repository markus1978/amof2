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

import sun.misc.FpUtils;

import cmof.Property;
import cmof.common.ReflectiveCollection;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.reflection.client.impl.ClientObjectImpl;

public class TypeWrapperSetImpl<E> implements ReflectiveCollection<E> {
    
    private final ReflectiveCollection untypedSet;
    protected final ObjectImpl fObject;
    protected final String propertyName;
    
    public ReflectiveCollection getUnypedSet() {
    	return untypedSet;
    }
    
    public TypeWrapperSetImpl(ReflectiveCollection untypedSet) {
        this.untypedSet = untypedSet;
        this.fObject = null;
        this.propertyName = null;
    }

    public TypeWrapperSetImpl(ReflectiveCollection untypedSet, ObjectImpl objectImpl, String propertyName) {
        this.untypedSet = untypedSet;
        this.fObject = objectImpl;
        this.propertyName = propertyName;
    }    

    // dummy
    public TypeWrapperSetImpl(ReflectiveCollection untypedSet, ClientObjectImpl clientObjectImpl, String propertyName) {
        this.untypedSet = untypedSet;
        this.fObject = null;
        this.propertyName = null;
    }
    
    public boolean add(Object element) {
    	boolean result = untypedSet.add(element);

        return result;
    }

    public boolean contains(Object element) {
        return untypedSet.contains(element);
    }

    public boolean remove(Object element) {
    	return untypedSet.remove(element);        
    }

    @SuppressWarnings("unchecked")
	public boolean addAll(Iterable<? extends Object> elements) {
    	return untypedSet.addAll(elements);   		
    }

    @SuppressWarnings("unchecked")
	public boolean containsAll(Iterable<? extends Object> elements) {
        return untypedSet.containsAll(elements);
    }

    @SuppressWarnings("unchecked")
	public boolean removeAll(Iterable<? extends Object> elements) {
    	return  untypedSet.removeAll(elements);
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
		untypedSet.clear();		        
	}
	
	// required by the oslo OCL adapter
	public Property getProperty() {
		return fObject.getSemantics().getProperty(propertyName);
	}
}
