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

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.reflection.client.ClientObject;
import hub.sam.mof.reflection.client.ClientRepository;
import hub.sam.mof.reflection.server.ServerExtent;
import hub.sam.mof.reflection.server.ServerRepository;
import hub.sam.mof.xmi.XmiException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Collection;

import org.jdom.JDOMException;

import cmof.Package;
import cmof.reflection.Extent;
import cmof.reflection.Factory;
import cmof.reflection.Object;

public class ClientRepositoryImpl extends AbstractBridge implements
		ClientRepository {
				
	private final ServerRepository remote;
	
	public ClientRepositoryImpl(ServerRepository remote) {
		this.remote = remote;
	}

	public Extent getExtent(String name) {
		try {
			ServerExtent serverExtent = remote.getExtent(name);
			if (serverExtent == null) {
				return null;
			} else {
				return new ClientExtentImpl(serverExtent);
			}
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getExtentNames() {
		try {
			return remote.getExtentNames();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public Extent createExtent(String name) {
		try {
			return new ClientExtentImpl(remote.createExtent(name));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteExtent(String name) {
		try {
			remote.deleteExtent(name);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public Factory createFactory(Extent forExtent, Object forPackage) {
		try {
			return ClientFactoryImpl.createClientFactory(remote.createFactory(
					((ClientExtentImpl)forExtent).getRemoteExtent(), getServerObjectFromLocalValue((ClientObject)forPackage)));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}

	public void addStaticModel(String staticModelClassName) throws SecurityException, NoSuchMethodException, ClassNotFoundException {
		try {
			remote.addStaticModel(staticModelClassName);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void addXmiModel(Extent extent, Package metaModel, String xmiFileName, int type, boolean createExtent) throws IOException, XmiException, hub.sam.mof.instancemodel.MetaModelException {
		File xmiFile = new File(xmiFileName);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(xmiFile));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int read = reader.read();
		while (read != -1) {
			out.write(read);
			read = reader.read();
		}
		try {
			if (!createExtent) {
				remote.addXmiModel(((ClientExtentImpl)extent).getRemoteExtent(), getServerObjectFromLocalValue((ClientObject)metaModel), out.toByteArray(), xmiFileName, type, createExtent);
			} else {
				remote.addXmiModel(null, null, out.toByteArray(), xmiFileName, type, createExtent);
			}
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		} catch (JDOMException e) {
			throw new RuntimeException(e);
		}
	}		

	public void addXmiModel(String xmiFileName) throws IOException, XmiException, hub.sam.mof.instancemodel.MetaModelException {
		addXmiModel(null, null, xmiFileName, Repository.XMI2, true);
	}	
	
	public void addUnisysXmiModel(String xmiFileName) throws IOException, XmiException, hub.sam.mof.instancemodel.MetaModelException {
		addXmiModel(null, null, xmiFileName, Repository.UNISYS, true);
	}

	public void loadUnisysModel(Extent target, Package metaModel, String xmiFileName) throws IOException, XmiException, MetaModelException {
		addXmiModel(target, metaModel, xmiFileName, Repository.UNISYS, false);
		
	}

	public void loadXmiModel(Extent target, Package metaModel, String xmiFileName) throws IOException, XmiException, MetaModelException {
		addXmiModel(target, metaModel, xmiFileName, Repository.XMI2, false);		
	}

	public void reset() {
		try {
			remote.reset();
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}		
	}

	public void addMagicDrawXmiModel(String xmiFileName) throws IOException, XmiException, MetaModelException {
		addXmiModel(null, null, xmiFileName, Repository.MD, true);
		
	}

	public void loadMagicDraw(Extent target, Package metaModel, String xmiFileName) throws IOException, XmiException, MetaModelException {
		addXmiModel(target, metaModel, xmiFileName, Repository.MD, false);		
	}
}
