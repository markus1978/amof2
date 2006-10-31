package hub.sam.mof.as;

import java.util.List;

import as.Action;

import cmof.Type;

public interface AsAction {
	public void staticSemantics(Action action, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException;
	public void invoke(Action action, List in, List<Object> out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame);
}
