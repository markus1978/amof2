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

package hub.sam.mof.reflection.client;

import hub.sam.mof.xmi.XmiException;

import java.util.Collection;

import org.jdom.JDOMException;

import cmof.Package;
import cmof.reflection.*;

public interface ClientRepository {
	public void reset();
	
	public Extent getExtent(String name);

	public Collection<String> getExtentNames();

	public Extent createExtent(String name);

	public void deleteExtent(String name);

	public Factory createFactory(Extent forExtent,
			cmof.reflection.Object forPackage);

	public void addStaticModel(String staticModelClassName)
			throws SecurityException, NoSuchMethodException,
			ClassNotFoundException;

	public void addXmiModel(String xmiFileName) throws java.io.IOException,
			XmiException, hub.sam.mof.instancemodel.MetaModelException;

	public void addUnisysXmiModel(String xmiFileName)
			throws java.io.IOException, XmiException,
			hub.sam.mof.instancemodel.MetaModelException;
	
	public void addMagicDrawXmiModel(String xmiFileName)
			throws java.io.IOException, XmiException,
			hub.sam.mof.instancemodel.MetaModelException;

	public void loadXmiModel(Extent target, Package metaModel,
			String xmiFileName) throws java.io.IOException, XmiException,
			hub.sam.mof.instancemodel.MetaModelException;

	public void loadUnisysModel(Extent target, Package metaModel,
			String xmiFileName) throws java.io.IOException, XmiException,
			hub.sam.mof.instancemodel.MetaModelException;
	
	public void loadMagicDraw(Extent target, Package metaModel,
			String xmiFileName) throws java.io.IOException, XmiException,
			hub.sam.mof.instancemodel.MetaModelException;
}
