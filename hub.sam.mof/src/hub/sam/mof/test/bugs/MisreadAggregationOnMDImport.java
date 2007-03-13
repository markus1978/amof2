package hub.sam.mof.test.bugs;

import java.io.IOException;

import org.jdom.JDOMException;

import junit.framework.TestCase;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
import cmof.Package;
import cmof.Property;
import cmof.reflection.Extent;

public class MisreadAggregationOnMDImport extends TestCase {
    
    private Repository repository;
    private Extent m3Extent;
    private Package cmofPackage;

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
	
	public void test() {
        repository = Repository.getLocalRepository();
        m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        cmofPackage = (Package) m3Extent.query("Package:cmof");

        String xmiFile = "resources/models/test/mas.mdxml";
        String clonedXmiFile = xmiFile + "_cloned.mdxml";
        
        try {
            String extentName = "CompositeViolationTest";
            Extent modelExtent = repository.createExtent(extentName);
            repository.loadXmiIntoExtent(modelExtent, cmofPackage, xmiFile, XmiKind.md);
            assertTrue(myTestComposite(modelExtent));
            repository.writeExtentToXmi(clonedXmiFile, cmofPackage, modelExtent, XmiKind.md);
            assertTrue(myTestComposite(modelExtent));
            repository.deleteExtent(extentName);
            
            modelExtent = repository.createExtent(extentName);
            repository.loadXmiIntoExtent(modelExtent, cmofPackage, clonedXmiFile, XmiKind.md);
            assertTrue(myTestComposite(modelExtent));
        }
        catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (XmiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MetaModelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
    
    private boolean myTestComposite(Extent extent) {
        return ((Property) extent.query("Package:petrinets/Class:Transition/Property:outputPlaces")).getOpposite().isComposite() == false;
    }
    
}
