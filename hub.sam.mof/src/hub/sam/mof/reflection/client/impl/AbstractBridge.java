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

package hub.sam.mof.reflection.client.impl;

import cmof.common.*;
import hub.sam.mof.reflection.server.*;
import hub.sam.mof.reflection.client.*;
import java.util.*;

public class AbstractBridge {
	protected ClientObject getLocalValueFromServerObject(ServerObject serverObject) {
		if (serverObject == null) {
			return null;
		}
		return ClientObjectImpl.getClientObject(serverObject);
	}
	
	protected ServerObject getServerObjectFromLocalValue(ClientObject localValue) {
		return ((ClientObjectImpl)localValue).getServerObject();		
	}
    
	@SuppressWarnings("unchecked")
	protected Object serverizeLocalValue(Object localValue) {         
        if (localValue instanceof ClientObject) {
            return getServerObjectFromLocalValue((ClientObject)localValue);
        } else if (localValue instanceof Iterable) {
        	Collection result = new Vector();
        	for (Object element: (Iterable)localValue) {
        		result.add(serverizeLocalValue(element));
        	}
        	return result;
        } else {
            return localValue; 
        }  
    }
    
    protected Object deserverizeRemoteValue(Object remoteValue) {
        if (remoteValue instanceof ServerObject) {
            return getLocalValueFromServerObject((ServerObject)remoteValue);
        } else if (remoteValue instanceof ServerReflectiveCollection) {
        	if (remoteValue instanceof ServerReflectiveSequence) {
        		return new ClientRelfectiveSequenceImpl((ServerReflectiveSequence)remoteValue);
        	} else {
        		return new ClientReflectiveCollectionImpl((ServerReflectiveCollection)remoteValue);
        	}
        } else if (remoteValue instanceof Collection) {
        	return new ClientReflectiveCollectionCollectionImpl((Collection)remoteValue);
        } else {
            return remoteValue;
        }
    }
}
