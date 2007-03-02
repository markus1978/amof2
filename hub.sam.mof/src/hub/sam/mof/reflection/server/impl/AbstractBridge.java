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

import java.rmi.Remote;
import java.util.*;
import hub.sam.mof.reflection.server.ServerObject;
import hub.sam.mof.util.AssertionException;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;

public abstract class AbstractBridge {
		
	protected ReflectionFactory getFactory() {
		return ReflectionFactory.getFactory();
	}
	
	protected cmof.reflection.Object getLocalValueFromServerObject(ServerObject serverObject) {
		if (serverObject == null) {
			return null;
		}
		cmof.reflection.Object localObject = null;
		if (serverObject instanceof hub.sam.mof.reflection.server.ejb.ServerObject) {
			try {
				java.util.List<Object> objectId = ((hub.sam.mof.reflection.server.ejb.ServerObject)serverObject).getLocalObjectId();
				localObject = (cmof.reflection.Object)hub.sam.mof.Repository.getLocalRepository().resolveFullId(objectId);
			} catch(java.rmi.RemoteException e) {
				throw new RuntimeException(e);
			}			
		} else {
			localObject = ((ServerObjectImpl)serverObject).getLocalObject();
		}
		if (localObject == null) {
			throw new AssertionException();
		}
		return localObject;
	}
	
	protected ServerObject getServerObjectFromLocalValue(cmof.reflection.Object localValue) {
		if (localValue == null) {
			return null;
		}
		ServerObject result = ServerObjectImpl.getServerObjectForLocalObject(localValue, this);
		if (result == null) {
			throw new AssertionException();
		}
		return result;
	}
    
	@SuppressWarnings("unchecked")
	protected Object serverizeLocalValue(Object localValue) {  
		if (localValue == null) {
			return null;
		}
        if (localValue instanceof cmof.reflection.Object) {
            Object result = getServerObjectFromLocalValue((cmof.reflection.Object)localValue);
            if (result == null) {
            	throw new AssertionException();
            }
            return result;
        } else if (localValue instanceof ReflectiveCollection) {
        	if (localValue instanceof hub.sam.util.Identity) {
	        	if (localValue instanceof ReflectiveSequence) {        		
	        		return getFactory().createReflectiveSequence((ReflectiveSequence)localValue);
	        	} else {
        			return getFactory().createReflectiveCollection((ReflectiveCollection)localValue);
        		}            
        	} else {
        		Collection result = new Vector(((ReflectiveCollection)localValue).size());
        		for (Object o: (ReflectiveCollection)localValue) {
        			result.add(serverizeLocalValue(o));
        		}
        		return result;
        	}
        } else {
            return localValue; 
        } 
    }
    
    protected Object deserverizeRemoteValue(Object remoteValue) {
    	if (remoteValue == null) {
    		return null;
    	}
    	Object result = null;
    	if (remoteValue instanceof ServerObject) {
            result = getLocalValueFromServerObject((ServerObject)remoteValue);
        } else if (remoteValue instanceof java.io.Serializable) {
            result = remoteValue;
        } else {
            throw new RuntimeException("assert");
        }
        if (result == null) {
        	throw new AssertionException();
        }
        return result;
    }
}
