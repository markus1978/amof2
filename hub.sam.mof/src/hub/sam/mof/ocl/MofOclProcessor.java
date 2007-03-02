package hub.sam.mof.ocl;

import cmof.Enumeration;
import cmof.NamedElement;
import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.util.AssertionException;
import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.OclProcessorImpl;
import org.oslo.ocl20.semantics.analyser.OclDebugVisitorImpl;
import org.oslo.ocl20.semantics.analyser.OclSemanticAnalyser;
import org.oslo.ocl20.semantics.analyser.OclSemanticAnalyserImpl;
import org.oslo.ocl20.semantics.analyser.OclSemanticAnalyserVisitorImpl;
import org.oslo.ocl20.semantics.bridge.BridgeFactory;
import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.semantics.bridge.EnvironmentImpl;
import org.oslo.ocl20.semantics.model.types.TypeFactory;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclUndefined;
import org.oslo.ocl20.standard.lib.StdLibAdapter;
import org.oslo.ocl20.standard.lib.StdLibAdapterImpl;
import org.oslo.ocl20.standard.types.TypeFactoryImpl;
import org.oslo.ocl20.syntax.ast.contexts.PackageDeclarationAS;
import org.oslo.ocl20.syntax.parser.OclParser;
import org.oslo.ocl20.syntax.parser.OclParserImpl;
import org.oslo.ocl20.synthesis.ModelEvaluationAdapter;
import org.oslo.ocl20.synthesis.ModelGenerationAdapter;
import org.oslo.ocl20.synthesis.OclCodeGenerator;
import org.oslo.ocl20.synthesis.OclEvaluator;
import org.oslo.ocl20.synthesis.OclEvaluatorImpl;
import org.oslo.ocl20.synthesis.OclEvaluatorVisitorImpl;
import org.oslo.ocl20.synthesis.RuntimeEnvironment;
import org.oslo.ocl20.synthesis.RuntimeEnvironmentImpl;
import uk.ac.kent.cs.kmf.util.ILog;
import warehouse.Box;
import warehouse.Item;
import warehouse.Rack;
import warehouse.WarehouseModel;
import warehouse.warehouseFactory;

import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MofOclProcessor extends OclProcessorImpl {

	private final OclParser parser = new OclParserImpl();
	private final OclSemanticAnalyser analyser = new OclSemanticAnalyserImpl(this, new OclSemanticAnalyserVisitorImpl(this), new OclDebugVisitorImpl(), null);
	private final BridgeFactory bridgeFactory = new MofBridgeFactory(this);
	private final TypeFactory typeFactory = new TypeFactoryImpl(this);
	private final ModelEvaluationAdapter evaluationAdapter = new MofEvaluationAdaptor(this);
	private final OclEvaluatorImpl evaluator = new OclEvaluatorImpl(this, new OclEvaluatorVisitorImpl(this));
	private final StdLibAdapterImpl stdLibAdapter = new MofStdLibAdapterImpl(this);

	private static final ILog myLog = new MofLog(System.out);
	private static final OclProcessor myProcessor = new MofOclProcessor(myLog);
	private Map<Class, Enumeration> enumerations = new HashMap<Class, Enumeration>();

	public static OclProcessor createProcessor() {
		return myProcessor;
	}

	public static Environment createEnvironment(Iterable<? extends Package> packages) {
		Environment env = new EnvironmentImpl(createProcessor().getBridgeFactory());
		env = addPackagesForEnvironment(env, packages);
		return env;
	}

	public static Environment createEnvironment(Package pkg) {
		Environment env = new EnvironmentImpl(createProcessor().getBridgeFactory());
		env = env.addNamespace(createProcessor().getBridgeFactory().buildNamespace(pkg));
		env = addPackagesForEnvironment(env, pkg.getNestedPackage());
		return env;
	}

	private static Environment addPackagesForEnvironment(Environment env, Iterable<? extends Package> packages) {
		for(Package pkg: packages) {
			env = env.addNamespace(createProcessor().getBridgeFactory().buildNamespace(pkg));
			env = addPackagesForEnvironment(env, pkg.getNestedPackage());
		}
		return env;
	}

	public static RuntimeEnvironment createRuntimeEnvironment(java.lang.Object self) {
		RuntimeEnvironment result = new RuntimeEnvironmentImpl();
		result.setValue("self", self);
		return result;
	}

	public static boolean evaluateInvariant(String invariant, NamedElement context, Environment env, RuntimeEnvironment runEnv) {
		ILog evalLog = new MofLog(System.out);
		OclAny oclResult = myProcessor.evaluateAsOCL(oclForInvariant(invariant, context), env, runEnv, evalLog);
		if (evalLog.hasErrors()) {
			evalLog.finalReport();
			throw new OclException("Errors during evaluation of '" + invariant + "' in context: " + context.getQualifiedName());
		}
		if (oclResult instanceof OclUndefined) {
			throw new OclException("evluated to undefined");
		} else if (oclResult instanceof OclBoolean) {
			return ((Boolean)((OclBoolean)oclResult.oclAsType(oclResult.oclType())).asJavaObject()).booleanValue();
		} else {
			throw new OclException("Evaluation of '" + invariant + "', in context: " + context.getQualifiedName() + ", did not result in a boolean");
		}
	}

	@SuppressWarnings("unchecked")
	public static Object evaluateExpression(String invariant, NamedElement context, Environment env, RuntimeEnvironment runEnv) {        
        ILog evalLog = new MofLog(System.out);
		OclAny oclResult = myProcessor.evaluateAsOCL(oclForInvariant(invariant, context), env, runEnv, evalLog);
		if (evalLog.hasErrors()) {
			evalLog.finalReport();
			throw new OclException("Errors during evaluation of '" + invariant + "' in context: " + context.getQualifiedName());
		}
		if (oclResult instanceof OclUndefined) {
			throw new OclException("evluated to undefined: " + invariant);
		} else {
		    if (oclResult instanceof OclReal) {
				return ((OclReal)oclResult).round().asJavaObject();
			} else if (oclResult instanceof OclCollection) {
				Object javaResult = oclResult.asJavaObject();
				if (javaResult instanceof List) {
					return new MofOclListImpl((List)javaResult, oclResult);
				} else if (javaResult instanceof Collection) {
					return new MofOclSetImpl((Collection)javaResult, oclResult);
				} else {
					throw new AssertionException("not implemented.");
				}
			} else {
				Object result = oclResult;
				while (result instanceof OclAny) {
					result = ((OclAny)result).asJavaObject();
				}
				return result;
			}
		}
	}

	public static List analyzeInvariant(Environment env, String invariant, NamedElement context) throws OclException {
		ILog parseLog = new MofLog(System.out);
		PackageDeclarationAS parseResult = myProcessor.getParser().parse(new StringReader(oclForInvariant(invariant, context)), parseLog, false);
		if (parseLog.hasErrors()) {
			parseLog.finalReport();
			throw new OclException("Errors during parse of '" + invariant + "' in context: " + context.getQualifiedName());
		}

		ILog analyzeLog = new MofLog(System.out);
	    List result = myProcessor.getAnalyser().analyse(parseResult, env, analyzeLog, true);

		if (analyzeLog.hasErrors()) {
			analyzeLog.finalReport();
			throw new OclException("Errors during analysis of '" + invariant + "' in context: " + context.getQualifiedName());
		} else {
			analyzeLog.finalReport();
		}

		if (myLog.hasErrors()) {
			myLog.finalReport();
			throw new OclException("Global Ocl processor error occured.");
		} else {
			myLog.reset();
		}

		return result;
	}

	private static String oclForInvariant(String invariant, NamedElement context) {
		StringBuffer constraint = new StringBuffer("context ");
		constraint.append(context.getQualifiedName().replaceAll("\\.","::"));
		constraint.append("\ninv: ");
		constraint.append(invariant);
		return constraint.toString();
	}

	private MofOclProcessor(ILog log) {
		super(log);
	}

	public StdLibAdapter getStdLibAdapter() {
		return stdLibAdapter;
	}

	public StdLibAdapter getStdLibGenerationAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStdLibAdapterName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelGenerationAdapter getModelGenerationAdapter() {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelEvaluationAdapter getModelEvaluationAdapter() {
		return evaluationAdapter;
	}

	public String getModelImplAdapterName() {
		// TODO Auto-generated method stub
		return null;
	}

	public BridgeFactory getBridgeFactory() {
		return bridgeFactory;
	}

	public TypeFactory getTypeFactory() {
		return typeFactory;
	}

	public OclParser getParser() {
		return parser;
	}

	public OclSemanticAnalyser getAnalyser() {
		return analyser;
	}

	public OclEvaluator getEvaluator() {
		return evaluator;
	}

	public OclCodeGenerator getGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addModel(Object mdl) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		// init mof repository
		Extent warehouseModel = WarehouseModel.createModel();
		cmof.Package warehouse = (cmof.Package)warehouseModel.query("Package:warehouse");

		NamedElement context = (NamedElement)warehouseModel.query("Package:warehouse/Class:Element");
		Environment env = createEnvironment(warehouse);

		String invariant = "if container->notEmpty() then position = self.container.position.concat('.').concat(self.identifier) else position = identifier endif";

		analyzeInvariant(env, invariant, context);

		Repository repository = Repository.getLocalRepository();
        Extent warehouseExtent = repository.createExtent("warehouseExtent");
		warehouseFactory factory = (warehouseFactory)repository.createFactory(warehouseExtent, warehouse);

		Rack a = factory.createRack(); a.setIdentifier("a");
        Rack b = factory.createRack(); b.setIdentifier("b");

        Box a1 = factory.createBox(); a1.setIdentifier("a1");
        Box a2 = factory.createBox(); a2.setIdentifier("a2");
        Box b1 = factory.createBox(); b1.setIdentifier("b1");
        a.getBox().add(a1); a.getBox().add(a2);
        b.getBox().add(b1);

        Item a1A = factory.createItem(); a1A.setIdentifier("a1A"); a1A.setWeight(1);
        Item a2A = factory.createItem(); a2A.setIdentifier("a2A"); a2A.setWeight(2);
        Item a2B = factory.createItem(); a2B.setIdentifier("a2B"); a2B.setWeight(3);
        Item b1A = factory.createItem(); b1A.setIdentifier("b1A"); b1A.setWeight(1);
        a1.getItem().add(a1A);
        a2.getItem().add(a2A); a2.getItem().add(a2B);
        b1.getItem().add(b1A);

		RuntimeEnvironment runEnv = createRuntimeEnvironment((cmof.reflection.Object)b1A);
		System.out.println("Result: " + evaluateInvariant(invariant, context, env, runEnv));
	}

	/**
	 * This one is only necessary becouse oslo needs to know the OclType for a Java value.
	 * To get hold of the original MOF Enumeration based on a Java enum value this map between
	 * java enum class and MOF Enumeration can be used.
	 */
	public Map<Class, Enumeration> getEnumerations() {
		return enumerations;
	}
}
