package hub.sam.mof.test;

import cmof.reflection.Extent;
import warehouse.Box;
import warehouse.Item;
import warehouse.WarehouseModel;
import warehouse.warehouseFactory;
import warehouse.ItemValue;
import warehouse.BoxValue;
import hub.sam.mof.jocl.standardlib.OclInteger;

public class OclTest extends AbstractRepository {

    public OclTest() {
        super("jocl test");
    }

    warehouse.warehouseFactory factory = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = WarehouseModel.createModel();
        factory = (warehouseFactory)repository.createFactory(m2Extent, (cmof.Package)m2Extent.query("Package:warehouse"));
    }

    public void testExpression1() {
        // create a text model
        Item i = factory.createItem();
        Item ii = factory.createItem();
        i.setWeight(1);
        ii.setWeight(2);
        Box b = factory.createBox();
        b.getItem().add(i);
        b.getItem().add(ii);

        // create some OCL values
        BoxValue self = new BoxValue(b);
        ItemValue n = new ItemValue(null);

        // test some OCL expressions
        assertEquals(Boolean.TRUE, new ItemValue(i).getWeight().oclEquals(new OclInteger(1)).javaValue());
        assertEquals(Boolean.FALSE, new ItemValue(ii).getWeight().oclEquals(new OclInteger(1)).javaValue());
        assertFalse(self.getItem().forAll(n, n.getWeight().oclEquals(new OclInteger(1))).javaValue());
        assertTrue(self.getItem().exists(n, n.getWeight().oclEquals(new OclInteger(1))).javaValue());
    }
}
