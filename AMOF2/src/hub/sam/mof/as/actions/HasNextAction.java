package hub.sam.mof.as.actions;

import as.Action;
import cmof.Type;
import core.primitivetypes.Boolean;
import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.as.AsSemanticException;
import hub.sam.mof.util.AssertionException;

import java.util.Iterator;
import java.util.List;

public class HasNextAction extends AbstractAction {

	@Override
	public void staticSemantics(Action action, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException {
		setAction(action);
		checkArgumentCounts(1,0,1,false);
		if (!getAction().getOutput().get(0).getType().getName().equals(Boolean.class.getSimpleName())) {
			throw new AsSemanticException("Action " + toString() + " has wrong output type.");
		}
	}

	@Override
	public void invoke(Action action, List in, List<Object> out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame) {
		setAction(action);
		Iterator iterator = frame.getIterators().get(getAction().getBody().get(1));
		if (iterator == null) {
			throw new AssertionException("Iterator " + getAction().getBody().get(1) + " does not exisit");
		}
		boolean hasNext = iterator.hasNext();
		if (!hasNext) {
			frame.getIterators().remove(getAction().getBody().get(1));
		}
		out.add(hasNext ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE);
	}
}
