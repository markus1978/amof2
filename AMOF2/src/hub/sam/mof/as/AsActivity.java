package hub.sam.mof.as;

import hub.sam.mof.as.actions.AsGuardExpression;
import hub.sam.mof.mas.AnalysisEnvironment;
import hub.sam.mof.mas.SemanticException;
import hub.sam.mof.mas.ExecutionEnvironment;
import hub.sam.mof.util.AssertionException;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import org.oslo.ocl20.semantics.bridge.Environment;

import as.Action;
import as.Activity;
import as.ActivityEdge;
import as.ActivityNode;
import as.ContextExtensionPin;
import as.ContextPin;
import as.DecisionNode;
import as.FinalNode;
import as.GuardSpecification;
import as.InitialNode;
import as.InputPin;
import as.JoinNode;
import as.MergeNode;
import as.OpaqueAction;
import as.OutputPin;
import as.Pin;
import as.TypeString;
import as.ValueNode;
import cmof.Feature;
import cmof.NamedElement;
import cmof.Namespace;
import cmof.Operation;
import cmof.Parameter;
import cmof.Type;
import cmof.cmofFactory;


public class AsActivity extends AsBehavior {

	private final Collection<String> contextQualifiedName;
	private final Activity activity;
	private SemanticException exceptions = null;
	private Collection<ActivityNode> alreadyChecked = new HashSet<ActivityNode>();
	private Operation context = null;
	private Map<GuardSpecification, AsGuardExpression> guards = new HashMap<GuardSpecification, AsGuardExpression>();

	private InitialNode startNode = null;

	public AsActivity(Collection<String> context, Activity activity) {
		this.contextQualifiedName = context;
		this.activity = activity;
	}

	public AsActivity(Activity activity) {
		this.activity = activity;
		context = (Operation)activity.getOwner();
		contextQualifiedName = null;
	}

	private Type resovleTypeOfObject(ValueNode object, Collection<Namespace> topLevelNamespaces) throws SemanticException {
		NamedElement type = resolveQualifiedName(((TypeString)object.getType()).getQualifiedTypeName(), topLevelNamespaces);
		if (type == null) {
			throw new SemanticException("Type type reference " + ((TypeString)object.getType()).getQualifiedTypeName() + " could not be resolved.");
		}
		if (!(type instanceof Type)) {
			throw new SemanticException("The object " + object.getName() + " has type reference that does not reference a type.");
		}
		((cmof.reflection.Object)object.getType()).delete();
		object.setType((Type)type);
		return (Type)type;
	}

	@Override
	public void staticSemantics(AnalysisEnvironment environment) throws SemanticException {
		Collection<Namespace> topLevelNamespaces = new Vector<Namespace>(environment.getTopLevelPackages());
		exceptions = new SemanticException("Errors in bevavior declarations for name " + contextQualifiedName);

		try {
			// resolve the context name
			NamedElement describedElement = resolveQualifiedName(contextQualifiedName, topLevelNamespaces);
            if (describedElement == null) {
                throw new SemanticException("The name " + contextQualifiedName + " does not denote an model element.");
            }
            if (describedElement instanceof Operation) {
				context = (Operation)describedElement;
			} else {
				throw new SemanticException("The name " + contextQualifiedName + " does not denote an operation.");
			}
			// add the context's namespace to the topLevelNamespace (quasi import)
            Namespace additionalNamespace = context.getNamespace().getNamespace();
            if (!topLevelNamespaces.contains(additionalNamespace)) {
                topLevelNamespaces.add(additionalNamespace);
            }
        } catch (SemanticException ex) {
			exceptions.addException(ex);
            throw exceptions;
        }

		// check context parameter
		for (ValueNode object: activity.getArgument()) {
			try {
				Parameter parameterForArgument = null;
				for (Parameter parameter: context.getFormalParameter()) {
					if (object.getName().equals(parameter.getName())) {
						parameterForArgument = parameter;
					}
				}
				if (parameterForArgument == null) {
					throw new SemanticException("There is no parameter for initial object " + object.getName() + ".");
				}
				Type type = resovleTypeOfObject(object, topLevelNamespaces);
				if (!type.conformsTo(parameterForArgument.getType())) {
					throw new SemanticException("The object " + object.getName() + " referencing an argument of " + context.getQualifiedName() +
							" has a type that does not conform to the according parameter.");
				}
			} catch (SemanticException ex) {
				exceptions.addException(ex);
			}
		}

		// resovle all other TypeStrings
		for (ActivityNode node: activity.getNode()) {
			try {
				if (node instanceof ValueNode) {
					ValueNode object = (ValueNode)node;
					if (object.getType() != null && object.getType() instanceof TypeString) {
						resovleTypeOfObject(object, topLevelNamespaces);
					}
				}
			} catch (SemanticException ex) {
				exceptions.addException(ex);
			}
		}

		// check return
		boolean hasReturn = false;
		for (ActivityNode node: activity.getNode()) {
			if (node instanceof ValueNode) {
				if (new String("return").equals(node.getName())) {
					if (context.getType() == null || !((ValueNode)node).getType().conformsTo(context.getType())) {
						exceptions.addException(new SemanticException("No return allowed or wrong return type used."));
					} else {
						hasReturn = true;
					}
				}
			}
		}
		if (!hasReturn && context.getType() != null) {
			exceptions.addException(new SemanticException("Return required."));
		}
		// check the actions
		// resolve the start
		InitialNode start = null;
		for (ActivityNode node: activity.getNode()) {
			if (node instanceof InitialNode) {
				if (start != null) {
					exceptions.addException(new SemanticException("The activity for + " + contextQualifiedName + " contains more than one start node."));
				} else {
					start = (InitialNode)node;
				}
			}
		}
		if (start == null) {
			exceptions.addException(new SemanticException("The activity for + " + contextQualifiedName + " does not contain a start node."));
			throw exceptions;
		}

		if (exceptions.getExceptions().size() > 0) {
			throw exceptions;
		}

		// backtracking throw control flow graph
		staticSemanticsForNode(start, environment);

		if (exceptions.getExceptions().size() > 0) {
			throw exceptions;
		}
		context.setOwnedBehavior(activity);
	}

	private void checkInputPins(Iterable<? extends Pin> pins) {
		for(Pin pin: pins) {
			if (pin.getOutgoing().size() != 0) {
				throw new SemanticException("Badly connected pins");
			}
			if (pin.getIncoming().size() != 1) {
				throw new SemanticException("Badly connected pins");
			}
			pin.setType(((ValueNode)pin.getIncoming().iterator().next().getSource()).getType());
		}
	}

	private void checkOutputPins(Iterable<? extends Pin> pins) {
		for(Pin pin: pins) {
			if (pin.getIncoming().size() != 0) {
				throw new SemanticException("Badly connected pins");
			}
			if (pin.getOutgoing().size() != 1) {
				throw new SemanticException("Badly connected pins");
			}
			pin.setType(((ValueNode)pin.getOutgoing().iterator().next().getTarget()).getType());
		}
	}

	private AsAction instantiateAction(String name) {
		String actionClassName = "hub.sam.mof.as.actions." + name + "Action";
		Class actionClass = null;
		AsAction actionInstance = null;
		try {
			actionClass = Thread.currentThread().getContextClassLoader().loadClass(actionClassName);
		} catch (ClassNotFoundException ex) {
			// empty
		}
		if (actionClass != null) {
			try {
				actionInstance = (AsAction)actionClass.getConstructor(new Class[] {}).newInstance(new Object[] {});
			} catch (Exception e) {
				throw new AssertionException(e);
			}
			return actionInstance;
		} else {
			return null;
		}
	}

	private void staticSemanticsForNode(ActivityNode node, AnalysisEnvironment environment) {
		if (!alreadyChecked.contains(node)) {
			alreadyChecked.add(node);
			if (node instanceof OpaqueAction) {
				OpaqueAction action = (OpaqueAction)node;
				checkInputPins(action.getInput());
				checkOutputPins(action.getOutput());

				String actionKind = action.getBody().get(0);
				AsAction actionInstance = instantiateAction(actionKind);
				if (actionInstance == null) {
					exceptions.addException(new SemanticException("The action kind " + actionKind + " does not exist."));
				} else {
					try {
						actionInstance.staticSemantics(action, context.getUmlClass(), environment);
					} catch (SemanticException e) {
						exceptions.addException(e);
					}
				}
			}
			for (ActivityEdge outgoing: node.getOutgoing()) {
				if (outgoing.getGuardSpecification() != null) {
					try {
						AsGuardExpression guard = new AsGuardExpression();
						guard.staticSemantics(outgoing.getGuardSpecification(), context.getUmlClass(), environment);
						if (outgoing.getGuardSpecification().getInput() != null) {
							Collection<InputPin> inputPins = new Vector<InputPin>();
							inputPins.add(outgoing.getGuardSpecification().getInput());
							checkInputPins(inputPins);
						}
						guards.put(outgoing.getGuardSpecification(), guard);
					} catch (SemanticException e) {
						exceptions.addException(e);
					}
				}
				staticSemanticsForNode(outgoing.getTarget(), environment);
			}
			if (node.getOutgoing().size() == 0 && ! (node instanceof FinalNode)) {
				exceptions.addException(new SemanticException("The control flow of activity for " + contextQualifiedName + " does not end with an end node."));
			}
		}
	}

	@Override
	public Object invoke(Object object, Object[] args, ExecutionEnvironment environment) {
		//System.out.println("### Behavior for " + context.getQualifiedName() + " invoked.");
		AsExecutionFrame frame = new AsExecutionFrame();
		Map<String, Object> objects = new HashMap<String, Object>();
		int i = 0;
		for (Parameter param: context.getFormalParameter()) {
			objects.put(param.getName(), args[i++]);
		}
		if (startNode == null) {
			for (ActivityNode node: activity.getNode()) {
				if (node instanceof InitialNode) {
					startNode = (InitialNode)node;
				}
			}
		}
		ActivityNode actualNode = startNode;
		while (!(actualNode instanceof FinalNode)) {
			if (actualNode instanceof Action) {
				Action action = (Action)actualNode;
				List<Object> inValues = new Vector<Object>();
				List<Object> outValues = new Vector<Object>();
				Object context = object;
				for (InputPin inPin: action.getInput()) {
					if (inPin instanceof ContextPin) {
						if (inPin instanceof ContextExtensionPin) {
							environment.addAdditionalContextAttribute(((ContextExtensionPin)inPin).getExtensionName(),
									objects.get(((ContextExtensionPin)inPin).getExtensionName()), inPin.getType(), this.context.getUmlClass());
						} else {
							context = objects.get(((ValueNode)inPin.getIncoming().iterator().next().getSource()).getName());
						}
					} else {
						inValues.add(objects.get(((ValueNode)inPin.getIncoming().iterator().next().getSource()).getName()));
					}
				}
				instantiateAction(((OpaqueAction)action).getBody().get(0)).invoke(action, inValues, outValues, context, environment, frame);
				Iterator<Object> outValuesIterator = outValues.iterator();
				for (OutputPin outPin: action.getOutput()) {
					objects.put(((ValueNode)outPin.getOutgoing().iterator().next().getTarget()).getName(), outValuesIterator.next());
				}
				actualNode = actualNode.getOutgoing().iterator().next().getTarget();
				environment.removeAdditionalAttribute();
			} else if (actualNode instanceof DecisionNode) {
				ActivityEdge trueBranch = null;
				ActivityEdge elseBranch = null;
				for (ActivityEdge branch: actualNode.getOutgoing()) {
					AsGuardExpression guard = new AsGuardExpression();
					if (guard.isElse(branch.getGuardSpecification())) {
						elseBranch = branch;
					} else {
						Object context = object;
						ContextPin inPin = branch.getGuardSpecification().getInput();

						if (inPin != null) {
							if (inPin instanceof ContextExtensionPin) {
								environment.addAdditionalContextAttribute(((ContextExtensionPin)inPin).getExtensionName(),
										objects.get(((ValueNode)inPin.getIncoming().iterator().next().getSource()).getName()), inPin.getType(), this.context.getUmlClass());
							} else {
								context = objects.get(((ValueNode)inPin.getIncoming().iterator().next().getSource()).getName());
							}
						}
						if (guard.invoke(branch.getGuardSpecification(), context, environment)) {
							if (trueBranch != null) {
								throw new RuntimeException("More than one decision answer evaluated to true.");
							} else {
								trueBranch = branch;
							}
						}

						environment.removeAdditionalAttribute();
					}
				}
				if (trueBranch == null) {
					if (elseBranch == null) {
						throw new RuntimeException("No decision answer evaluated to true.");
					} else {
						actualNode = elseBranch.getTarget();
					}
				} else {
					actualNode = trueBranch.getTarget();
				}
			} else if (actualNode instanceof MergeNode || actualNode instanceof InitialNode) {
				actualNode = actualNode.getOutgoing().iterator().next().getTarget();
			} else {
				throw new AssertionException("not implemented.");
			}
		}
		if (context.getType() != null) {
			return objects.get("return");
		} else {
			return null;
		}
	}
}
