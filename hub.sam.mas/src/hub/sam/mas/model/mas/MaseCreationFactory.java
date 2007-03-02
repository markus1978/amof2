/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mas.model.mas;

import hub.sam.mas.editor.editor.MaseEditDomain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.eclipse.gef.requests.CreationFactory;

public class MaseCreationFactory implements CreationFactory {
    
    private final Logger logger = Logger.getLogger(MaseCreationFactory.class.getName());
    private final Class type;
    private Method createMethod;
    private final MaseEditDomain editDomain;
    private final masFactory factory;
    
    /**
     * Constructs a model factory for a given model class.
     * 
     * @param editDomain references the MaseRepository where new model elements are created
     * @param type the model class
     */
    public MaseCreationFactory(MaseEditDomain editDomain, Class type) {
        this.editDomain = editDomain;
        this.type = type;
        this.factory = null;
        initCreateMethod();
    }

    public MaseCreationFactory(masFactory factory, Class type) {
        this.editDomain = null;
        this.factory = factory;
        this.type = type;
        initCreateMethod();
    }
    
    private void initCreateMethod() {
        try {
            this.createMethod = this.getClass().getMethod("create" + type.getSimpleName(), (Class[]) null);
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private masFactory getFactory() {
        if (editDomain != null) {
            return editDomain.getFactory();
        }
        else {
            return factory;
        }
    }
    
    public Object getNewObject() {
        try {
            logger.debug("creating " + type.getSimpleName());
            return createMethod.invoke(this, (Object[]) null);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Class getObjectType() {
        return type;
    }
    
    public Activity createActivity() {
        return getFactory().createActivity();
    }
    
    public InitialNode createInitialNode() {
        InitialNode newObject = getFactory().createInitialNode();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public FinalNode createFinalNode() {
        FinalNode newObject = getFactory().createFinalNode();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public OpaqueAction createOpaqueAction() {
        OpaqueAction newObject = getFactory().createOpaqueAction();
        
        InputPinList newInputList = getFactory().createInputPinList();
        newObject.setInputList(newInputList);
        
        OutputPinList newOutputList = getFactory().createOutputPinList();
        newObject.setOutputList(newOutputList);
        
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public ValueNode createValueNode() {
        ValueNode newObject = getFactory().createValueNode();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public ObjectFlow createObjectFlow() {
        ObjectFlow newObject = getFactory().createObjectFlow();
        ModelGarbageCollector.getInstance().observeProperty(newObject, "target");
        return newObject;
    }

    public ControlFlow createControlFlow() {
        ControlFlow newObject = getFactory().createControlFlow();
        ModelGarbageCollector.getInstance().observeProperty(newObject, "target");
        return newObject;
    }

    public InputPin createInputPin() {
        InputPin newObject = getFactory().createInputPin();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public OutputPin createOutputPin() {
        OutputPin newObject = getFactory().createOutputPin();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public ContextPin createContextPin() {
        ContextPin newObject = getFactory().createContextPin();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public ContextExtensionPin createContextExtensionPin() {
        ContextExtensionPin newObject = getFactory().createContextExtensionPin();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public GuardSpecification createGuardSpecification() {
        GuardSpecification newObject = getFactory().createGuardSpecification();
        return newObject;
    }

    public DecisionNode createDecisionNode() {
        DecisionNode newObject = getFactory().createDecisionNode();
        
        ContextPinList newList = getFactory().createContextPinList();
        newObject.getContextList().add(newList);

        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }
    
    public ForkNode createForkNode() {
        ForkNode newObject = getFactory().createForkNode();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }
    
    public JoinNode createJoinNode() {
        JoinNode newObject = getFactory().createJoinNode();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }
    
    public ExpansionRegion createExpansionRegion() {
        ExpansionRegion newObject = getFactory().createExpansionRegion();
        
        ExpansionRegionBody newBody = getFactory().createExpansionRegionBody();
        newObject.setBody(newBody);

        InExpansionNodeList newInputList = getFactory().createInExpansionNodeList();
        newObject.setInList(newInputList);
        
        OutExpansionNodeList newOutputList = getFactory().createOutExpansionNodeList();
        newObject.setOutList(newOutputList);
        
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }
    
    public InExpansionNode createInExpansionNode() {
        InExpansionNode newObject = getFactory().createInExpansionNode();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

    public OutExpansionNode createOutExpansionNode() {
        OutExpansionNode newObject = getFactory().createOutExpansionNode();
        ModelGarbageCollector.getInstance().mark(newObject);
        return newObject;
    }

}
