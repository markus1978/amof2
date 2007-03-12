/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package hub.sam.mof.instancemodel.conversion;

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.xmi.IIdMemorizer;
import hub.sam.mof.Repository;
import hub.sam.util.AbstractFluxBox;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Converter <Co,Po,DataValueo,Ci,Pi,T,D,DataValuei> {

    private final Conversion<Co,Po,DataValueo,Ci,Pi,T,D,DataValuei> conversion;
    private InstanceModel<Ci,Pi,DataValuei> targetModel;
    private InstanceModel<Co,Po,DataValueo> sourceModel;
    private AbstractFluxBox<String, ClassInstance<Ci,Pi,DataValuei>, Ci> fluxBox =
        new AbstractFluxBox<String, ClassInstance<Ci,Pi,DataValuei>, Ci>() {
            @Override
            protected ClassInstance<Ci,Pi,DataValuei> createValue(String key, Ci params) {
                return targetModel.createInstance(key, params);
            }
    };
    
    private IIdMemorizer idMemorizer = null;

    public Converter(Conversion<Co,Po,DataValueo,Ci,Pi,T,D,DataValuei> conversion) {
        super();
        this.conversion = conversion;
    }
    
    public void setIdMemorizer(IIdMemorizer memorizer) {
    	idMemorizer = memorizer;
    }

    public void convert(InstanceModel<Co,Po, DataValueo> model, InstanceModel<Ci,Pi,DataValuei> targetModel)
            throws MetaModelException {
        this.targetModel = targetModel;
        this.sourceModel = model;

        for(ValueSpecification<Co,Po,DataValueo> instance: model.getOutermostComposites()) {
            try {
                convertInstance(instance.asInstanceValue().getInstance());
            } catch (MetaModelException e) {
                if (Repository.getConfiguration().generousXMI()) {
                    System.out.println("Warning: " + e.getMessage());
                } else {
                    throw e;
                }
            }
        }
    }

    private Map<ClassInstance<Co,Po,DataValueo>, String> ids = new HashMap<ClassInstance<Co,Po,DataValueo>,String>();
    private static int unique = 1;
    public String getId(ClassInstance<Co,Po,DataValueo> instance) {
        String id = instance.getId();
        if (id == null) {
            id = ids.get(instance);
            if (id == null) {
                id = Integer.toString((hashCode() % (Integer.MAX_VALUE / 2)) + unique++);
                ids.put(instance, id);
            }
        }
        return id;
    }

    public AbstractFluxBox<String, ClassInstance<Ci,Pi,DataValuei>, Ci> getFluxBox() {
        return fluxBox;
    }

    private Collection<ClassInstance<Co,Po,DataValueo>> alreadyConverted = new HashSet<ClassInstance<Co,Po,DataValueo>>();

    public final ClassInstance<Ci,Pi,DataValuei> convertInstance(ClassInstance<Co,Po,DataValueo> instance)
            throws MetaModelException {
        Ci classifieri = conversion.getClassifier(instance.getClassifier());
        ClassInstance<Ci,Pi,DataValuei> targetInstance = getFluxBox().getObject(getId(instance), classifieri);
        if (targetInstance.getClassifier() == null) {
            targetInstance.setClassifier(classifieri);
        }

        if (alreadyConverted.contains(instance)) {
            return targetInstance;
        } else {
            alreadyConverted.add(instance);
        }

        for(StructureSlot<Co,Po,DataValueo> slot: instance.getSlots()) {
            for(ValueSpecificationImpl<Co,Po,DataValueo> value: slot.getValues(null)) {
                try {
                    convertValue(value, slot, instance, targetInstance);
                } catch (MetaModelException e) {
                    if (Repository.getConfiguration().generousXMI()) {
                        System.out.println("Warning: " + e.getMessage());
                    } else {
                        throw e;
                    }
                }
            }
        }

        if (instance.getComposite() != null) {
            try {
                targetInstance.setComposite(convertInstance(instance.getComposite()));
            } catch (MetaModelException e) {
                if (Repository.getConfiguration().generousXMI()) {
                    System.out.println("Warning: " + e.getMessage());
                } else {
                    throw e;
                }
            }
        }
        
        if (idMemorizer != null) {
        	idMemorizer.idChanges(instance.getId(), targetInstance.getId());
        }
        return targetInstance;
    }

    @SuppressWarnings("unchecked")
    protected void convertValue(ValueSpecificationImpl<Co,Po,DataValueo> value, StructureSlot<Co,Po,DataValueo> slot,
                                ClassInstance<Co,Po,DataValueo> instance, ClassInstance<Ci,Pi,DataValuei> targetInstance)
            throws MetaModelException {
        if (!conversion.doConvert(value, slot, instance)) {
            return;
        }
        if (value.asDataValue() != null) {
        	T type = conversion.getPropertyType(conversion.getProperty(
                    slot.getProperty(),conversion.getClassifier(instance.getClassifier())));
            D dataType = conversion.asDataType(type);
            if (dataType == null) {
                throw new MetaModelException("Type \"" + type + "\" is expected to be a data type, but it is not.");
            }            
        	Collection<DataValueo> mulitpleValues = conversion.splitDataValue(dataType, value.asDataValue().getValue());
            for(DataValueo singleValue: mulitpleValues) {
	        	ValueSpecificationImpl<Ci,Pi,DataValuei> targetValue;
	            targetValue = targetModel.createPrimitiveValue(
	                    conversion.createFromString(dataType, singleValue));
	            targetInstance.addValue(conversion.getProperty(slot.getProperty(), targetInstance.getClassifier(), targetValue), targetValue, null);
            }
        } else {
	        ValueSpecificationImpl<Ci,Pi,DataValuei> targetValue;
	        if (value.asInstanceValue() != null) {
	            targetValue = targetModel.createInstanceValue(convertInstance(value.asInstanceValue().getInstance()));
	        //} else if (value.asDataValue() != null) {
	        //    T type = conversion.getPropertyType(conversion.getProperty(
	        //            slot.getProperty(),conversion.getClassifier(instance.getClassifier())));
	        //    D dataType = conversion.asDataType(type);
	        //    if (dataType == null) {
	        //        throw new MetaModelException("Type \"" + type + "\" is expected to be a data type, but it is not.");
	        //    }
	        //    targetValue = targetModel.createPrimitiveValue(
	        //            conversion.createFromString(dataType, value.asDataValue().getValue()));
	        } else if (value.asUnspecifiedValue() != null) {
	            T type = conversion.getPropertyType(conversion.getProperty(
	                    slot.getProperty(),conversion.getClassifier(instance.getClassifier())));
	            D dataType = conversion.asDataType(type);
	            if (dataType == null) {
	                // it must be a reference
	                Iterable<? extends ValueSpecificationImpl<Co,Po,DataValueo>> references;
	                if (value.asUnspecifiedValue().getParameter() != null) {
	                    references = sourceModel.createReferences((String)value.asUnspecifiedValue().getUnspecifiedData(),
	                            (String)value.asUnspecifiedValue().getParameter());
	                } else {
	                    references = sourceModel.createReferences((String)value.asUnspecifiedValue().getUnspecifiedData());
	                }
	                for (ValueSpecificationImpl<Co,Po,DataValueo> aValue: references) {
	                    value = aValue;
	                    if (value.asInstanceValue().getInstance().isValid()) {
	                        convertValue(value, slot, instance, targetInstance);
	                    }
	                }
	                return;
	            } else {
	                // it is a DataValue
	                value = sourceModel.createPrimitiveValue((DataValueo)value.asUnspecifiedValue().getUnspecifiedData());
	                convertValue(value, slot, instance, targetInstance);
	                return;
	            }
	        } else {
	            throw new RuntimeException("unreachable");
	        }
	        try {
	            targetInstance.addValue(conversion.getProperty(slot.getProperty(), targetInstance.getClassifier(), targetValue), targetValue, null);
	        } catch (cmof.exception.CompositeViolation ex) {
	            throw ex;
	        }
        }
    }
}
