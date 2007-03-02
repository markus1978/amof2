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

import cmof.reflection.Factory;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.reflection.*;
import hub.sam.mof.reflection.server.ServerFactory;
import hub.sam.mof.reflection.server.ServerObject;

public class ServerFactoryImpl extends AbstractBridge implements ServerFactory {

    private Factory localFactory;
    
    protected void create(Factory local) {
        this.localFactory = local;
    }
    
	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
    public Object createFromString(ServerObject dataType, String string) {
        return serverizeLocalValue(localFactory.createFromString((cmof.DataType)getLocalValueFromServerObject(dataType), string));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
    public String convertToString(ServerObject dataType, Object object) {        
        return localFactory.convertToString((cmof.DataType)getLocalValueFromServerObject(dataType), deserverizeRemoteValue(object));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
    public ServerObject create(ServerObject metaClass) {
        return getServerObjectFromLocalValue(localFactory.create((cmof.UmlClass)getLocalValueFromServerObject(metaClass)));
    }

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public ServerObject create(String metaClassName) {
		return getServerObjectFromLocalValue(((FactoryImpl)localFactory).create(metaClassName));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public String getImplClassName() {
		return JavaMapping.mapping.getFullQualifiedFactoryNameForPackage(((FactoryImpl)localFactory).getMetaModel()) + "ClientImpl";
	}
}
