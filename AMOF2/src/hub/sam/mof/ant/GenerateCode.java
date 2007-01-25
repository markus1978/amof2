package hub.sam.mof.ant;

import cmof.reflection.Extent;
import cmof.*;
import cmof.Package;
import hub.sam.mof.Repository;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.codegeneration.CodeGenerationConfiguration;
import hub.sam.mof.mas.layers.M1SemanticModel;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractClusterableException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.File;
import java.util.Collection;
import java.util.Vector;

public class GenerateCode extends Task {

    private boolean unisys = false;
    private boolean ea = false;
    private boolean md = false;
    private boolean instances = false;
    private boolean library = false;
    private boolean force = false;
    private File libraryFile = null;
    private String staticModel = null;
    private File src = null;
    private File destdir = null;
    private boolean remote = true;
    private boolean ocl = false;

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
        if (library && libraryFile == null) {
            throw new BuildException("Parameter libraryFile is mandatory");
        }
        if (library && !libraryFile.isFile()) {
            throw new BuildException(libraryFile.toString() + " is not a file");
        }
        if (!src.isFile()) {
            throw new BuildException(src.toString() + " is not a file");
        }
        if (!destdir.isDirectory()) {
            throw new BuildException(destdir.toString() + " is not a directory");
        }

        CodeGenerationConfiguration.setActualConfig(new CodeGenerationConfiguration(isOcl(), false, isRemote()));

        // check if nessesary
        if ((src.lastModified() <= destdir.lastModified()) && (destdir.listFiles().length > 0)) {
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
            if (library) {
                System.out.println("Reading library.");
                repository.loadXmiIntoExtent(extent, cmof, libraryFile.toString());
            }

            System.out.println("Reading model.");
            if (!unisys && !ea && !md) {
                repository.loadXmiIntoExtent(extent, cmof, src.toString());
            } else if (unisys){
                repository.loadUnisysXmiIntoExtent(extent, cmof, src.toString());
            } else if (ea) {
                repository.loadEAXmiIntoExtent(extent, cmof, src.toString());
            } else if (md) {
                repository.loadMagicDrawXmiIntoExtent(extent, cmof, src.toString());
            }

            if (instances) {
                System.out.println("Creating semantic elements.");
                Collection<cmof.Package> packages = new Vector<Package>();
                for (Object o : extent.outermostComposites()) {
                    if (o instanceof cmof.Package) {
                        packages.add((cmof.Package)o);
                    }
                }
                cmofFactory factory = (cmofFactory)repository.createFactory(extent, cmof);
                new M1SemanticModel(factory).createImplicitElements(packages);
            }

            System.out.println("Generating code.");
            repository.generateCode(extent, destdir.toString(), false);
            if (staticModel != null) {
                System.out.println("Generating code for static model.");
                repository.generateStaticModel(extent, staticModel, destdir.toString());
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

    public void setUnisys(boolean unisys) {
        this.unisys = unisys;
    }

    public void setLibrary(boolean core) {
        this.library = core;
    }

    public void setLibraryFile(File file) {
        this.libraryFile = file;
    }

    public void setEa(boolean ea) {
        this.ea = ea;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public void setMd(boolean md) {
        this.md = md;
    }

    public void setInstances(boolean instances) {
        this.instances = instances;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public boolean isOcl() {
        return ocl;
    }

    public void setOcl(boolean ocl) {
        this.ocl = ocl;
    }

    public static void main(String[] args) {
        GenerateCode gc = new GenerateCode();
        gc.setSrc(new File("resources/models/sdl.mdxml"));
        gc.setDestdir(new File("generated-src"));
        gc.setMd(true);
        gc.setInstances(true);
        gc.setStaticModel("SDL.SdlModel");
        gc.setForce(true);
        gc.execute();
    }
}
