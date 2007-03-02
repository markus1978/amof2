package hub.sam.tools.tests;

import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.Xmi2Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ReadMultipleFileXmi {

    public static void main(String[] args) {
        Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
        try {
            for(String fileName: args) {
                File file = new File(fileName);
                String key = file.getName();
                xmiMap.put(key.substring(0, key.lastIndexOf(".")), new FileInputStream(file));
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            System.exit(1);
        }

        Repository repo = Repository.getLocalRepository();
        Extent extent = repo.createExtent("work");
        cmof.Package m2 = (cmof.Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
        try {
            Xmi2Reader.readMofXmi(xmiMap, extent, m2, Xmi1Reader.XmiKind.mof);
            repo.writeExtentToXmi("resources/models/work/a.xml", m2, extent);
        } catch(Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }
}
