package Java;

import hub.sam.mof.Repository;
import hub.sam.mof.as.layers.MultiLevelImplementationsManager;
import hub.sam.mof.reflection.ExtentImpl;
import cmof.reflection.Extent;
import junit.framework.TestCase;

public class Main extends TestCase  {
    public static void main(String[] args) throws Exception {
        Repository repo = Repository.getLocalRepository();
        Extent javaM2 = Java.JavaModel.createModel();
        Extent javaM1M0 = repo.createExtent("extent");
        cmof.Package javaPackage = (cmof.Package)javaM2.query("Package:Java");
        ((ExtentImpl)javaM1M0).setCustomImplementationsManager(new MultiLevelImplementationsManager(
                repo.createFactory(javaM1M0, javaPackage)));

        repo.loadXmiIntoExtent(javaM1M0, javaPackage, "test.xml");
        //repo.writeExtentToXmi("test-out.xml", javaPackage, javaM1M0);
        ((Java.UmlClass)javaM1M0.query("Class:c1")).runMain();
    }

    public Main() {
        super("The Java Example");
    }

    public void test() {
        try {
            main(new String[]{"test.xml"});
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assertTrue(false);
        }
    }
}
