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

import cmof.reflection.*;
import warehouse.*;

public class CustomCode extends AbstractRepository {

    public CustomCode() {
        super("custom code tests");
    }

    warehouse.warehouseFactory factory = null;

    @Override
	public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = WarehouseModel.createModel();
        factory = (warehouseFactory)repository.createFactory(m2Extent, (cmof.Package)m2Extent.query("Package:warehouse"));
    }

    public void testDerivedAttribute() {
        Item i = factory.createItem();
        Item ii = factory.createItem();
        i.setWeight(1);
        ii.setWeight(2);
        Box b = factory.createBox();
        b.getItem().add(i);
        b.getItem().add(ii);
        assertEquals(1, i.getWeight());
        assertEquals(2, ii.getWeight());
        assertEquals(3, b.getWeight());
        assertEquals(i.getWeight() + ii.getWeight(), b.getWeight());
    }

    public void testOpeation() {
        Item i = factory.createItem();
        i.test();
    }
    
    public void testRenaming() {
    	try {
    		factory.createBox().fooOperation();
    	} catch (Exception e) {
    		assertTrue(false);
    	}
    }

    /* TODO not yet possible
    public void testHierarchy() {
    	assertTrue(((cmof.reflection.Object)factory.createItem()).getMetaClass().allParents().size() > 0);
    }
    */
}
