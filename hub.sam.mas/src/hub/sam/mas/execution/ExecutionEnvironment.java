package hub.sam.mas.execution;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclEnvironment;
import hub.sam.mof.ocl.OclObjectEnvironment;
import cmof.NamedElement;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Extent;

public class ExecutionEnvironment extends AnalysisEnvironment {	   
    
    private final Extent extent;
    
	public ExecutionEnvironment(Extent m1Extent, Extent m2Extent, Repository repository) {
		super(m1Extent, m2Extent, repository);
        extent = m1Extent;
	}	
	
	public Object evaluateInvariant(String invariant, NamedElement context, java.lang.Object self) {
        if (self == null) {
            OclEnvironment environment = extent.getAdaptor(OclEnvironment.class);
            if (environment == null) {
                throw new IllegalArgumentException("ocl environment adaptor cannot be created for this object");
            } else {                    
                return OclObjectEnvironment.createObjectEnvironment(null, environment).execute(invariant);
            }
        } else {        
            return ((cmof.reflection.Object)self).getAdaptor(OclObjectEnvironment.class).execute(invariant);
        }
	}
    
}
