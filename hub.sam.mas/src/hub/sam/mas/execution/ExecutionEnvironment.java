package hub.sam.mas.execution;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclProcessor;

import java.util.HashMap;
import java.util.Map;

import cmof.NamedElement;
import cmof.Package;
import cmof.UmlClass;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class ExecutionEnvironment extends AnalysisEnvironment {
		
	private final Extent m1Extent;
	private final Map<Package, Factory> factories = new HashMap<Package, Factory>();
	private final Repository repository;
	
	public ExecutionEnvironment(Extent m1Extent, Extent m2Extent, Repository repository) {
		super(m2Extent, repository);
		this.repository = repository;
		this.m1Extent = m1Extent;
	}	
	
	private Factory getFactoryForPackage(Package forPackage) {
		Factory result = factories.get(forPackage);
		if (result == null) {
			result = repository.createFactory(m1Extent, forPackage);
			factories.put(forPackage, result);
		}
		return result;
	}
	
	public Object createElement(UmlClass umlClass) {
		return getFactoryForPackage(umlClass.getPackage()).create(umlClass);
	}
	
	public Object evaluateInvariant(String invariant, NamedElement context, java.lang.Object self) {		
		return OclProcessor.evaluateExpression(invariant, context, getOclEnvironment(), OclProcessor.createRuntimeEnvironment(self));
	}
    
}
