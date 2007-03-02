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

import cmof.common.ReflectiveSequence;
import cmof.reflection.Argument;

import java.rmi.RemoteException;

public interface ServerObject {

	public ServerObject getMetaClass() throws java.rmi.RemoteException;

	public ServerObject container() throws java.rmi.RemoteException;

	public java.lang.Object get(ServerObject property)
			throws cmof.exception.IllegalArgumentException, java.rmi.RemoteException;

    public java.lang.Object get(ServerObject property, java.lang.Object qualifier)
                throws cmof.exception.IllegalArgumentException, java.rmi.RemoteException;


    public void set(ServerObject property, java.lang.Object value)
            throws ClassCastException, cmof.exception.IllegalArgumentException, java.rmi.RemoteException;

    public void set(ServerObject property, java.lang.Object qualifier, java.lang.Object value)
                throws ClassCastException, cmof.exception.IllegalArgumentException, java.rmi.RemoteException;

    public boolean isSet(ServerObject property) throws IllegalArgumentException, java.rmi.RemoteException;

    public boolean isSet(ServerObject property, java.lang.Object qualifier) throws IllegalArgumentException, java.rmi.RemoteException;

    public void unset(ServerObject property) throws IllegalArgumentException, java.rmi.RemoteException;

    public void unset(ServerObject property, java.lang.Object qualifier) throws IllegalArgumentException, java.rmi.RemoteException;

    public boolean ejbEquals(java.lang.Object element) throws java.rmi.RemoteException;

	public void delete() throws java.rmi.RemoteException;

	public java.lang.Object invokeOperation(ServerObject op, ReflectiveSequence<Argument> arguments) throws java.rmi.RemoteException;

	public boolean isInstanceOfType(ServerObject type, boolean includeSubTypes) throws java.rmi.RemoteException;

	public ServerObject getOutermostContainer() throws java.rmi.RemoteException;

	public java.util.Collection getComponents() throws java.rmi.RemoteException;

    public java.lang.Object get(String propertyName) throws java.rmi.RemoteException;

    public java.lang.Object get(String propertyName, java.lang.Object qualifier) throws java.rmi.RemoteException;

    public java.lang.Object invokeOperation(String opName, java.lang.Object[] args) throws java.rmi.RemoteException;

    public void set(String propertyName, java.lang.Object value) throws java.rmi.RemoteException;

    public void set(String propertyName, java.lang.Object qualifier, java.lang.Object value) throws java.rmi.RemoteException;

    public String getImplClassName() throws java.rmi.RemoteException;

	public boolean remoteEquals(java.lang.Object obj) throws RemoteException;

	public int remoteHashCode() throws RemoteException;
}
