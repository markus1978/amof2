package hub.sam.mof.as.actions;

import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsExecutionFrame;
import hub.sam.mof.as.AsSemanticException;

import java.util.List;

import as.Action;
import as.OpaqueAction;
import cmof.Type;

public class PrintAction extends AbstractAction {

	@Override
	public void staticSemantics(Action action, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException {
		setAction(action);
		checkArgumentCounts(-1, -1, 0, false);
	}

	@Override
	public void invoke(Action action, List in, List<Object> out, Object context, AsExecutionEnvironment environment, AsExecutionFrame frame) {
		boolean first = true;
		for (Object o: ((OpaqueAction)action).getBody()) {
			if (first) {
				first = false;
			} else {
				System.out.print(o.toString());
			}
		}
		for (Object o: in) {
			if (first) {
				first = false;
			} else {
				System.out.print(o.toString());
			}
		}
		if (!first) {
			System.out.println();
		}
	}


}
