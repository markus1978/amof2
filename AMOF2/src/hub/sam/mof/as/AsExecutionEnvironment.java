package hub.sam.mof.as;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.MofOclModelElementTypeImpl;
import hub.sam.mof.ocl.MofOclProcessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.oslo.ocl20.OclProcessor;

import as.ContextExtensionPin;

import cmof.NamedElement;
import cmof.Package;
import cmof.Type;
import cmof.UmlClass;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class AsExecutionEnvironment extends AsAnalysisEnvironment {
		
	private final Extent m1Extent;
	private final Map<Package, Factory> factories = new HashMap<Package, Factory>();
	private final Repository repo;
	
	public AsExecutionEnvironment(Extent m1Extent, Extent m2Extent, Repository repo) {
		super(m2Extent, repo);
		this.repo = repo;
		this.m1Extent = m1Extent;
	}	
	
	private Factory getFactoryForPackage(Package forPackage) {
		Factory result = factories.get(forPackage);
		if (result == null) {
			result = repo.createFactory(m1Extent, forPackage);
			factories.put(forPackage, result);
		}
		return result;
	}
	
	public Object createElement(UmlClass umlClass) {
		return getFactoryForPackage(umlClass.getPackage()).create(umlClass);
	}
	
	public Object evaluateInvariant(String invariant, NamedElement context, java.lang.Object self) {		
		return MofOclProcessor.evaluateExpression(invariant, context, getOclEnvironment(), MofOclProcessor.createRuntimeEnvironment(self));
	}
}
