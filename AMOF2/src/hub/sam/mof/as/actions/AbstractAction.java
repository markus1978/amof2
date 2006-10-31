package hub.sam.mof.as.actions;

import java.util.List;

import hub.sam.mof.as.AsAction;
import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.as.AsSemanticException;
import hub.sam.mof.util.AssertionException;

import as.Action;
import as.ContextExtensionPin;
import as.ContextPin;
import as.InputPin;
import as.OpaqueAction;
import cmof.Type;

public abstract class AbstractAction implements AsAction {

	private OpaqueAction action = null;	
	
	protected void setAction(Action action) {
		this.action = (OpaqueAction)action;		
	}
	
	@Override
	public String toString() {
		return action.getBody().toString();
	}
	
	protected Type getContextType(Type contextType) {
		boolean hasCustomContext = false;
		for (InputPin inputPin: getAction().getInput()) {
			if (inputPin instanceof ContextPin) {
				if (inputPin instanceof ContextExtensionPin) {
					throw new AsSemanticException("For action " + toString() + " no context extensions are allowed.");
				}
				if (hasCustomContext) {
					throw new AsSemanticException("For action " + toString() + " only one context parameter is allowed.");
				} 
				hasCustomContext = true;
				contextType = inputPin.getType();
			}
		}
		return contextType;
	}
	
	protected Type getInputType(int index) {
		int i = -1;
		for (InputPin pin: getAction().getInput()) {
			if (!(pin instanceof ContextPin)) {
				i++;	
			}
			if (i == index) {
				return pin.getType();
			}
		}
		throw new AssertionException();
	}
	
	protected OpaqueAction getAction() {
		return action;
	}
	
	protected void checkArgumentCounts(int body, int in, int out, boolean context) {
		boolean badArgumentCount = false;
		if (body != -1) {
			if (action.getBody().size() != body + 1) {
				badArgumentCount = true;
			}
		}
		if (action.getOutput().size() != out) {
			badArgumentCount = true;
		}		
		boolean hasContext = false;
		int realIn = 0;
		for (InputPin inputPin: action.getInput()) {
			if (inputPin instanceof ContextPin) {
				hasContext = true;
			} else {
				realIn++;
			}
		}
		if (in != realIn && in != -1) {
			badArgumentCount = true;
		}
		if (hasContext && !context) {
			badArgumentCount = true;
		}
		if (badArgumentCount) {
			throw new AsSemanticException("Action " + toString() + " has wrong number of arguments.");
		}
	}
 	
	public abstract void staticSemantics(Action action, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException;
	public abstract void invoke(Action action, List in, List<Object> out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame);
}
