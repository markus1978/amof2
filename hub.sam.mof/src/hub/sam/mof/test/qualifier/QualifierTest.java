package hub.sam.mof.test.qualifier;

import cmof.Property;
import cmof.reflection.Extent;
import hub.sam.mof.test.AbstractRepository;
import qualifier.QualifierModel;
import qualifier.qualifierFactory;
import qualifier.Source;
import qualifier.QualifierType;
import qualifier.Target;
import qualifier.ASource;
import qualifier.ATarget;
import qualifier.BTarget;
import qualifier.AQualifierType;
import qualifier.BQualifierType;

public class QualifierTest extends AbstractRepository {

    public QualifierTest() {
        super("qualifier tests");
    }

    private qualifierFactory qualifierFactory = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = QualifierModel.createModel();
        Extent m1Extent = repository.createExtent("m1Extent");
        qualifierFactory = (qualifierFactory)repository.createFactory(m1Extent,
                (cmof.Package)m2Extent.query("Package:qualifier"));
    }

    public void testImport() throws Exception {
        Extent m2Extent = repository.createExtent("m2Extent");
        repository.loadMagicDrawXmiIntoExtent(m2Extent, m3, "resources/models/test/qualifier-test.mdxml");
        Property superAttr = (Property)m2Extent.query("Package:qualifier/Class:Source/Property:superAttr");
        assertNotNull(superAttr.getQualifier());
        assertEquals(superAttr.getQualifier().getName(), "access");
    }

    public void testNormalAttr() throws Exception {
        Source source = qualifierFactory.createSource(); source.setName("source");
        QualifierType qualifier1 = qualifierFactory.createQualifierType(); qualifier1.setName("qualifier1");
        QualifierType qualifier2 = qualifierFactory.createQualifierType(); qualifier2.setName("qualifier2");
        Target target1 = qualifierFactory.createTarget(); target1.setName("target1");
        Target target2 = qualifierFactory.createTarget(); target2.setName("target2");
        source.setSuperAttr(qualifier1, target1);
        source.setSuperAttr(qualifier2, target2);

        assertEquals(target1, source.getSuperAttr(qualifier1));
        assertEquals(target2, source.getSuperAttr(qualifier2));
        assertEquals(source, target1.getSource());
        assertEquals(source, target2.getSource());
    }

    public void testSubsettedAttr() throws Exception {
        ASource asource = qualifierFactory.createASource(); asource.setName("source");
        AQualifierType qualifier1 = qualifierFactory.createAQualifierType(); qualifier1.setName("qualifier1");
        AQualifierType qualifier2 = qualifierFactory.createAQualifierType(); qualifier2.setName("qualifier2");
        BQualifierType qualifier3 = qualifierFactory.createBQualifierType(); qualifier3.setName("qualifier3");

        ATarget targetA1 = qualifierFactory.createATarget(); targetA1.setName("targetA1");
        ATarget targetA2 = qualifierFactory.createATarget(); targetA2.setName("targetA2");
        BTarget targetB = qualifierFactory.createBTarget(); targetB.setName("targetB");
        asource.setSuperAttrA(qualifier1, targetA1);
        asource.setSuperAttrA(qualifier2, targetA2);
        asource.setSuperAttrB(qualifier3, targetB);

        assertEquals(targetA1, asource.getSuperAttrA(qualifier1));
        assertEquals(targetA2, asource.getSuperAttrA(qualifier2));
        assertEquals(targetB, asource.getSuperAttrB(qualifier3));

        assertEquals(targetA1, asource.getSuperAttr(qualifier1));
        assertEquals(targetA2, asource.getSuperAttr(qualifier2));
        assertEquals(targetB, asource.getSuperAttr(qualifier3));
    }

}
