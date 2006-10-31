package Traffic;

import as.asFactory;
import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsBehavior;
import hub.sam.mof.as.AsImplementationsManager;
import hub.sam.mof.as.AsSemanticException;
import hub.sam.mof.as.layers.MultiLevelImplementationsManager;
import hub.sam.mof.as.parser.ActionSemanticsParser;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.ImplementationsManager;
import hub.sam.mof.reflection.ImplementationsManagerContainer;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.util.AbstractClusterableException;
import hub.sam.util.Clusterable;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Vector;

@SuppressWarnings({"OverlyCoupledClass"})
public class ASSimulatorWithDomainModel {

    @SuppressWarnings({"unchecked", "OverlyLongMethod", "OverlyCoupledMethod"})
    public void execute() throws Exception {
        System.out.println("initialize");
        Repository repo = Repository.getLocalRepository();
        Extent m2Extent = repo.createExtent("m2test");
        Extent m3Extent = repo.getExtent(Repository.CMOF_EXTENT_NAME);
        cmof.Package asPackage = (cmof.Package)m3Extent.query("Package:as");
        cmof.Package cmofPackage = (cmof.Package)m3Extent.query("Package:cmof");

        try {
            repo.loadXmiIntoExtent(m2Extent, cmofPackage, "traffic.xml");
        } catch (Exception e) {
            if (e instanceof Clusterable) {
                AbstractClusterableException.printReport((Clusterable)e, System.err);
                System.exit(1);
            } else {
                throw e;
            }
        }

        asFactory factory = (asFactory)repo.createFactory(m2Extent, asPackage);
        ActionSemanticsParser parser = new ActionSemanticsParser(new FileReader(new File("src/Traffic/SemanticsWithDomainModel.as")));
        parser.setFactory(factory);

        Collection<AsBehavior> behaviorDcls = new Vector<AsBehavior>();

        System.out.println("parse.");
        parser.behavior_declaration(behaviorDcls);
        for (AsSemanticException ex: parser.getErrors()) {
            System.err.println(ex.getMessage());
        }
        if (parser.getErrors().size() > 0) {
            System.exit(1);
        }

        System.out.println("semantic analysis.");
        System.out.println(m2Extent.query("Package:Traffic"));
        AsAnalysisEnvironment environment = new AsAnalysisEnvironment(m2Extent, repo);

        boolean exceptions = false;
        for (AsBehavior dcl: behaviorDcls) {
            try {
                dcl.staticSemantics(environment);
            } catch (AsSemanticException ex) {
                AbstractClusterableException.<AsSemanticException>printReport(ex, System.err);
                exceptions = true;
            }
        }
        if (exceptions) {
            System.exit(1);
        }

        Extent m1Extent = repo.createExtent("m1Test");
        Package m2Model = (Package)m2Extent.query("Package:Traffic");
        TrafficFactory trafficFactory = (TrafficFactory)repo.createFactory(m1Extent, m2Model);
        ImplementationsManagerContainer implementationsManagerContainer = new ImplementationsManagerContainer(
                new ImplementationsManager[] { new AsImplementationsManager(m1Extent, m2Extent, repo),
                                               new MultiLevelImplementationsManager(trafficFactory),
                                               new ImplementationsManagerImpl() }
        );
        ((ExtentImpl)m1Extent).setCustomImplementationsManager(implementationsManagerContainer);

        System.out.println("create and instantiate example model.");
        Crossroad crossroad = Simulator.createSampleModel(trafficFactory);

        //while (true) {
            crossroad.justDoIt();
        //}
    }


    public static void main(String[] args) throws Exception {
        new ASSimulatorWithDomainModel().execute();
    }
}
