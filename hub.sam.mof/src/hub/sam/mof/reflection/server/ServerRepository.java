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

import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;

import java.rmi.RemoteException;
import java.util.Collection;

import org.jdom.JDOMException;

public interface ServerRepository {
	public void reset() throws RemoteException;
	
    public ServerExtent getExtent(String name) throws java.rmi.RemoteException;
    
    public Collection getExtentNames() throws java.rmi.RemoteException;

    public ServerExtent createExtent(String name) throws java.rmi.RemoteException;
    
    public void deleteExtent(String name) throws java.rmi.RemoteException;
    
    public ServerFactory createFactory(ServerExtent forExtent, ServerObject forPackage) throws java.rmi.RemoteException;     
 
    public void addStaticModel(String staticModelClassName) throws java.rmi.RemoteException, SecurityException, NoSuchMethodException, ClassNotFoundException;
    
    public void addXmiModel(ServerExtent extent, ServerObject metaModel, byte[] xmi, String name, int type, boolean createExtent) throws java.rmi.RemoteException, JDOMException, XmiException, MetaModelException;
}
