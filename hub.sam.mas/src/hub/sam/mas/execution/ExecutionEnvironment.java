package hub.sam.mas.execution;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclObjectEnvironment;

import java.util.HashMap;
import java.util.Map;

import cmof.NamedElement;
import cmof.Package;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class ExecutionEnvironment extends AnalysisEnvironment {
	
	public ExecutionEnvironment(Extent m1Extent, Extent m2Extent, Repository repository) {
		super(m2Extent, m1Extent, repository);
	}	
	
	public Object evaluateInvariant(String invariant, NamedElement context, java.lang.Object self) {
		return ((cmof.reflection.Object)self).getAdaptor(OclObjectEnvironment.class).execute(invariant);		
	}
    
}
