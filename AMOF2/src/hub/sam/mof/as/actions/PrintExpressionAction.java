package hub.sam.mof.as.actions;

import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.mas.SemanticException;
import hub.sam.mof.mas.ExecutionEnvironment;

import java.util.List;

import as.Action;

public class PrintExpressionAction extends ExpressionAction {

	@Override
	public void invoke(Action action, List in, List out, Object context, ExecutionEnvironment environment, AsExecutionFrame frame) {
		setAction(action);
		System.out.println(environment.evaluateInvariant(getExpression(), ((cmof.reflection.Object)context).getMetaClass(), context));
	}

	@Override
	protected void checkOutputParameter() {
		if (getAction().getOutput().size() != 0) {
			throw new SemanticException("Wrong number of outputs for action " + this.toString() + " (" + getAction().getOutput().size() + ").");
		}
	}
}
