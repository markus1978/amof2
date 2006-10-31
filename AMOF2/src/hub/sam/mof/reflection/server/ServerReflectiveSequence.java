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

package hub.sam.mof.reflection.server;


public interface ServerReflectiveSequence extends ServerReflectiveCollection {

	public ServerReflectiveSequence subList(int from, int to) throws java.rmi.RemoteException;
	
    public java.lang.Object get(int index) throws java.rmi.RemoteException;
    
    public Object set(int index, Object element) throws java.rmi.RemoteException;
    
    public void add(int index, Object element) throws java.rmi.RemoteException;
    
    public boolean addAll(int index, Iterable<? extends Object> elements) throws java.rmi.RemoteException;
    
    public Object remove(int index) throws java.rmi.RemoteException;
      
}
