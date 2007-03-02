package hub.sam.mof.test.as;

import as.asFactory;
import cmof.Package;
import cmof.PrimitiveType;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsBehavior;
import hub.sam.mof.as.AsImplementationsManager;
import hub.sam.mof.as.AsSemanticException;
import hub.sam.mof.as.parser.ActionSemanticsParser;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.util.AbstractClusterableException;
import hub.sam.util.Clusterable;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Vector;

public class AsTest extends TestCase {

    @SuppressWarnings("unchecked")
    public void exectute() throws Exception {
        System.out.println("initialize");
        Repository repo = Repository.getLocalRepository();
        Extent m2Extent = repo.createExtent("m2test");
        Extent m3Extent = repo.getExtent(Repository.CMOF_EXTENT_NAME);
        Package asPackage = (cmof.Package)m3Extent.query("Package:as");
        Package cmofPackage = (cmof.Package)m3Extent.query("Package:cmof");

        try {
            repo.loadXmiIntoExtent(m2Extent, cmofPackage, "../../models/user/core.xml");
            repo.loadEAXmiIntoExtent(m2Extent, cmofPackage, "automaton-ea.xml");
            Repository.booleanType = (PrimitiveType)m2Extent.query("Package:core/Package:primitivetypes/PrimitiveType:Boolean");
        } catch (Exception e) {
            if (e instanceof Clusterable) {
                AbstractClusterableException.printReport((Clusterable)e, System.err);
                System.exit(1);
            } else {
                throw e;
            }
        }

        asFactory factory = (asFactory)repo.createFactory(m2Extent, asPackage);
        ActionSemanticsParser parser = new ActionSemanticsParser(new FileReader(new File("src/hub/sam/mof/test/as/Semantics.as")));
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
        ((ExtentImpl)m1Extent).setCustomImplementationsManager(new AsImplementationsManager(m1Extent, m2Extent, repo));

        java.lang.System.out.println("load example automaton.");
        Package m2SyntaxModel = (Package)m2Extent.query("Package:StateAutomaton/Package:Syntax");
        cmofFactory extensionFac = (cmofFactory)repo.createFactory(m2Extent, (Package)m3Extent.query("Package:cmof"));
        Tag nsPrefix = extensionFac.createTag();
        nsPrefix.setName("org.omg.xmi.nsPrefix");
        nsPrefix.setValue("automaton");
        nsPrefix.getElement().add(m2SyntaxModel);
        m2SyntaxModel.getTag().add(nsPrefix);
        Package m2SemanticsModel = (Package)m2Extent.query("Package:StateAutomaton/Package:Semantics");

        java.lang.System.out.println("create and instantiate example automaton.");
        repo.loadXmiIntoExtent(m1Extent, m2SyntaxModel, "testautomat.xml");


        StateAutomaton.Semantics.SemanticsFactory semanticsFactory = (StateAutomaton.Semantics.SemanticsFactory)repo.createFactory(m1Extent, m2SemanticsModel);
        AutomatonExample test = new AutomatonExample(m1Extent, semanticsFactory);

        java.lang.System.out.println("run example automaton.");
        test.sendSignalA();
        test.sendSignalB();
        test.sendSignalA();

        test.receiveSignals();
    }


    public AsTest() {
        super("Automaton example");
    }

    public void test() {
        try {
            exectute();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assertTrue(false);
        }
    }

    public static void main(String[] args) throws Exception {
        new AsTest().exectute();
    }
}
