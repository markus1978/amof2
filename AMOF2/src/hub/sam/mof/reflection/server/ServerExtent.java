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

import cmof.exception.QueryParseException;
import java.util.*;

public interface ServerExtent {
    public Collection getObject() throws java.rmi.RemoteException;

    public Collection objectsOfType(ServerObject type,
            boolean includeSubtypes) throws java.rmi.RemoteException;

    /* TODO
    public ReflectiveCollection<? extends Link> linksOfType(ServerObject type);
    */

    /* TODO
    public ReflectiveCollection<? extends Object> linkedObjects(
            Association association, Object endObject, boolean end1to2direction);
    */

    /*
    public boolean linkExists(Association association, Object firstObject,
            Object secondObject);
    */

    public ServerObject query(java.lang.String queryString) throws QueryParseException, java.rmi.RemoteException;
}
