package hub.sam.mof.as;

import hub.sam.mof.mas.AnalysisEnvironment;
import hub.sam.mof.mas.SemanticException;
import hub.sam.mof.mas.ExecutionEnvironment;

import java.util.List;

import as.Action;

import cmof.Type;

public interface AsAction {
	public void staticSemantics(Action action, Type contextType, AnalysisEnvironment environment) throws SemanticException;
	public void invoke(Action action, List in, List<Object> out, Object context, ExecutionEnvironment environment, AsExecutionFrame frame);
}
