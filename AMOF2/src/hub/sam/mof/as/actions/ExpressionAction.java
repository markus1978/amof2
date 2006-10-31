package hub.sam.mof.as.actions;

import hub.sam.mof.as.AsAction;
import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.as.AsSemanticException;

import java.util.List;

import as.Action;
import as.ActivityEdge;
import as.ContextExtensionPin;
import as.ContextPin;
import as.OpaqueAction;
import as.ValueNode;
import cmof.Type;
import cmof.common.ReflectiveCollection;

public class ExpressionAction extends AsGuardExpression implements AsAction {

	private OpaqueAction action;
	
	@Override
	public String toString() {
		return action.getBody().toString();
	}
	
	protected OpaqueAction getAction() {
		return action;
	}
	
	protected int bodyArgs() {
		return 2;
	}
	
	protected boolean iterable() {
		return false;
	}
	
	protected String getExpression() {
		String expression = getAction().getBody().get(1);
		expression = expression.substring(1, expression.length()-1);
		return expression;
	}
	
	protected void setAction(Action action) {
		this.action = (OpaqueAction)action;
	}
	
	protected void checkOutputParameter() {
		if (getAction().getOutput().size() != 1) {
			throw new AsSemanticException("Wrong number of outputs for action " + this.toString() + " (" + getAction().getOutput().size() + ").");
		}
	}
	
	public void staticSemantics(Action action, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException {
		setAction(action);
		if (getAction().getBody().size() != bodyArgs()) {
			throw new AsSemanticException("Wrong number of arguments for action " + this.toString());
		}
		String expression = getExpression();
		
		if (getAction().getInput().size() != 0) {
			if (getAction().getInput().size() == 1 && getAction().getInput().get(0) instanceof ContextPin) {
				ReflectiveCollection<? extends ActivityEdge> incomingValues = getAction().getInput().get(0).getIncoming();				
				Type inputType = ((ValueNode)incomingValues.iterator().next().getSource()).getType();
				ContextPin contextPin = (ContextPin)getAction().getInput().get(0);
				if (contextPin instanceof ContextExtensionPin) {
					environment.addAdditionalContextAttribute((ContextExtensionPin)contextPin, null, inputType, contextType);
				} else {
					contextType = inputType;
				}
			} else {
				throw new AsSemanticException("Wrong number of inputs for action " + this.toString());
			}
		}
		checkOutputParameter();
		
		if (getAction().getOutput().size() == 0) {
			environment.checkOclConstraint(expression, contextType, null, iterable(), false, false);
		} else {
			environment.checkOclConstraint(expression, contextType,
					((ValueNode)getAction().getOutput().get(0).getOutgoing().iterator().next().getTarget()).getType(),
					iterable(), false, false);
		}
		
		environment.removeAdditionalAttribute();
	}

	@SuppressWarnings("unchecked")
	public void invoke(Action action, List in, List out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame) {
		setAction(action);
		out.add(environment.evaluateInvariant(getExpression(), ((cmof.reflection.Object)context).getMetaClass(), context));
	}
}
