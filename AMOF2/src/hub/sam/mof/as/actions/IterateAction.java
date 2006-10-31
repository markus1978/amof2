package hub.sam.mof.as.actions;

import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsExecutionFrame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import as.Action;


public class IterateAction extends ExpressionAction {


	
	@Override
	protected int bodyArgs() {
		return 3;
	}

	@Override
	protected boolean iterable() {
		return true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void invoke(Action action, List in, List out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame) {
		setAction(action);
		Iterator iterator = frame.getIterators().get(getAction().getBody().get(2));
		if (iterator == null) {
			iterator = ((Iterable)environment.evaluateInvariant(getExpression(), 
					((cmof.reflection.Object)context).getMetaClass(), context)).iterator();
			 frame.getIterators().put(getAction().getBody().get(2), iterator); 			
		}
		out.add(iterator.next());
	}
}
