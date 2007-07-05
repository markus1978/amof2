package hub.sam.mas.execution;

import hub.sam.mof.Repository;
import hub.sam.mof.ocl.OclEnvironment;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.ocl.OclObjectEnvironment;

import java.util.HashMap;
import java.util.Map;

import cmof.NamedElement;
import cmof.PrimitiveType;
import cmof.Type;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.Extent;

public class ExecutionEnvironment extends AnalysisEnvironment {	   
    
    private final Extent extent;
    private final Extent m2Extent;
        
	public ExecutionEnvironment(Extent m1Extent, Extent m2Extent, Repository repository) {
		super(m1Extent, m2Extent, repository);
        extent = m1Extent;
        this.m2Extent = m2Extent;
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
	
	private Map<String, Type> primitiveTypes = null;
	
	private void initialise() {
		primitiveTypes =  new HashMap<String, Type>();
		for(Object obj: m2Extent.getObject()) {
			if (obj instanceof PrimitiveType) {
				primitiveTypes.put(((PrimitiveType)obj).getName(), (PrimitiveType)obj);
			}
		}
	}		

	public Type getTypeForObject(Object object) {		
		Type result = null;
        if (object == null) {
        	if (primitiveTypes == null) {
    			initialise();
    		}
        	result = primitiveTypes.get("Object");
        } else if (object instanceof cmof.reflection.Object) {
        	result = ((cmof.reflection.Object)object).getMetaClass();
		} else {			
			if (primitiveTypes == null) {
				initialise();
			}
			result = primitiveTypes.get(object.getClass().getSimpleName());			
		}
        if (result == null) {
        	throw new OclException("could not determine type of an object");
        } else {
        	return result;
        }
	}
    
}
