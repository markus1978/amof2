package hub.sam.mof.ant;

import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractClusterableException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;

public class UpgradeXmi extends Task {

    private String inKind = null;
    private String outKind = null;
    private File inFile = null;
    private File outFile = null;

    @Override
    public void execute() throws BuildException {        
        if (inKind == null) {
            throw new BuildException("Parameter inKind is mandatory");
        }
        if (outKind == null) {
            throw new BuildException("Parameter outKind is mandatory");
        }
        if (inFile == null) {
            throw new BuildException("Parameter inFile is mandatory");
        }
        if (outFile == null) {
            throw new BuildException("Parameter outFile is mandatory");
        }

        Xmi1Reader.XmiKind fromKind = null;
        Xmi1Reader.XmiKind toKind = null;
        for(Xmi1Reader.XmiKind kind: Xmi1Reader.XmiKind.class.getEnumConstants()) {
            if (inKind.equals(kind.toString())) {
                fromKind = kind;
            }
            if (outKind.equals(kind.toString())) {
                toKind = kind;
            }
        }
        if ((fromKind == null) || (toKind == null)) {
            throw new BuildException("Unknown xmi kind used");
        }

        if (inFile.lastModified() <= outFile.lastModified()) {
            return;
        }

        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        try {
            System.out.println("Initializing.");
            Repository repo = Repository.getLocalRepository();
            Extent m3 = repo.getExtent(Repository.CMOF_EXTENT_NAME);
            cmof.Package cmof = (Package)m3.query("Package:cmof");

            System.out.println("Reading model.");
            Extent m2 = repo.createExtent("m2");
            repo.loadXmiIntoExtent(m2, cmof, inFile.getCanonicalPath(), fromKind);

            System.out.println("Custom actions");
            custom(m2, cmof, repo);

            System.out.println("Writing model.");
            repo.writeExtentToXmi(outFile.getCanonicalPath(), cmof, m2, toKind);
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

    public void setInKind(String inKind) {
        this.inKind = inKind;
    }

    public void setOutKind(String outKind) {
        this.outKind = outKind;
    }

    public void setInFile(File inFile) {
        this.inFile = inFile;
    }

    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }

    protected void custom(Extent extent, cmof.Package m3Model, Repository repository) {
        // empty
    }

    public static void main(String[] args) {
        UpgradeXmi xmi = new UpgradeXmi();
        xmi.setInFile(new File(args[0]));
        xmi.setInKind(args[1]);
        xmi.setOutFile(new File(args[2]));
        xmi.setOutKind(args[3]);
        xmi.execute();
    }
}
