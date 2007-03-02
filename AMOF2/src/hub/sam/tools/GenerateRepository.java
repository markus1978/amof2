package hub.sam.tools;

import cmof.reflection.Extent;
import cmof.*;
import cmof.Package;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.XmiException;
import org.jdom.JDOMException;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public class GenerateRepository {

    public static void main(String[] args) throws IOException, MetaModelException, XmiException, JDOMException {
        if (args.length > 4) {
            error(args);
        }

        String xmiKindStr = args[0];
        Xmi1Reader.XmiKind xmiKind = null;
        for(Xmi1Reader.XmiKind kind: Xmi1Reader.XmiKind.class.getEnumConstants()) {
            if (xmiKindStr.equals(kind.toString())) {
                xmiKind = kind;
            }
        }
        if (xmiKind == null) {
            error(args);
        }

        Repository repo = Repository.getLocalRepository();
        Extent m3 = repo.getExtent(Repository.CMOF_EXTENT_NAME);
        cmof.Package cmof = (cmof.Package)m3.query("Package:cmof");

        Extent m2 = repo.createExtent("m2");

        if (args.length == 4) {
            System.out.println("Reading library.");
             repo.loadXmiIntoExtent(m2, cmof, args[3]);
        }

        System.out.println("Reading xmi.");
        repo.loadXmiIntoExtent(m2, cmof, args[1], xmiKind);

        // if instces
        System.out.println("Creating semantic elements.");
        Collection<cmof.Package> packages = new Vector<Package>();
        for (Object o : m2.outermostComposites()) {
            if (o instanceof cmof.Package) {
                packages.add((cmof.Package)o);
            }
        }
        cmofFactory factory = (cmofFactory)repo.createFactory(m2, cmof);
        new M1SemanticModel(factory).createImplicitElements(packages);

        System.out.println("Generating code.");
        repo.generateCode(m2, args[2]);
    }

    private static void error(String[] args) {
        System.err.println("wrong args");
        System.err.println(args);
        System.exit(1);
    }
}
