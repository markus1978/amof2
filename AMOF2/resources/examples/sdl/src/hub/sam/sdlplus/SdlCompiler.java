package hub.sam.sdlplus;

import SDL.SdlAgent;
import SDL.SdlAgentInstance;
import SDL.SdlAgentInstanceSet;
import SDL.SdlAgentKind;
import SDL.SdlDataType;
import cmof.NamedElement;
import cmof.Property;
import cmof.UmlClass;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.as.layers.MultiLevelImplementationsManager;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.sdlplus.parser.ParseException;
import hub.sam.sdlplus.parser.SdlplusParser;
import hub.sam.sdlplus.semantics.SemanticAnalysis;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

/**
 * The main compiler class. It is responsible for the repository, command line arguments, calls parser and semantic analysis,
 * and provides unique string representations for model elements, used for consistent error reporting.
 */
public class SdlCompiler {
    private static SdlCompiler theCompiler = new SdlCompiler();
    private static final Trace theTrace = new Trace();

    /**
     * Returns a default compiler instance.
     */
    public static SdlCompiler getCompiler() {
        return theCompiler;
    }

    public static Trace getTrace() {
        return theTrace;
    }

    public SdlDataType getPidType() {
        return pidType;
    }

    private Extent sdlMetaExtent = null;
    private Extent sdlModelExtent = null;
    private Repository repository = null;
    private cmof.Package sdlPackage = null;
    private SdlDataType pidType = null;

    /**
     * Initializes the repository. It creates all nessasary extends for all needed meta-models and models.
     * These are two M2 extents and two M1 extents; one for the normal sdl model the other for the "sdl with concrete
     * syntax extensions.
     */
    private void initializeRepository() throws Exception {    	
        repository = Repository.getLocalRepository();
        repository.getConfiguration().setThreadSafe(true);
        sdlMetaExtent = SDL.SdlModel.createModel();
        sdlPackage = (cmof.Package)sdlMetaExtent.query("Package:SDL");
        sdlModelExtent = repository.createExtent("sdl-model-extent");
        ((ExtentImpl)sdlModelExtent).setCustomImplementationsManager(new MultiLevelImplementationsManager(
                repository.createFactory(sdlModelExtent, sdlPackage)));
    }

    /**
     * A utility function that adds values for line and column two a model element. This can be used to attach the location
     * of an item in the source file to a corresponding model element. The method can be called for any RefObject that as a
     * single value Integer attribute of name "line" and "column".
     */
    public void addContextM1(cmof.reflection.Object aObject, int line, int column) {
        aObject.set("line", line);
        aObject.set("column", column);
    }

    /**
     * A utility function that returns a descriptive string about the position of the given model element in the source
     * file.
     */
    public String getContextM1(cmof.reflection.Object aObject) {
        return "[" + aObject.get("line") + "," + aObject.get("column") + "]";
    }

    /**
     * A utility function that returns a descriptive string about the given model element in the source
     * file. The string includes the elements type, name and position in the source file.
     */
    public String getContextInformation(cmof.reflection.Object context) {
        return context.getMetaClass() + ":" + context.toString() + " at " + SdlCompiler.getCompiler().getContextM1(context);
    }

    /**
     * The main function. It analyses the command line arguments, initializes the input stream, initializes the repository,
     * calls the parser and semantic analysis, finally it writes the analysed model.
     */
    public synchronized void compile(String[] args) {
        InputStream in;
        // analyse the given arguments
        if (args.length == 0) {
            System.out.println("Reading from standard input.");
            in = System.in;
        } else if (args.length == 1) {
            System.out.println("Reading from file " + args[0] + ".");
            try {
                in = new java.io.FileInputStream(args[0]);
            } catch (java.io.FileNotFoundException e) {
                System.out.println("File " + args[0] + " not found.");
                return;
            }
        } else {
            System.out.println("Wrong usage.");
            return;
        }

        try {
            System.out.println("Initialiasing repository.");
            initializeRepository();

            System.out.println("Analysing the meta-model.");
            metaMetrics();

            SdlplusParser parser = new SdlplusParser(in);
            System.out.println("Start parsing.");
            parser.parse(repository, sdlMetaExtent, sdlModelExtent);

            System.out.println("Start semantic analysis.");
            boolean success = new SemanticAnalysis().doSemanticAnalysis(repository, sdlMetaExtent, sdlModelExtent);
            if (success) {
               //System.out.println("Write output.");
               //repository.writeExtentToXmi("resources/test-files/test-out.xml", sdlPackage, sdlModelExtent);
            }

            pidType = (SdlDataType)sdlModelExtent.query("SdlPackage:predefined/SdlDataType:Pid");

            System.out.println("Running the model");
            SdlAgent system = null;
            for (cmof.reflection.Object o: sdlModelExtent.getObject()) {
                if (o instanceof SdlAgent) {
                    if (((SdlAgent)o).getType().getKind() == SdlAgentKind.SYSTEM) {
                        system = (SdlAgent)o;
                    }
                }
            }
            if (system == null) {
                System.err.println("Model does not contain a system");
                return;
            }
            SdlAgentInstanceSet systemInstanceSet = (SdlAgentInstanceSet)system.instanciate();
            SdlAgentInstance systemInstance = systemInstanceSet.getValue().iterator().next();
            systemInstance.run();
            int i = 1;
            while(i == 1) {
                wait(5*1000);
            }
            theTrace.generateDot("out.dot");
            System.exit(0);
            /*
            SdlGate envGate = (SdlGate)sdlModelExtent.query("SdlPackage:daemonGame_pkg/SdlAgentType:daemonGameSystem_type/SdlGate:envGate");
            SdlSignal newGameType = (SdlSignal)sdlModelExtent.query("SdlPackage:daemonGame_pkg/SdlAgentType:daemonGameSystem_type/SdlSignal:newGame");
            SdlSignalInstance newGame = newGameType.metaCreateSdlSignalInstance();
            systemInstance.dispatchSignal(newGame, envGate);
            */
        } catch (ParseException e) {
            System.out.println("Encountered errors during parse:");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public synchronized void metaMetrics() {
        // SDL class number / methods

        Collection<UmlClass> sdlClasses = new Vector<UmlClass>();
        Collection<Property> sdlProperties = new Vector<Property>();
        for (Object element: sdlMetaExtent.getObject()) {
            if (element instanceof UmlClass) {
                if ("SDL".equals(((NamedElement)element).getNamespace().getName())) {
                    sdlClasses.add((UmlClass)element);
                }
            } else if (element instanceof Property) {
                if (((NamedElement)element).getNamespace() != null) {
                    if ("SDL".equals(((NamedElement)element).getNamespace().getNamespace().getName())) {
                         sdlProperties.add((Property)element);
                    }
                }
            }
        }

        // Used Abstract classes / methods
        // inheritences of abstract classes
        int sdlAbstractSpecialisations = 0;
        int specialisation = 0;
        Collection<UmlClass> abstractClasses = new HashSet<UmlClass>();
        for (UmlClass sdlClass: sdlClasses) {
            for (UmlClass superClass: sdlClass.getSuperClass()) {
                specialisation++;
                if (!"SDL".equals(superClass.getNamespace().getName())) {
                    sdlAbstractSpecialisations++;
                    abstractClasses.add(superClass);
                }
            }
        }

        // Used Abstract classes / methods
        int sdlAbstractRefinements = 0;
        int refinements = 0;
        Collection<Property> abstractProperties = new HashSet<Property>();
        for (Property sdlProperty: sdlProperties) {
            for (Property redefinedProperty: sdlProperty.getRedefinedProperty()) {
                refinements++;
                if (!"SDL".equals(redefinedProperty.getNamespace().getNamespace().getName())) {
                    sdlAbstractRefinements++;
                    abstractProperties.add(redefinedProperty);
                }
            }
            for (Property subsettedProperty: sdlProperty.getSubsettedProperty()) {
                refinements++;
                if (!"SDL".equals(subsettedProperty.getNamespace().getNamespace().getName())) {
                    sdlAbstractRefinements++;
                    abstractProperties.add(subsettedProperty);
                }
            }
        }

        int operations = 0;
        for (UmlClass aClass: abstractClasses) {
            operations += aClass.getOwnedOperation().size();
        }
        for (UmlClass aClass: sdlClasses) {
            operations += aClass.getOwnedOperation().size();
        }

        System.out.println("Number of used abstract classes:               " + abstractClasses.size());
        System.out.println("Number of SDL classes:                         " + sdlClasses.size());
        System.out.println("Number of specialisations of abstract classes: " + sdlAbstractSpecialisations);
        System.out.println("Number of specialisations of classes in SDL:   " + specialisation);
        System.out.println("--------------------------------------------------");
        System.out.println("Number of abstract properties:                 " + abstractProperties.size());
        System.out.println("Number of SDL properties:                      " + sdlProperties.size());
        System.out.println("Number of refinement of abstract properties:   " + sdlAbstractRefinements);
        System.out.println("Number of specialisation of properties in SDL: " + refinements);
        System.out.println("Number of declared operations:                 " + operations);
    }

    /**
     * Gets the default compiler instance and runs it.
     */
    public static void main(String[] args) {
        SdlCompiler.getCompiler().compile(args);
    }
}
