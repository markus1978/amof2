package hub.sam.mof.as.actions;

import hub.sam.mof.as.AsAnalysisEnvironment;
import hub.sam.mof.as.AsExecutionEnvironment;
import hub.sam.mof.as.AsSemanticException;
import hub.sam.mof.Repository;
import as.ActivityEdge;
import as.ContextExtensionPin;
import as.ContextPin;
import as.GuardSpecification;
import as.ValueNode;
import cmof.Type;
import cmof.common.ReflectiveCollection;

public class AsGuardExpression {

	public boolean isElse(GuardSpecification guardSpecification) {
		String expression = guardSpecification.getBody();
		return expression.equals("else");
	}

	private String getExpression(GuardSpecification guardSpecification) {
		String expression = guardSpecification.getBody();
		return expression.substring(1, expression.length()-1);
	}

	public void staticSemantics(GuardSpecification guardSpecification, Type contextType, AsAnalysisEnvironment environment) throws AsSemanticException {

		if (guardSpecification.getInput() != null) {
			ReflectiveCollection<? extends ActivityEdge> incomingValues = guardSpecification.getInput().getIncoming();
			Type inputType = ((ValueNode)incomingValues.iterator().next().getSource()).getType();
			ContextPin contextPin = guardSpecification.getInput();
			if (contextPin instanceof ContextExtensionPin) {
				environment.addAdditionalContextAttribute((ContextExtensionPin)contextPin, null, inputType, contextType);
			} else {
				contextType = inputType;
			}
		}

		if (!isElse(guardSpecification)) {
			environment.checkOclConstraint(getExpression(guardSpecification), contextType, Repository.booleanType, false, false, false);
		}

		environment.removeAdditionalAttribute();
	}

	public boolean invoke(GuardSpecification guardSpecification, Object context, AsExecutionEnvironment environment) {
		return ((Boolean)environment.evaluateInvariant(getExpression(guardSpecification),
				((cmof.reflection.Object)context).getMetaClass(), context)).booleanValue();
	}
}
