package hub.sam.tools.tests;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.ocl.OclProcessor;

import java.util.Collection;
import java.util.Vector;

import org.oslo.ocl20.semantics.bridge.Environment;

import cmof.Classifier;
import cmof.Constraint;
import cmof.OpaqueExpression;
import cmof.Package;
import cmof.reflection.Extent;

public class AnalyzeUML2Ocl {	
	private void run() throws Exception {
		System.out.println("Read UML model.");
		Repository repo = Repository.getLocalRepository();		
		Package cmofPackage = (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		Extent uml = repo.createExtent("UML2");
		repo.loadXmiIntoExtent(uml, cmofPackage, "resources/models/uml/uml.xml");
		
		System.out.println("Generate code for UML model.");
		repo.generateCode(uml, "resources/uml-src/", false);
		
		System.out.println("Build environment.");
		Collection<Package> packages = new Vector<Package>(); 
		for (Object o: uml.objectsOfType(null, true)) {
			if (o instanceof Package) {
				packages.add((Package)o);
			}
		}
		Environment env = OclProcessor.createEnvironment(packages);
		
		int total = 0;
		int failures = 0;
		int subtotal = 0;
		
		loop: for (Object o: uml.objectsOfType(null, true)) {
			if (o instanceof Constraint) {
				subtotal++;
				Constraint constraint = (Constraint)o;
				if (constraint.getContext() instanceof Classifier) {
					System.out.println("Analyze " + constraint.getQualifiedName() + ".");
					total++;
					try {
						OclProcessor.analyzeInvariant(env, ((OpaqueExpression)constraint.getSpecification()).getBody(),constraint.getContext());
					} catch (OclException e) {
						System.err.println(e.getMessage());
						failures++;
					}
				} else {
					//System.err.println("CONTEXT: " + constraint.getContext() + ":" + ((cmof.reflection.Object)constraint.getContext()).getMetaClass());
				}
			}
		}		
		
		//Constraint constraint = (Constraint)uml.query("Package:InfrastructureLibrary/Package:Core/Package:Constructs/Class:Namespace/Constraint:importedMember_derived");
		//MofOclProcessor.analyzeInvariant(env, ((OpaqueExpression)constraint.getSpecification()).getBody(),constraint.getContext()); 
		
		System.out.println("Analyzed " + total + "(" + subtotal + ") invariants with " + failures + " failures.");
	}		
	
	public static void main(String[] args) throws Exception {
		new AnalyzeUML2Ocl().run();
	}
	
}
