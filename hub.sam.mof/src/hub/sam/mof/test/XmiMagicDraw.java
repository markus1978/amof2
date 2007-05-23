package hub.sam.mof.test;

import hub.sam.mof.xmi.XmiImportExport;
import cmof.reflection.Extent;

public class XmiMagicDraw extends AbstractRepository {

    public XmiMagicDraw() {
        super("XMI MagicDraw tests");
    }
    
    private Extent firstImport = null;
  
    public void testImport() throws Exception {
        firstImport = repository.createExtent("firstImport");
        try {
        	repository.loadMagicDrawXmiIntoExtent(firstImport, m3, "resources/models/test/qualifier-test.mdxml");
        } catch (Exception e) {        	
        	e.printStackTrace();
        	assertTrue(false);
        }
    }
       
    public void testExport() throws Exception {        
        testImport();
        try {
        	repository.writeExtentToMagicDrawXmi("resources/models/work/out.xml", m3, firstImport);
        } catch (Exception e) {        	
        	e.printStackTrace();
        	assertTrue(false);
        }
    }
    
    public void testSingleDiagramInfo() throws Exception {
    	firstImport = repository.createExtent("firstImport");
    	XmiImportExport info = repository.loadMagicDrawXmiIntoExtent(firstImport, m3, "resources/models/test/qualifier-test.mdxml");
    	repository.writeExtentToMagicDrawXmi("resources/models/work/out.xml", m3, firstImport, info);
    }
    
    public void testDoubleDiagramInfo() throws Exception {
    	firstImport = repository.createExtent("firstImport");
    	XmiImportExport info = repository.loadMagicDrawXmiIntoExtent(firstImport, m3, "resources/models/test/qualifier-test.mdxml");
    	repository.writeExtentToMagicDrawXmi("resources/models/work/out.xml", m3, firstImport, info);
    	repository.writeExtentToMagicDrawXmi("resources/models/work/out.xml", m3, firstImport, info);
    }
}