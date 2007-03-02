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

package hub.sam.mof.test;

import warehouse.WarehouseModel;
import hub.sam.mof.reflection.client.*;
import hub.sam.mof.reflection.client.impl.*;
import hub.sam.mof.*;
import cmof.reflection.*;

public class ClientServer extends junit.framework.TestCase {
	
	public ClientServer() {
		super("ClientServer");
	}
	
	@Override
	public void setUp() {
		// empty
	}
	
	protected ClientRepository getRepository() {
		return Repository.connectToLocalRepository();
	}
	
	public void testReflection() {
		ClientRepository repository = getRepository();
		assertNotNull(repository);
		repository.getExtentNames().iterator().next().equals(Repository.CMOF_EXTENT_NAME);
	}

	public void testModel() {
		ClientRepository repository = getRepository();		
		Extent cmof = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		assertNotNull(cmof);
		cmof.Package cmofPackage = (cmof.Package)cmof.query("Package:cmof");
		assertNotNull(cmofPackage);
		assertEquals("cmof", cmofPackage.getName());
		assertTrue(cmofPackage.getMember().size() > 0);
	}
	
	public void testFactory() {
		ClientRepository repository = getRepository();		
		Extent cmof = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		cmof.Package cmofPackage = (cmof.Package)cmof.query("Package:cmof");		
		cmof.cmofFactory factory = (cmof.cmofFactory)repository.createFactory(repository.createExtent("test"), (cmof.reflection.Object)cmofPackage);
		assertNotNull(factory);
		cmof.UmlClass testClass = factory.createUmlClass();
		assertNotNull(testClass);
		testClass.setName("Test");
		assertEquals(testClass.getName(),"Test");
		for (java.lang.Object o: cmofPackage.getMember()) {
			assertNotNull(o);
		}
	}
	
	public void testNonIdentityCollections() {
		ClientRepository repository = getRepository();		
		Extent cmof = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		cmof.reflection.Object one = null;
		for (cmof.reflection.Object object: cmof.getObject()) {
			one = object;
			assertNotNull(object);
		}
		for (cmof.reflection.Object object: one.getComponents()) {
			assertNotNull(object);
		}
	}
	
	public void testOperationInvocation() {
		ClientRepository repository = getRepository();		
		Extent cmof = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		cmof.UmlClass cmofClass = (cmof.UmlClass)cmof.query("Package:cmof/Class:Class");
		try {
			assertTrue(cmofClass.allParents().size() > 1);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (RuntimeException e) {
			assertTrue(false);
		}
	}
	
	public void testContainer() {
		ClientRepository repository = getRepository();		
		Extent cmof = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		try {
			for(cmof.reflection.Object o: cmof.getObject()) {
				o.container();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	public void testAddStaticModel() throws Exception {
		getRepository().addStaticModel(WarehouseModel.class.getName());
		assertTrue(getRepository().getExtentNames().contains(WarehouseModel.class.getName()));
		for (cmof.reflection.Object o: getRepository().getExtent(WarehouseModel.class.getName()).getObject()) {
			assertNotNull(o.getMetaClass());
		}
	}
	
	public void testAddXmiModel() throws Exception {
		getRepository().addXmiModel("resources/models/test/warehouse.xml");
		assertTrue(getRepository().getExtentNames().contains("resources/models/test/warehouse.xml"));
		for (cmof.reflection.Object o: getRepository().getExtent("resources/models/test/warehouse.xml").getObject()) {
			assertNotNull(o.getMetaClass());
		}
	}
	
	public void testEquals() throws Exception {
		ClientRepository repository = getRepository();		
		Extent cmof = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		assertTrue(cmof.query("Package:cmof").equals(cmof.query("Package:cmof")));
		assertEquals(cmof.query("Package:cmof").hashCode(), cmof.query("Package:cmof").hashCode());		
		assertFalse(cmof.query("Package:cmof").equals(cmof.query("Package:core")));
		assertFalse(cmof.query("Package:cmof").hashCode() == cmof.query("Package:core").hashCode());
	}
}
