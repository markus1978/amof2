package hub.sam.mof.test;

import cmof.reflection.Extent;
import hub.sam.mof.domainmodels.ProxyImplementationsManager;
import hub.sam.mof.domainmodels.ProxyInstanceModel;
import hub.sam.mof.domainmodels.ProxyModelContext;
import hub.sam.mof.reflection.ExtentImpl;
import soloDomainModelSolotest.soloDomainModelSolotestFactory;
import soloDomainModelSolotest.SoloTestClassClass;
import soloDomainModelSolotest.SoloTestClass;

import java.util.Arrays;

public class DomainModelsTest extends AbstractRepository {
    public DomainModelsTest() {
        super("Domain Models");
    }

    private soloDomainModelSolotestFactory factory = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = repository.createExtent("m2");
        repository.loadXmiIntoExtent(m2Extent, m3, "resources/models/work/solo-domain-model-test.xml");
        cmof.Package m2 = (cmof.Package)m2Extent.query("Package:soloDomainModelSolotest");

        Extent m1Extent = repository.createExtent("m1");
        ProxyModelContext context = new ProxyModelContext(
                Arrays.asList(new cmof.Package[]{m2}), repository, m1Extent);
        ((ExtentImpl)m1Extent).setCustomImplementationsManager(new ProxyImplementationsManager(context));
        ((ProxyInstanceModel)((ExtentImpl)m1Extent).getModel()).setContext(context);
        factory = (soloDomainModelSolotestFactory)repository.createFactory(m1Extent, m2);
    }

    public void testClassProxy() throws Exception {
        SoloTestClassClass classClass = factory.createSoloTestClassClass();
        assertTrue(classClass != null);
        assertTrue(classClass.equals(factory.createSoloTestClassClass()));
    }

    public void testFactoryMethod() throws Exception {
        SoloTestClassClass classClass = factory.createSoloTestClassClass();
        assertTrue(classClass.factoryMethod() != null);
        assertTrue(classClass.factoryMethod().getParameter().equals("HelloWorld"));
    }

    public void testFactoryMethodWithParameter() throws Exception {
        SoloTestClassClass classClass = factory.createSoloTestClassClass();
        assertTrue(classClass.factoryMethod("a") != null);
        assertTrue(classClass.factoryMethod("a").getParameter().equals("a"));
    }

    public void testConstructor() throws Exception {
        SoloTestClassClass classClass = factory.createSoloTestClassClass();
        assertTrue(classClass.SoloTestClass() != null);
        assertTrue(classClass.SoloTestClass().getParameter().equals("HelloWorld"));
    }

    public void testProperty() throws Exception {
        SoloTestClassClass classClass = factory.createSoloTestClassClass();
        SoloTestClass classInstance = classClass.factoryMethod();
        classInstance.setParameter("a");
        assertTrue(classInstance.getParameter().equals("a"));
    }

    public void testOperation() throws Exception {
        SoloTestClassClass classClass = factory.createSoloTestClassClass();
        SoloTestClass classInstance = classClass.factoryMethod();
        assertTrue(factory.createSoloTestClassClass().factoryMethod().objectValue(classInstance).equals(classInstance));
    }
}
