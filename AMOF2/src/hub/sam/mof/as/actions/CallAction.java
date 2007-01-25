package hub.sam.mof.as.actions;

import as.Action;
import as.ContextPin;
import as.InputPin;
import as.OutputPin;
import cmof.Operation;
import cmof.Type;
import cmof.UmlClass;
import cmof.common.ReflectiveSequence;
import cmof.reflection.Argument;
import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.mas.AnalysisEnvironment;
import hub.sam.mof.mas.SemanticException;
import hub.sam.mof.mas.ExecutionEnvironment;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.ocl.MofOclModelElementTypeImpl;
import hub.sam.mof.reflection.ArgumentImpl;
import hub.sam.mof.util.ReadOnlyListWrapper;
import hub.sam.mof.util.Wrapper;
import org.oslo.ocl20.semantics.bridge.ModelElement;

import java.util.Arrays;
import java.util.List;

public class CallAction extends AbstractAction {

    private MofClassSemantics semantics = null;

    private boolean isClassProxyCall() {
        return getAction().getBody().size() == 3;
    }

    protected Operation getFeature(UmlClass contextType, AnalysisEnvironment environment) {
        return resolveFeature(contextType, environment);
    }

    protected Operation resolveFeature(UmlClass context, AnalysisEnvironment environment) {
        if (semantics == null) {
            semantics = MofClassifierSemantics.createClassClassifierForUmlClass(context);
        }
        String operationName;
        MofClassifierSemantics semantics;
        if (isClassProxyCall()) {
            operationName = getAction().getBody().get(2);

            String body = getAction().getBody().get(1);
            ModelElement oclModelElementTypeToCreate;
            if (body.contains("::")) {
                oclModelElementTypeToCreate = environment.getOclEnvironment().lookupPathName(Arrays.asList(body.split("::")));
            } else {
                oclModelElementTypeToCreate = environment.getOclEnvironment().lookup(body);
            }
            if (!(oclModelElementTypeToCreate instanceof MofOclModelElementTypeImpl)) {
                throw new SemanticException("The arguments of action " + toString() + " does not match to a class proxy.");
            }
			Object mofDelegate = ((MofOclModelElementTypeImpl)oclModelElementTypeToCreate).getMofDelegate();
            if (!(mofDelegate instanceof UmlClass)) {
                throw new SemanticException("The arguments of action " + toString() + " does not match to a class proxy.");
            }
            semantics = MofClassifierSemantics.createClassClassifierForUmlClass((UmlClass)mofDelegate);
        } else {
            operationName = getAction().getBody().get(1);
            semantics = this.semantics;
        }

        for (InputPin inputPin: getAction().getInput()) {
            if (!(inputPin instanceof ContextPin)) {
                operationName += "_" + inputPin.getType().getQualifiedName();
            }
        }
        Operation operation = semantics.getFinalOperation(operationName);
        if (operation == null) {
            throw new SemanticException("The arguments of action " + toString() + " does not match to an operation in the given context.");
        }
        return operation;
    }


    @Override
    public void staticSemantics(Action action, Type contextType, AnalysisEnvironment environment) throws SemanticException {
        setAction(action);
        if (getAction().getBody().size() > 3 || getAction().getBody().size() < 2) {
            throw new SemanticException("Action " + toString() + " has wrong number of arguments.");
        }
        contextType = getContextType(contextType);
        if (!(contextType instanceof UmlClass)) {
            throw new SecurityException("Context type for action " + toString() + " must be an Class");
        }

        Operation operation = getFeature((UmlClass)contextType, environment);

        Type requiredType = null;
        for (OutputPin outputPin: getAction().getOutput()) {
            if (requiredType != null) {
                throw new SemanticException("Action " + toString() + " has wrong number of arguments.");
            } else {
                requiredType = outputPin.getType();
            }
        }
        if (operation.getType() == null && requiredType != null) {
            throw new SemanticException("Action " + toString() + " has wrong number of arguments.");
        }
        if (operation.getType() != null && requiredType != null && !operation.getType().conformsTo(requiredType)) {
            throw new SemanticException("The return type of action " + toString() + " does not match its output parameter " +
                    "(" + operation.getType() + " vs. " + requiredType + ").");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void invoke(Action action, List in, List out, Object context, ExecutionEnvironment environment, AsExecutionFrame frame) {
        setAction(action);
        Operation opToCall = getFeature(((cmof.reflection.Object)context).getMetaClass(), environment);
        ReflectiveSequence<Argument> args = new ReadOnlyListWrapper<Argument, Object>(in, new Wrapper<Argument, Object>() {
            public Argument getE(Object forO) {
                return new ArgumentImpl(null, forO);
            }
            public Object getO(Argument forE) {
                return null;
            }
        });
        cmof.reflection.Object objectToCall;
        if (isClassProxyCall()) {
            objectToCall = (cmof.reflection.Object)environment.createElement(opToCall.getUmlClass());
        } else {
            objectToCall = (cmof.reflection.Object)context;
        }
        Object result = objectToCall.invokeOperation(opToCall, args);
        if (opToCall.getType() != null) {
            out.add(result);
        }
    }
}
