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

import java.rmi.RemoteException;
import java.io.*;
import java.util.Collection;

import org.jdom.JDOMException;

import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.reflection.server.*;
import hub.sam.mof.xmi.XmiException;
import cmof.reflection.*;
import hub.sam.mof.*;

public class ServerRepositoryImpl extends AbstractBridge implements ServerRepository {

	private Repository localRepository;
	
	protected final void create(Repository local) {
		this.localRepository = local;
	}
		
	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public ServerExtent getExtent(String name) {
		Extent localExtent = localRepository.getExtent(name);
		if (localExtent == null) {
			return null;
		} else {
			return getFactory().createExtent(localExtent);
		}		
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public Collection getExtentNames() {
		return new java.util.Vector<java.lang.Object>(localRepository.getExtentNames());
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public ServerExtent createExtent(String name) {
		return getFactory().createExtent(localRepository.createExtent(name));
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public void deleteExtent(String name) {
		localRepository.deleteExtent(name);
	}

	/**
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public ServerFactory createFactory(ServerExtent forExtent,
			ServerObject forPackage) {
		return getFactory().createFactory(localRepository.createFactory(				
			getLocalExtent(forExtent), (cmof.Package)getLocalValueFromServerObject(forPackage)));		
	}
	
	/**
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public void addStaticModel(String staticModelClassName) throws SecurityException, NoSuchMethodException, ClassNotFoundException {
		localRepository.addStaticModel(PlugInActivator.getClassLoader().loadClass(staticModelClassName));		
	}

	/** 
	 * @throws MetaModelException 
	 * @throws XmiException 
	 * @throws IOException 
	 * @throws JDOMException 
	 * @ejb.interface-method  view-type = "remote"
	 */	
	public void addXmiModel(ServerExtent extent, ServerObject metaModel, byte[] xmi, String name, int type, boolean createExtent) throws RemoteException, JDOMException, XmiException, MetaModelException {
		InputStream xmiStream = new ByteArrayInputStream(xmi);
		try {
			if (!createExtent) {												
				localRepository.loadXmiModel(getLocalExtent(extent), (cmof.Package)getLocalValueFromServerObject(metaModel), xmiStream, type);
			} else {
				localRepository.addXmiModel(xmiStream, name, type);
			}
		} catch (IOException e) {
			throw new RemoteException();
		} 
	}
	
	private Extent getLocalExtent(ServerExtent extent) {
		if (extent == null) {
			return null;
		}
		Extent localExtent = null;
		if (extent instanceof hub.sam.mof.reflection.server.ejb.ServerExtent) {
			try {
				java.util.List<java.lang.Object> extentId = ((hub.sam.mof.reflection.server.ejb.ServerExtent)extent).getLocalExtentId();
				localExtent = (cmof.reflection.Extent)Repository.getLocalRepository().resolveFullId(extentId);
			} catch(java.rmi.RemoteException e) {
				throw new RuntimeException(e);
			}			
		} else {
			localExtent = ((ServerExtentImpl)extent).localExtent;
		}
		return localExtent;
	}

	public void reset() throws RemoteException {
		localRepository.reset();
	}
}
