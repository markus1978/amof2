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

package hub.sam.util;


import java.util.*;

public class Tree<E> extends MultiMap<E, E>{

    public MultiMap<E, E> collapseTree() {
        MultiMap<E, E> result = new MultiMap<E, E>();
        for (E key: values.keySet()) {
            collectAllValues(key, result.get(key));
        }
        return result;
    }
    
    public Collection<E> getRoots() {
        Collection<E> result = new HashSet<E>();
        for (Collection<E> value: values.values()) {
            result.addAll(value);
        }
        for (Collection<E> value: values.values()) {
            for (E singleValue: value) {
                if (values.get(singleValue) != null) {
                    result.removeAll(values.get(singleValue));
                }
            }
        }
        return result;
    }
    
    public Collection<E> getLeaves(E key) {
        Collection<E> result = new HashSet<E>();
        Collection<E> children = values.get(key);
        if (children == null || children.size() == 0) {
            result.add(key);
        } else {
            for (E child: children) {
                result.addAll(getLeaves(child));
            }
        }
        return result;        
    }
    
    private void collectAllValues(E key, Collection<E> result) { 
        Collection<E> values = this.values.get(key);
        if (values != null) {
            for (E value: values) {
                result.add(value);
                collectAllValues(value, result);            
            }                
        }
    }
}
