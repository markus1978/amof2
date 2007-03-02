package hub.sam.mof.test;

import cmof.reflection.Extent;
import warehouse.WarehouseModel;
import warehouse.warehouseFactory;
import warehouse.Item;

public class JavaTypeTest extends AbstractRepository{

    public JavaTypeTest() {
        super("java type tests");
    }

    private warehouse.warehouseFactory factory = null;
    private cmof.Package warehouseModel = null;
    private Extent m1Extent = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = WarehouseModel.createModel();
        warehouseModel = (cmof.Package)m2Extent.query("Package:warehouse");
        m1Extent = getRepository().createExtent("m1");
        factory = (warehouseFactory)repository.createFactory(m1Extent, warehouseModel);
    }


    public void testAttribute() {
        Item i = factory.createItem();
        TestClass testClass = new TestClass(2, 3);
        i.setJavaTypeTest(testClass);
        i.getJavaTypeTestList().add(testClass);
        assertEquals(2, i.getJavaTypeTest().getX());
        assertEquals(3, i.getJavaTypeTest().getY());
        assertEquals(2, i.getJavaTypeTestList().iterator().next().getX());
    }

    public void testXMI() throws Exception {
        Item i = factory.createItem();
        TestClass testClass = new TestClass(2, 3);
        i.setJavaTypeTest(testClass);
        i.getJavaTypeTestList().add(testClass);
        i.getJavaTypeTestList().add(new TestClass(3,4));
        getRepository().writeExtentToXmi("resources/models/test/TmpTestModel.xml", warehouseModel, m1Extent);
        Extent m1TestExtent = getRepository().createExtent("m1Test");
        getRepository().loadXmiIntoExtent(m1TestExtent, warehouseModel, "resources/models/test/TmpTestModel.xml");
        i = (Item)m1TestExtent.getObject().iterator().next();
        assertEquals(2, i.getJavaTypeTest().getX());
        assertEquals(3, i.getJavaTypeTest().getY());
        assertEquals(2, i.getJavaTypeTestList().iterator().next().getX());
    }

}
