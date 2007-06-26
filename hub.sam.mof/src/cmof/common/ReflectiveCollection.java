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

package cmof.common;

import java.io.Serializable;

public interface ReflectiveCollection<E> extends Iterable<E>, Serializable {
    
    public boolean add(Object element);

    public boolean contains(Object element);
    
    public boolean remove(Object element);
    
    public boolean addAll(Iterable<? extends Object> elements);

    public boolean containsAll(Iterable<? extends Object> elements);
    
    public boolean removeAll(Iterable<? extends Object> elements);
    
    public int size(); 
    
    public void clear();

}
