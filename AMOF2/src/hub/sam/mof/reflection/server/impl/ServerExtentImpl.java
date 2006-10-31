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

package hub.sam.mof.reflection.server.impl;

import hub.sam.mof.reflection.server.*;
import cmof.exception.QueryParseException;
import cmof.reflection.*;
import java.util.*;

public class ServerExtentImpl extends AbstractBridge implements ServerExtent {
    
    protected Extent localExtent;
            
    protected void create(Extent local) {
    	this.localExtent = local;
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
    @SuppressWarnings ("unchecked")
    public Collection getObject() {
    	cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> localResult = localExtent.getObject();
    	Collection result = new Vector(localResult.size());
    	for (cmof.reflection.Object o: localResult) {
    		result.add(getServerObjectFromLocalValue(o));
    	}
    	
        return result;
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
    @SuppressWarnings ("unchecked")
    public Collection objectsOfType(ServerObject type, boolean includeSubtypes) {
    	cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> localResult = localExtent.objectsOfType((cmof.UmlClass)getLocalValueFromServerObject(type), includeSubtypes);
    	Collection result = new Vector(localResult.size());
    	for (cmof.reflection.Object o: localResult) {
    		result.add(getServerObjectFromLocalValue(o));
    	}
    	
        return result;
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
    public ServerObject query(String queryString) throws QueryParseException {
        return getServerObjectFromLocalValue(localExtent.query(queryString));
    }
}
