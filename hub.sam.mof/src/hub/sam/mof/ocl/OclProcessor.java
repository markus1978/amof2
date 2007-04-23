package hub.sam.mof.ocl;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.oslobridge.MofEnumerationImpl;
import hub.sam.mof.ocl.oslobridge.MofLog;
import hub.sam.mof.ocl.oslobridge.MofOclListImpl;
import hub.sam.mof.ocl.oslobridge.MofOclModelElementTypeImpl;
import hub.sam.mof.ocl.oslobridge.MofOclProcessor;
import hub.sam.mof.ocl.oslobridge.MofOclSetImpl;
import hub.sam.mof.util.AssertionException;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.semantics.bridge.EnvironmentImpl;
import org.oslo.ocl20.semantics.model.contexts.ClassifierContextDecl;
import org.oslo.ocl20.semantics.model.contexts.Constraint;
import org.oslo.ocl20.semantics.model.contexts.ConstraintKind$Class;
import org.oslo.ocl20.semantics.model.contexts.ContextDeclaration;
import org.oslo.ocl20.semantics.model.types.BagType;
import org.oslo.ocl20.semantics.model.types.BooleanType;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OrderedSetType;
import org.oslo.ocl20.semantics.model.types.RealType;
import org.oslo.ocl20.semantics.model.types.SequenceType;
import org.oslo.ocl20.semantics.model.types.SetType;
import org.oslo.ocl20.semantics.model.types.StringType;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclUndefined;
import org.oslo.ocl20.standard.types.PrimitiveImpl;
import org.oslo.ocl20.syntax.ast.contexts.PackageDeclarationAS;
import org.oslo.ocl20.synthesis.RuntimeEnvironment;
import org.oslo.ocl20.synthesis.RuntimeEnvironmentImpl;

import core.primitivetypes.UnlimitedNatural;

import uk.ac.kent.cs.kmf.util.ILog;
import warehouse.Box;
import warehouse.Item;
import warehouse.Rack;
import warehouse.WarehouseModel;
import warehouse.warehouseFactory;
import cmof.MultiplicityElement;
import cmof.NamedElement;
import cmof.Package;
import cmof.Type;
import cmof.reflection.Extent;

public class OclProcessor {

    public static final String OCL_CONTEXT_UNDEFINED = "OclContextUndefined";
    
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
	
	@SuppressWarnings("unchecked")
	public static Object evaluateExpression(List cs, String invariant, NamedElement context, RuntimeEnvironment runEnv) {        
        ILog evalLog = new MofLog(System.out);
		OclAny oclResult = evaluateAsOcl(cs,  runEnv, evalLog);
		if (evalLog.hasErrors()) {
			evalLog.finalReport();
			throw new OclException("Errors during evaluation of '" + invariant + "' in context: " + (context == null ? "null" : context.getQualifiedName()));
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
	
    private static OclAny evaluateAsOcl(List cs, RuntimeEnvironment renv, ILog log) {    	        
        if (cs == null)
            return null;
        OclAny result = null;
        List results = new Vector();
        Iterator i = cs.iterator();
        while (i.hasNext()) {
            ContextDeclaration decl = (ContextDeclaration) i.next();
            Iterator ci = decl.getConstraint().iterator();
            while (ci.hasNext()) {
                Constraint constraint = (Constraint)ci.next();
                if (! ConstraintKind$Class.DEF.equals(constraint.getKind())) {
                    OclAny x = myProcessor.getEvaluator().evaluateAsOCL(decl, renv, log);
                    results.add(x);
                }
            }
        }
        return (OclAny)results.get(0);
    }    

	public static List analyzeExpression(Environment env, String expression, NamedElement context) throws OclException {
		ILog parseLog = new MofLog(System.out);
		PackageDeclarationAS parseResult = myProcessor.getParser().parse(new StringReader(oclForInvariant(expression, context)), parseLog, false);
		if (parseLog.hasErrors()) {
			parseLog.finalReport();
			throw new OclException("Errors during parse of '" + expression + "' in context: " + (context == null ? "null" : context.getQualifiedName()));
		}

		ILog analyzeLog = new MofLog(System.out);
	    List result = myProcessor.getAnalyser().analyse(parseResult, env, analyzeLog, true);

		if (analyzeLog.hasErrors()) {
			analyzeLog.finalReport();
			throw new OclException("Errors during analysis of '" + expression + "' in context: " + (context == null ? "null" : context.getQualifiedName()));
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
	
	public static List analyzeExpression(Environment oclEnvironment, String expression, NamedElement context, Type requiredType, 
			boolean isCollection, boolean isUnique, boolean isOrdered) throws OclException {
		String errorPrefix = "Ocl expression [" + expression + "] ";
		List result = null;
		try {
			result = OclProcessor.analyzeExpression(oclEnvironment, expression, context);
		} catch (OclException ex) {
			throw new OclException(ex);
		}
		
		if (result != null && requiredType != null) {
			for(Object o1: result) {
				ClassifierContextDecl ccd = (ClassifierContextDecl)o1;
				for (Object o2: ccd.getConstraint()) {
					org.oslo.ocl20.semantics.model.contexts.Constraint constraint = (org.oslo.ocl20.semantics.model.contexts.Constraint)o2;
					Classifier exprType = constraint.getBodyExpression().getType();
					Classifier exprClassifierType = null;
					if (exprType instanceof MofOclModelElementTypeImpl || exprType instanceof MofEnumerationImpl || exprType instanceof PrimitiveImpl) {
						exprClassifierType = exprType;
						if (!(!(context instanceof MultiplicityElement) || ((MultiplicityElement)context).getUpper() == 1)) {
							throw new OclException(errorPrefix + "has collection type for non collection feature.");							
						}
					} else if (exprType instanceof CollectionType) {
						if (!isCollection) {
							throw new OclException(errorPrefix + "has non collection type for collection feature.");						
						}
						exprClassifierType = ((CollectionType)exprType).getBaseElementType();
						if (exprType instanceof BagType) {
							if (isOrdered) {
								throw new OclException(errorPrefix + "has non ordered collection type for an ordered feature.");								
							}
							if (isUnique) {
								throw new OclException(errorPrefix + "has non unique collection type for an unique feature.");								
							}
						} else if (exprType instanceof SequenceType) {							
							if (isUnique) {
								throw new OclException(errorPrefix + "has non unique collection type for an unique feature.");
							}
						} else if (exprType instanceof SetType) {
							if (!(exprType instanceof OrderedSetType)) {
								if (isOrdered) {
									throw new OclException(errorPrefix + "has non ordered collection type for an ordered feature.");									
								}								
							}
						} 
					} else {
						throw new OclException(errorPrefix + "has unknown static type.");
					}
					
					if (exprClassifierType instanceof MofOclModelElementTypeImpl || exprClassifierType instanceof MofEnumerationImpl) {
						Object mofDelegate = null;
						if (exprClassifierType instanceof MofOclModelElementTypeImpl) {
							mofDelegate = ((MofOclModelElementTypeImpl)exprClassifierType).getMofDelegate();
						} else {
							mofDelegate = ((MofEnumerationImpl)exprClassifierType).getMofDelegate();
						}
						if (!requiredType.equals(mofDelegate)) {
							throw new OclException(errorPrefix + "has wrong type or base type.");
						}											
					} else if (exprClassifierType instanceof PrimitiveImpl) {
						if (exprClassifierType instanceof BooleanType) {
							if (!requiredType.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
								throw new OclException(errorPrefix + "has wrong type or base type.");
							}
						} else if (exprClassifierType instanceof StringType) {
							if (!requiredType.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
								throw new OclException(errorPrefix + "has wrong type or base type.");
							}
						} else if (exprClassifierType instanceof RealType) {
							if (!requiredType.getName().equals(Integer.class.getSimpleName()) && !requiredType.getName().equals(UnlimitedNatural.class.getSimpleName())) {
								throw new OclException(errorPrefix + "has wrong type or base type.");
							}
						}
					} else {
						throw new OclException(errorPrefix + "has wrong base type.");
					}
				}
			}
		}
		return result;
	}


	private static String oclForInvariant(String invariant, NamedElement context) {
        String contextString = null;
        if (context == null) {
            /* This is a hack. The ocl interpreter needs a context in all cases. Unfortunatly when
             * we want to evaluate an expression on null.
             */        
            contextString = OCL_CONTEXT_UNDEFINED;
        } else {
            contextString = context.getQualifiedName().replaceAll("\\.","::");
        }
		StringBuffer constraint = new StringBuffer("context ");
		constraint.append(contextString);
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

		analyzeExpression(env, invariant, context);

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
