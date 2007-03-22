package hub.sam.mof.ocl;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.oslobridge.MofLog;
import hub.sam.mof.ocl.oslobridge.MofOclListImpl;
import hub.sam.mof.ocl.oslobridge.MofOclProcessor;
import hub.sam.mof.ocl.oslobridge.MofOclSetImpl;
import hub.sam.mof.util.AssertionException;

import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.semantics.bridge.EnvironmentImpl;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclUndefined;
import org.oslo.ocl20.syntax.ast.contexts.PackageDeclarationAS;
import org.oslo.ocl20.synthesis.RuntimeEnvironment;
import org.oslo.ocl20.synthesis.RuntimeEnvironmentImpl;

import uk.ac.kent.cs.kmf.util.ILog;
import warehouse.Box;
import warehouse.Item;
import warehouse.Rack;
import warehouse.WarehouseModel;
import warehouse.warehouseFactory;
import cmof.NamedElement;
import cmof.Package;
import cmof.reflection.Extent;

public class OclProcessor {

	private static final ILog myLog = new MofLog(System.out);
	private static final org.oslo.ocl20.OclProcessor myProcessor = new MofOclProcessor(myLog);	

	private static org.oslo.ocl20.OclProcessor getDefaultProcessor() {
		return myProcessor;
	}
	
	public static Environment createEnvironment(Iterable<? extends Package> packages) {
		Environment env = new EnvironmentImpl(getDefaultProcessor().getBridgeFactory());
		env = addPackagesForEnvironment(env, packages);
		return env;
	}

	public static Environment createEnvironment(Package pkg) {
		Environment env = new EnvironmentImpl(getDefaultProcessor().getBridgeFactory());
		env = env.addNamespace(getDefaultProcessor().getBridgeFactory().buildNamespace(pkg));
		env = addPackagesForEnvironment(env, pkg.getNestedPackage());
		return env;
	}

	private static Environment addPackagesForEnvironment(Environment env, Iterable<? extends Package> packages) {
		for(cmof.Package pkg: packages) {
			env = env.addNamespace(getDefaultProcessor().getBridgeFactory().buildNamespace(pkg));
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
}
