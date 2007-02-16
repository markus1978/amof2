package hub.sam.mof.merge;

import cmof.Package;
import cmof.Property;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.Tools;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.Xmi2Reader;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@SuppressWarnings({"ClassWithTooManyMethods"})
public class MergeTest extends TestCase {
    private Repository repository;
    private Package m3;
    private Extent testModel;
    private cmofFactory factory;
    private Compare compare;

    public MergeTest() throws Exception {
        super("Merge tests");
    }

    @Override
    protected void setUp() throws Exception {
    	repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        m3 = (Package)m3Extent.query("Package:cmof");

        testModel = repository.createExtent("testModel");
        factory = (cmofFactory)repository.createFactory(testModel, m3);
        repository.loadMagicDrawXmiIntoExtent(testModel, m3, "resources/models/test/merge-tests.mdxml");

        Collection<Property> doNotCompare = new Vector<Property>();
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:NamedElement/Property:name"));
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:NamedElement/Property:namespace"));
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:Element/Property:owner"));
        doNotCompare.add((Property)m3Extent.query("Package:cmof/Class:Type/Property:package"));
        compare = new Compare(doNotCompare);
    }

    private void post() throws Exception {
        repository.writeExtentToXmi("resources/models/work/mergetest.xml", m3, testModel);
    }

    private void aTest(int number) throws Exception {
        Package A = (Package)testModel.query("Package:MergeTest" + number + "/Package:A");

        MergeContext.mergePackages(A, factory, null);

        Package Result = (Package)testModel.query("Package:MergeTest" + number + "/Package:Result");
        Difference difference = compare.compare(Result, A);
        if (difference != null) {
            System.err.println("Merge result does not match expected results: " + difference.getMessage());
        }
        assertNull(difference);
    }

    public void atestUMLL0() throws Exception {
        Extent umlExtent = repository.createExtent("uml test");
        Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
        xmiMap.put("Infrastructure.cmof", new FileInputStream(new File("resources/models/uml/Infrastructure.cmof.xml")));
        xmiMap.put("L0.cmof", new FileInputStream(new File("resources/models/uml/L0.cmof.xml")));
        Xmi2Reader.readMofXmi(xmiMap, umlExtent, m3, Xmi1Reader.XmiKind.mof);
        Tools.setOppositeValues(umlExtent);

        MergeContext.mergePackages((cmof.Package)umlExtent.query("Package:L0"),
                (cmof.cmofFactory)repository.createFactory(umlExtent, m3), null);

        Tools.showPropertiesWithoutType(umlExtent);

        repository.writeExtentToXmi("resources/models/work/UML0MergeTest.xml", m3, umlExtent);
        repository.deleteExtent("uml test");
    }

    public void atestUMLL1() throws Exception {
        Extent umlExtent = repository.createExtent("uml test");
        Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
        xmiMap.put("Infrastructure.cmof", new FileInputStream(new File("resources/models/uml/Infrastructure.cmof.xml")));
        xmiMap.put("Superstructure.cmof", new FileInputStream(new File("resources/models/uml/Superstructure.cmof.xml")));
        xmiMap.put("L1.cmof", new FileInputStream(new File("resources/models/uml/L1.cmof.xml")));
        Xmi2Reader.readMofXmi(xmiMap, umlExtent, m3, Xmi1Reader.XmiKind.mof);

        Tools.setOppositeValues(umlExtent);
        Package l1Package = (Package)umlExtent.query("Package:L1");
        MergeContext.mergePackages(l1Package, (cmof.cmofFactory)repository.createFactory(umlExtent, m3), null);
        Tools.removeFoldedImports(umlExtent);

        Collection<cmof.reflection.Object> toDelete = new Vector<cmof.reflection.Object>();
        for(cmof.reflection.Object obj: umlExtent.getObject()) {
            if (obj instanceof cmof.Package && !l1Package.equals(obj)) {
                toDelete.add(obj);
            }
        }
        for(cmof.reflection.Object obj: toDelete) {
            if (((ObjectImpl)obj).getClassInstance().isValid()) {
                obj.delete();
            }
        }

        Tools.showPropertiesWithoutType(umlExtent);

        repository.writeExtentToXmi("resources/models/work/UML1MergeTest.xml", m3, umlExtent);
        repository.deleteExtent("uml test");
    }

    public void atestUMLL2() throws Exception {
        Extent umlExtent = repository.createExtent("uml test");
        Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
        xmiMap.put("Infrastructure.cmof", new FileInputStream(new File("resources/models/uml/Infrastructure.cmof.xml")));
        xmiMap.put("Superstructure.cmof", new FileInputStream(new File("resources/models/uml/Superstructure.cmof.xml")));
        xmiMap.put("L1.cmof", new FileInputStream(new File("resources/models/uml/L1.cmof.xml")));
        xmiMap.put("L2.cmof", new FileInputStream(new File("resources/models/uml/L2.cmof.xml")));
        Xmi2Reader.readMofXmi(xmiMap, umlExtent, m3, Xmi1Reader.XmiKind.mof);

        Tools.setOppositeValues(umlExtent);
        Package l1Package = (Package)umlExtent.query("Package:L2");
        MergeContext.mergePackages(l1Package, (cmof.cmofFactory)repository.createFactory(umlExtent, m3), null);
        Tools.removeFoldedImports(umlExtent);

        Collection<cmof.reflection.Object> toDelete = new Vector<cmof.reflection.Object>();
        for(cmof.reflection.Object obj: umlExtent.getObject()) {
            if (obj instanceof cmof.Package && !l1Package.equals(obj)) {
                toDelete.add(obj);
            }
        }
        for(cmof.reflection.Object obj: toDelete) {
            if (((ObjectImpl)obj).getClassInstance().isValid()) {
                obj.delete();
            }
        }

        Tools.showPropertiesWithoutType(umlExtent);

        repository.writeExtentToXmi("resources/models/work/UML2MergeTest.xml", m3, umlExtent);
        repository.deleteExtent("uml test");
    }

    public void atestUMLL3() throws Exception {
        Extent umlExtent = repository.createExtent("uml test");
        Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
        xmiMap.put("Infrastructure.cmof", new FileInputStream(new File("resources/models/uml/Infrastructure.cmof.xml")));
        xmiMap.put("Superstructure.cmof", new FileInputStream(new File("resources/models/uml/Superstructure.cmof.xml")));
        xmiMap.put("L1.cmof", new FileInputStream(new File("resources/models/uml/L1.cmof.xml")));
        xmiMap.put("L2.cmof", new FileInputStream(new File("resources/models/uml/L2.cmof.xml")));
        xmiMap.put("L3.cmof", new FileInputStream(new File("resources/models/uml/L3.cmof.xml")));
        Xmi2Reader.readMofXmi(xmiMap, umlExtent, m3, Xmi1Reader.XmiKind.mof);

        Tools.setOppositeValues(umlExtent);
        Package l1Package = (Package)umlExtent.query("Package:L3");
        MergeContext.mergePackages(l1Package, (cmof.cmofFactory)repository.createFactory(umlExtent, m3), null);
        Tools.removeFoldedImports(umlExtent);

        Collection<cmof.reflection.Object> toDelete = new Vector<cmof.reflection.Object>();
        for(cmof.reflection.Object obj: umlExtent.getObject()) {
            if (obj instanceof cmof.Package && !l1Package.equals(obj)) {
                toDelete.add(obj);
            }
        }
        for(cmof.reflection.Object obj: toDelete) {
            if (((ObjectImpl)obj).getClassInstance() != null && ((ObjectImpl)obj).getClassInstance().isValid()) {
                obj.delete();
            }
        }

        Tools.showPropertiesWithoutType(umlExtent);

        repository.writeExtentToXmi("resources/models/work/UML3MergeTest.xml", m3, umlExtent);
        repository.deleteExtent("uml test");
    }

    public void test1() throws Exception {
        aTest(1);
    }

    public void test2() throws Exception {
        aTest(2);
    }

    public void test3() throws Exception {
        aTest(3);
    }

    public void test4() throws Exception {
        aTest(4);
    }

    public void test5() throws Exception {
        aTest(5);
    }

    public void test6() throws Exception {
        aTest(6);
    }

    public void test7() throws Exception {
        aTest(7);
    }

    public void test8() throws Exception {
        aTest(8);
    }

    public void test9() throws Exception {
        aTest(9);
    }

    public void test10() throws Exception {
        aTest(10);
    }

    public void test11() throws Exception {
        aTest(11);
    }

    public void test12() throws Exception {
        aTest(12);
    }

    public void test13() throws Exception {
        aTest(13);
    }

    public void test14() throws Exception {
        aTest(14);
    }

    public void test15() throws Exception {
        aTest(15);
    }

    @Override
    protected void tearDown() throws Exception {
        post();
    }

    public static void main(String[] args) throws Exception {
        MergeTest test = new MergeTest();
        test.setUp();
        //test.testUMLL1();
        test.tearDown();
    }
}
