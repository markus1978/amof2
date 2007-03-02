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

public class UpdateGraph extends AbstractRepository {

    public UpdateGraph() {
        super("Update graph tests");
    }
        
    warehouse.warehouseFactory factory = null;
    
    @Override
	public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = WarehouseModel.createModel();
        factory = (warehouseFactory)repository.createFactory(m2Extent, (cmof.Package)m2Extent.query("Package:warehouse"));
    }
    
    public void testAttribute() {
        Item i = factory.createItem();
        assertNull(i.getIdentifier());
        i.setIdentifier("name");
        assertEquals("name", i.getIdentifier());
        i.setIdentifier("name2");
        assertEquals("name2", i.getIdentifier());
        assertEquals(0, i.getWeight());
        i.setWeight(12);
        assertEquals(12, i.getWeight());
        
        Item ii = factory.createItem();
        assertNull(ii.getIdentifier());
        ii.setIdentifier("iname");
        assertEquals("iname", ii.getIdentifier());
        ii.setIdentifier("iname2");
        assertEquals("iname2", ii.getIdentifier());
        assertEquals(0, ii.getWeight());
        ii.setWeight(11);
        assertEquals(11, ii.getWeight());
        
        assertEquals("name2", i.getIdentifier());
        assertEquals(12, i.getWeight());
    }
    
    public void testAddReferenceAttribute() {
        Rack r = factory.createRack();
        Box b = factory.createBox();
        r.getBox().add(b);
        assertTrue(r.getBox().contains(b));
        assertTrue(r.getContent().contains(b));
        assertEquals(b.getContainer(), r);
        assertNull(r.getContainer());
        assertEquals(1, r.getContent().size());
        assertEquals(1, r.getBox().size());
        assertEquals(0, b.getContent().size());
        assertEquals(0, b.getItem().size());
    }
    
    public void testRemoveReferenceAttribute1() {
        Rack r = factory.createRack();
        Box b = factory.createBox();
        r.getBox().add(b);
        assertTrue(r.getBox().contains(b));
        r.getBox().remove(b);
        assertEquals(r.getBox().size(), 0);
        assertEquals(r.getContent().size(), 0);
        assertNull(b.getContainer());
    }
    
    public void testRemoveReferenceAttribute2() {
        Rack r = factory.createRack();
        Box b = factory.createBox();
        r.getBox().add(b);
        r.getContent().remove(b);
        assertEquals(r.getBox().size(), 0);
        assertEquals(r.getContent().size(), 0);
        assertNull(b.getContainer());
    }
    
    public void testEnumAttribute() {
        Item i = factory.createItem();
        assertEquals(VisibilityKind.PUBLIC, i.getVisibility());
        i.setVisibility(VisibilityKind.PRIVATE);
        assertEquals(VisibilityKind.PRIVATE, i.getVisibility());
    }
}
