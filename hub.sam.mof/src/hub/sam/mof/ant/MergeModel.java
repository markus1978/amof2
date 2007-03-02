package hub.sam.mof.ant;

import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.Tools;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.merge.MergeContext;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.Xmi2Reader;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractClusterableException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class MergeModel extends Task {
    private List<XmiFile> xmifiles = new Vector<XmiFile>();
    private File dest = null;
    private String packageToMerge = null;

    @Override
    public void execute() throws BuildException {
        if (dest == null || dest.isDirectory()) {
            throw new BuildException("Parameter dest is mandantory and must not be a directory.");
        }
        if (packageToMerge == null) {
            throw new BuildException("Parameter packageToMerge is mandantory");
        }

        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        try {
            boolean old = true;
            Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
            for (XmiFile xmifile: xmifiles) {
                String id = xmifile.getKey();
                File file = xmifile.getFile();
                if (file.lastModified() > dest.lastModified()) {
                    old = false;
                }
                xmiMap.put(id, new FileInputStream(file));
            }

            if (old) {
                return;
            }

            System.out.println("Initializing.");
            Repository repo = Repository.getLocalRepository();
            Extent m3 = repo.getExtent(Repository.CMOF_EXTENT_NAME);
            cmof.Package cmof = (cmof.Package)m3.query("Package:cmof");

            System.out.println("Reading model.");
            Extent m2 = repo.createExtent("m2");
            Xmi2Reader.readMofXmi(xmiMap, m2, cmof, Xmi1Reader.XmiKind.mof);


            Tools.setOppositeValues(m2);
            Tools.repairBadOMGUmlMultiplicities(m2);

            cmof.Package pkg = (cmof.Package)m2.query(packageToMerge);
            if (pkg == null) {
                throw new BuildException("Package to merge not found");
            }
            MergeContext.mergePackages(pkg, (cmof.cmofFactory)repo.createFactory(m2, cmof), null);
            Tools.removeFoldedImports(m2);

            Collection<cmof.reflection.Object> toDelete = new Vector<cmof.reflection.Object>();
            for(cmof.reflection.Object obj: m2.getObject()) {
                if (obj instanceof cmof.Package && !pkg.equals(obj)) {
                    toDelete.add(obj);
                }
            }

            for(cmof.reflection.Object obj: toDelete) {
            	if (((ObjectImpl)obj).getClassInstance() != null &&
            			((ObjectImpl)obj).getClassInstance().isValid()) {
                    obj.delete();
                }
            }

            repo.writeExtentToXmi(dest.getPath(), cmof, m2);
        } catch (XmiException xe) {
            AbstractClusterableException.printReport(xe, System.err);
            throw new BuildException(xe);
        } catch (GenerationException ge) {
            AbstractClusterableException.printReport(ge, System.err);
            throw new BuildException(ge);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new BuildException(e);
        }
    }

    public void setPackageToMerge(String packageToMerge) {
        this.packageToMerge = packageToMerge;
    }

    public void setDest(File dest) {
        this.dest = dest;
    }

    public void addXmifile(XmiFile xmiFile) {
        xmifiles.add(xmiFile);
    }
}
