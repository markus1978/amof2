package hub.sam.tools;

import cmof.Package;
import cmof.reflection.Extent;
import cmof.reflection.Object;
import hub.sam.mof.Repository;
import hub.sam.mof.Tools;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.merge.MergeContext;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.Xmi2Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Merge {

    public static void main(String[] args) throws Exception {
        Repository repository;
        Package m3;

        repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        m3 = (Package)m3Extent.query("Package:cmof");

        Extent umlExtent = repository.createExtent("uml test");
        Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
        xmiMap.put("Infrastructure.cmof", new FileInputStream(new File("resources/models/uml/Infrastructure.cmof.xml")));
        xmiMap.put("Superstructure.cmof", new FileInputStream(new File("resources/models/uml/Superstructure.cmof.xml")));
        xmiMap.put("CD.cmof", new FileInputStream(new File("resources/examples/uml/CD.cmof.xml")));
        /*
        xmiMap.put("L1.cmof", new FileInputStream(new File("resources/models/uml/L1.cmof.xml")));
        xmiMap.put("L2.cmof", new FileInputStream(new File("resources/models/uml/L2.cmof.xml")));
        xmiMap.put("L3.cmof", new FileInputStream(new File("resources/models/uml/L3.cmof.xml")));
        */
        Xmi2Reader.readMofXmi(xmiMap, umlExtent, m3, Xmi1Reader.XmiKind.mof);

        Tools.setOppositeValues(umlExtent);
        Tools.repairBadOMGUmlMultiplicities(umlExtent);
        repository.writeExtentToXmi("resources/examples/uml/CD.premerged.cmof.xml", m3, umlExtent);

        cmof.Package packageToMerge = (Package)umlExtent.query("Package:uml");
        if (packageToMerge == null) {
            throw new Exception("Package to merge not found");
        }
        MergeContext.mergePackages(packageToMerge, (cmof.cmofFactory)repository.createFactory(umlExtent, m3), null);
        Tools.removeFoldedImports(umlExtent);

        Collection<cmof.reflection.Object> toDelete = new Vector<Object>();
        for(cmof.reflection.Object obj: umlExtent.getObject()) {
            if (obj instanceof cmof.Package && !packageToMerge.equals(obj)) {
                toDelete.add(obj);
            }
        }

        for(cmof.reflection.Object obj: toDelete) {
            if (((ObjectImpl)obj).getClassInstance().isValid()) {
                obj.delete();
            }
        }
        
        repository.writeExtentToXmi("resources/examples/uml/CD.merged.cmof.xml", m3, umlExtent);
        repository.deleteExtent("uml test");
    }
}
