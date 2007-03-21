package hub.sam.mof.ocl.oslobridge;

import hub.sam.mof.PlugInActivator;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.ocl.OclException;
import hub.sam.mof.util.AssertionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Namespace;
import org.oslo.ocl20.semantics.bridge.OclModelElementType;
import org.oslo.ocl20.semantics.bridge.Operation;
import org.oslo.ocl20.semantics.bridge.Property;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.standard.types.OclAnyTypeImpl;

import cmof.DataType;
import cmof.Enumeration;
import cmof.NamedElement;
import cmof.StructuralFeature;
import cmof.Type;
import cmof.UmlClass;

public class MofOclModelElementTypeImpl extends OclAnyTypeImpl implements OclModelElementType {

	private final MofClassSemantics semantics;
	private final cmof.Classifier mofElementType;
	private final Map<String, Property> mofProperties = new HashMap<String, Property>();
	
	@SuppressWarnings("unchecked")
	public MofOclModelElementTypeImpl(cmof.Classifier impl, OclProcessor proc) {		
        super(proc);
        if (impl.getName().equals("Classifier")) {
        	System.out.println("#### Geht nicht ...");
        }
        mofElementType = impl;
        List<Operation> operations = super.getOperations();
        operations.add(proc.getBridgeFactory().buildOperation(proc.getTypeFactory().buildBooleanType(), "=", new Classifier[] { this }));
        operations.add(proc.getBridgeFactory().buildOperation(proc.getTypeFactory().buildBooleanType(), "<>", new Classifier[] { this }));
        List<Operation> oclAnyOper = proc.getTypeFactory().buildOclAnyType().getOperations();
        operations.addAll(oclAnyOper);
        if (impl instanceof UmlClass) {
        	semantics = MofClassifierSemantics.createClassClassifierForUmlClass((UmlClass)impl);
        } else {
        	semantics = null;
        }
    } 

    public cmof.Classifier getImplementation() {
    	return mofElementType;
    }
    
    public void addAdditionalProperty(String name, Object value, Type propertyType) {
    	MofEvaluationAdaptor.currentValue = value;
    	MofAdditionalPropertyImpl additionalProperty = new MofAdditionalPropertyImpl(name, processor.getBridgeFactory().buildClassifier(propertyType));	
    	mofProperties.put(name, additionalProperty);
    }
    
    public void removeAdditionalProperty(String name) {
    	MofEvaluationAdaptor.currentValue = null;
    	mofProperties.remove(name);
    }
                  
    @Override
	public Property lookupProperty(String name) {
        Property prop = mofProperties.get(name);
        if (prop == null) {
            if (mofElementType instanceof UmlClass) {
            	            	
                StructuralFeature sf = semantics.getProperty(name);
                if (sf == null) {
                    String n2 = name.substring(0, 1).toUpperCase() + name.substring(1);
                    sf = semantics.getProperty(n2);
                }
                if (sf != null) {
                	prop = super.processor.getBridgeFactory().buildProperty(sf);
                	mofProperties.put(name, prop);
                }
            } else if (mofElementType instanceof Enumeration) {
            	// emtpy
            } else if (mofElementType instanceof DataType) {
            	// empty
            }
        }
        return prop;
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<Property> getProperties() {
        return new Vector(mofProperties.values());
    }

    @Override
	public void addProperty(Property p) {
        mofProperties.put(p.getName(), p);
    }

    @Override
	public Operation lookupOperation(String name, List types) {    	
        Operation operation = super.lookupOperation(name, types);
	    if (operation==null) {	    	
		    if (mofElementType instanceof UmlClass) {
		    	StringBuffer fullName = new StringBuffer(name);
		    	for(Object o: types) {
		    		fullName.append("_");
		    		if (o instanceof CollectionType) {		    			
		    			fullName.append(((NamedElement)((MofOclModelElementTypeImpl)((CollectionType)o).getBaseElementType()).getMofDelegate()).getQualifiedName());
		    		} else if (o instanceof Classifier) {
		    			fullName.append(((NamedElement)((MofOclModelElementTypeImpl)o).getMofDelegate()).getQualifiedName());
		    		} else {
		    			throw new OclException("Unknown operation parameter type.");
		    		}
		    	}
	            cmof.Operation cmofOperation = semantics.getFinalOperation(fullName.toString());
	            if (cmofOperation != null) {
	            	return new MofOperationImpl(cmofOperation, super.processor);
	            }
	        }
        }
        return operation;
    }
    
    @Override
	public String getName() {
        return mofElementType.getName();
    }

    @Override
	public Namespace getNamespace() {
        Namespace ns = super.getNamespace();
        if (ns == null) {
            ns = processor.getBridgeFactory().buildNamespace(mofElementType.getPackage());
            super.setNamespace(ns);
        }
        return ns;
    }

    @Override
	public Boolean conformsTo(Classifier c) {
        if (c.equals(processor.getTypeFactory().buildOclAnyType()))
            return Boolean.TRUE;
        if (mofElementType instanceof UmlClass) {
            if (c instanceof MofOclModelElementTypeImpl) {
                cmof.Classifier cc = ((MofOclModelElementTypeImpl) c).mofElementType;
                UmlClass self = ((UmlClass) mofElementType);
                if (self.conformsTo(cc)) {
                	return Boolean.TRUE;
                }
            }
        } else if (mofElementType instanceof Enumeration) {
        	// emtpy
        } else if (mofElementType instanceof DataType) {
        	// emtpy
        }
        return Boolean.FALSE;
    }

    @Override
	public Object accept(SemanticsVisitor v, Object obj) {
        return v.visit(this, obj);
    }

    @Override
	public String toString() {
        return "OclModelElementType(" + mofElementType.getQualifiedName() + ")";
    }

    public Object getMofDelegate() {
    	return mofElementType;
    }
	@Override
	public Object getDelegate() {
		try {
			return PlugInActivator.getClassLoader().loadClass(JavaMapping.mapping.getFullQualifiedJavaIdentifier(mofElementType));
		} catch (ClassNotFoundException ex) {
			throw new AssertionException(ex);
		}
	}    
}
