package hub.sam.mof.ant;

import cmof.reflection.Extent;
import cmof.Package;
import cmof.cmofFactory;
import cmof.Tag;
import hub.sam.mof.Repository;
import hub.sam.mof.merge.MergeContext;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.XmiException;
import hub.sam.mof.xmi.XmiToCMOF;
import hub.sam.util.AbstractClusterableException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.util.Collection;
import java.util.Vector;

public class PrepareLanguageModel extends Task {

    private String staticModel = null;
    private String rootPackage = null;
    private boolean force = false;
    private File src = null;
    private File destdir = null;
    private File dest = null;

    @SuppressWarnings({"OverlyLongMethod"})
    @Override
    public void execute() throws BuildException {
        // check all parameters
        if (src == null) {
            throw new BuildException("Parameter src is mandatory");
        }
        if (destdir == null) {
            throw new BuildException("Parameter destdir is mandatory");
        }
        if (!src.isFile()) {
            throw new BuildException(src.toString() + " is not a file");
        }
        if (!destdir.isDirectory()) {
            throw new BuildException(destdir.toString() + " is not a directory");
        }
        if (dest != null && dest.isDirectory()) {
            throw new BuildException(dest.toString() + " is not a file");
        }

        // check if nessesary
        if ((src.lastModified() <= destdir.lastModified()) && (destdir.listFiles().length > 0) && (dest == null || dest.lastModified() > 0)) {
            if (!force) {
                return;
            }
        } else {
            destdir.setLastModified(src.lastModified());
        }

        // dont get confused by the ant class loader
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());

        // do it
        try {
            System.out.println("Initializing.");
            Repository repository = Repository.getLocalRepository();
            cmof.Package cmof = (cmof.Package)repository.getExtent(Repository.CMOF_EXTENT_NAME).
                    query("Package:cmof");

            Extent extent = repository.createExtent("extent");

            System.out.println("Reading model.");
            Xmi1Reader.XmiKind kind = Xmi1Reader.XmiKind.mof;
            if (src.getName().lastIndexOf(".") > 0) {
                String extension = src.getName().substring(src.getName().lastIndexOf(".") + 1, src.getName().length());
                if (extension.equals("mdxml")) {
                    kind = Xmi1Reader.XmiKind.md;
                }
            }
            repository.loadXmiIntoExtent(extent, cmof, src.toString(), kind);

            cmofFactory factory = (cmofFactory)repository.createFactory(extent, cmof);
            if (rootPackage != null) {
                System.out.println("Merging language model.");
                cmof.Package rootPackage;
                try {
                    rootPackage = (cmof.Package)extent.query("Package:" + this.rootPackage);
                } catch (Throwable e) {
                    throw new BuildException("Cannot resolve root package " + this.rootPackage);
                }
                MergeContext.mergePackages(rootPackage, factory, null);

                Tag nsNamespace = factory.createTag();
                nsNamespace.setValue(rootPackage.getName());
                nsNamespace.setName(XmiToCMOF.NsPrefixTagName);
                rootPackage.getTag().add(nsNamespace);
            }
            System.out.println("Creating semantic elements.");
            Collection<cmof.Package> packages = new Vector<Package>();
            for (Object o : extent.outermostComposites()) {
                if (o instanceof cmof.Package) {
                    packages.add((cmof.Package)o);
                }
            }
            new M1SemanticModel(factory).createImplicitElements(packages);

            System.out.println("Generating code.");
            repository.generateCode(extent, destdir.toString(), false);
            if (staticModel != null) {
                System.out.println("Generating code for static model.");
                repository.generateStaticModel(extent, staticModel, destdir.toString());
            }

            if (dest != null) {
                System.out.println("Exporting XMI.");
                repository.writeExtentToXmi(dest.getAbsolutePath(), cmof, extent);
            }
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

    public void setDestdir(File destdir) {
        this.destdir = destdir;
    }

    public void setSrc(File src) {
        this.src = src;
    }

    public void setStaticModel(String staticModel) {
        this.staticModel = staticModel;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public void setDest(File dest) {
        this.dest = dest;
    }

    public static void main(String[] args) {
        PrepareLanguageModel task = new PrepareLanguageModel();
        task.setSrc(new File(args[0]));
        task.setDestdir(new File(args[1]));
        task.setRootPackage(args[2]);
        task.execute();
    }
}
