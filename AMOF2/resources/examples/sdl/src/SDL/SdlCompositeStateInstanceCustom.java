package SDL;

import Pattern.Communication.Event;
import Pattern.Communication.Listener;
import Pattern.Evaluation.Evaluation;
import Pattern.Evaluation.Expression;
import cmof.common.ReflectiveSequence;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.util.ListImpl;
import hub.sam.sdlplus.SdlCompiler;

import java.util.Collection;
import java.util.Vector;

public class SdlCompositeStateInstanceCustom extends SdlCompositeStateInstanceDlg {
    @Override
    public void initialize() {
        ((SdlInstance)getSuper(SdlInstance.class)).initialize();
        initializeStates();
    }

    @Override
    public void initializeStates() {
        for(SdlState state: getMetaClassifierSdlStateType().getState()) {
            SdlStateInstance stateInstance = state.metaCreateSdlStateInstance();
            self.setState(state, stateInstance);
        }
    }

    @Override
    public void start() {
        if (self.getStatus() != null) {
            return; // is already started
        }
        // do the start transition
        System.out.println("Run composite state type " + self.getMetaClassifierSdlStateType().getName());
        self.setStatus(SdlStateStatus.STARTED);
        SdlStateAutomaton automaton = self.getMetaClassifierSdlStateType().getStateAutomaton();
        Start start = automaton.getStart();
        executeImmidiateTransition(start, self);
        while(executeImmidiateTransition(self.getActualState().iterator().next().getMetaClassifierSdlState(), self)) {
                // nothing
        }
        self.setStatus(SdlStateStatus.RUNNING);
    }

    @Override
    public synchronized void run() {
        //noinspection InfiniteLoopStatement
        while(self.getStatus() != SdlStateStatus.STOPED) {
            // execute any possibly imidiate transition
            while(executeImmidiateTransition(self.getActualState().iterator().next().getMetaClassifierSdlState(), self)) {
                // nothing
            }

            // check for other signals
            if (self.getTriggered() == null) {
                self.getOwningInstance().getOwningInstanceSet().update();
            }

            // perhaps take a none transition
            if (self.getTriggered() == null) {
                // check for none transitions
                SdlInputInstance none = null;
                for(SdlInputInstance input: self.getInput()) {
                    if (input.getMetaClassifierSdlInput().getType() == null) {
                        none = input;
                    }
                }
                if (none != null) {
                    // flip coin
                    if (((System.nanoTime() / 23) % 2) == 1) {
                        self.setTriggered(none);
                    }
                }
            }

            // wait for arriving signal
            if (self.getTriggered() == null) {
                ((ObjectImpl)self).hold();
            }

            // consume input
            SdlTransition transition = self.getTriggered().getMetaClassifierSdlInput().getTransition();
            self.setTriggered(null);
            // remove obsolete triggers
            Collection<SdlInputInstance> oldTriggers = new Vector<SdlInputInstance>();
            for (SdlInputInstance oldTrigger: self.getInput()) {
                oldTriggers.add(oldTrigger);
            }
            self.getInput().removeAll(oldTriggers);
            for (SdlInputInstance oldTrigger: oldTriggers) {
                oldTrigger.metaDelete();
            }
            executeTransition(transition, self);
        }

        if (self.getTriggered() != null) {
            System.out.println("drop trigger at line " + self.getTriggered().getMetaClassifierSdlInput().getLine());
        }
        System.out.println("composite state of type " + self.getMetaClassifierSdlStateType() + " stopped.");
        self.getOwningInstance().getOwningInstanceSet().terminate(self.getOwningInstance());
    }

    @Override
    public boolean consume(Listener listener, Event event) {
        if (self.getTriggered() == null) {
            SdlSignalInstance signal = (SdlSignalInstance)event;
            SdlInputInstance input = (SdlInputInstance)listener;
            SdlCompiler.getTrace().addCommunication(signal.getSender().getBehavior(), self, signal);

            int i = 0;
            for(SdlVariable variable: input.getMetaClassifierSdlInput().getParameter()) {
                SdlVariableSlot signalParameterSlot = signal.getParameter(
                        signal.getMetaClassifierSdlSignal().getParameter().get(i));
                SdlDataValue argumentValue = signalParameterSlot.getValue().iterator().next();
                signalParameterSlot.getValue().remove(argumentValue);
                resolveSlot(variable).updateValue(argumentValue);
                i++;
            }

            self.setSender(signal.getSender());
            signal.metaDelete();
            self.setTriggered((SdlInputInstance)listener);
            ((ObjectImpl)self).schedule(self);
            return true;
        } else {
            return false;
        }
    }

    class Runner implements Runnable {
        private final SdlCompositeStateInstance self;
        Runner(SdlCompositeStateInstance self) {
            super();
            this.self = self;
        }

        public synchronized void run() {
            self.run();
        }
    }

    @Override
    public SdlInstance getContainingInstance() {
        return self.getOwningInstance();
    }

    private static void executeAction(SdlAction action, SdlCompositeStateInstance self) {
        System.out.println("execute action at line " + action.getLine());
        if (action instanceof SdlOutput) {
            SdlOutput output = (SdlOutput)action;
            SdlSignalInstance signal = (SdlSignalInstance)output.create(self);
            signal.setSender(self.getOwningInstance());

            // evaluate the to expression
            Expression toExpression = output.getTo();
            if (toExpression != null) {
                SdlEvaluation toEval = (SdlEvaluation)toExpression.instantiate();
                toEval.updateContext(self);
                signal.setReceiver(((PidValue)toEval.getValue()).getValue());
            }

            self.getOwningInstance().dispatchSignal(signal, output.getVia());
        } else if (action instanceof SdlCreate) {
            SdlCreate create = (SdlCreate)action;
            SdlAgent agent = create.getAgent();
            SdlAgentInstance instance = (SdlAgentInstance)create.create(self);
            self.getOwningInstance().setOffspring(instance);
            self.getOwningInstance().getOwningInstanceSet().getAgentInstance().getAgentInstanceSet(agent).
                    getValue().add(instance); // TODO max instances check
            instance.run();
        } else if (action instanceof SdlAssignment) {
            SdlAssignment assignment = (SdlAssignment)action;
            SdlVariable variable = assignment.getVariable();
            ReflectiveSequence<SdlVariableSlot> slots = new ListImpl<SdlVariableSlot>();
            SdlVariableSlot slot = self.resolveSlot(variable);
            SdlEvaluation expr = (SdlEvaluation)assignment.getExpression().instantiate();
            expr.updateContext(self);
            SdlDataValue value = (SdlDataValue)expr.getValue();
            slot.updateValue(value);
            /*
            slots.addAll(self.getVariable());
            slots.addAll(self.getOwningInstance().getVariable()); // TODO recusive
            boolean set = false;
            for (SdlVariableSlot slot: slots) {
                if (slot.getMetaClassifierSdlVariable().equals(variable)) {
                    SdlEvaluation expr = (SdlEvaluation)assignment.getExpression().instantiate();
                    expr.updateContext(self);
                    SdlDataValue value = (SdlDataValue)expr.getValue();
                    slot.updateValue(value);
                }
            }
            */
        }
    }

    private static boolean executeImmidiateTransition(SdlAbstractState pstate, SdlCompositeStateInstance self) {
        // select transition
        SdlTransition transtion = null;
        for(SdlTrigger trigger: pstate.getTrigger()) {
            if (trigger instanceof SdlImidiate) {
                transtion = trigger.getTransition();
            }
        }
        if (transtion == null) {
            return false;
        }


        executeTransition(transtion, self);
        return true;
    }

    private static void executeTransition(SdlTransition transition, SdlCompositeStateInstance self) {
        // execute transtion
        for(SdlAction action: transition.getAction()) {
            executeAction(action, self);
        }

        // enter next state
        SdlAbstractState target = transition.getTarget();
        if (target instanceof SdlPseudoState) {
            if (target instanceof SdlSplit) {
                executeSplit((SdlSplit)target, self);
            } else if (target instanceof Stop) {
                self.setStatus(SdlStateStatus.STOPED);
            } else if (target instanceof SdlStateNode) {
                SdlStateNode targetStateNode = (SdlStateNode)target;
                if (targetStateNode.getState().size() == 0) {
                    // reenter the current state
                    nextstate(self, self.getActualState().iterator().next().getMetaClassifierSdlState());
                } else {
                    nextstate(self, targetStateNode.getState().iterator().next());
                }
            } else {
                // TODO fork, join, etc.
                executeImmidiateTransition(target, self);
            }
        } else {
            nextstate(self, target);
        }
    }

    private static void executeSplit(SdlSplit split, SdlCompositeStateInstance self) {
        SdlEvaluation questionEvaluation = (SdlEvaluation)split.getQuestion().instantiate();
        questionEvaluation.updateContext(self);
        SdlChoice choosen = null;
        SdlChoice elseChoice = null;
        for (SdlChoice choice: split.getAnswer()) {
            if (choice instanceof SdlElse) {
                elseChoice = choice;
            } else {
                for (SdlRange rangeCondition: choice.getCondition()) {
                    if (rangeCondition instanceof SdlOpenRange) {
                        SdlOpenRange condition = (SdlOpenRange)rangeCondition;
                        if (evalOpenRange(condition, self, questionEvaluation)) {
                            if (choosen == null) {
                                choosen = choice;
                            } else {
                                throw new RuntimeException("assert, two choices are true");
                            }
                        }
                    } else {
                        SdlClosedRange condition = (SdlClosedRange)rangeCondition;
                        if (evalOpenRange(condition.getLower(), self, questionEvaluation) &&
                                evalOpenRange(condition.getUpper(), self, questionEvaluation)) {
                            if (choosen == null) {
                                choosen = choice;
                            } else {
                                throw new RuntimeException("assert, two choices are true");
                            }
                        }
                    }
                }
            }
        }
        if (choosen == null) {
            if (elseChoice == null) {
                throw new RuntimeException("assert, no choice selected");
            } else {
                choosen = elseChoice;
            }
        }
        executeTransition(choosen.getTransition(), self);
    }

    private static boolean evalOpenRange(SdlOpenRange condition, SdlCompositeStateInstance self,
                                      Evaluation questionEvaluation) {
        // TODO, use the actual condition operator
        SdlEvaluation choiceEvaluation = (SdlEvaluation)condition.getExpression().instantiate();
        choiceEvaluation.updateContext(self);
        return ((SdlGeneralValue)questionEvaluation.getValue()).getValue().equals(
                ((SdlGeneralValue)choiceEvaluation.getValue()).getValue());
    }

    private static void nextstate(SdlCompositeStateInstance self, SdlAbstractState target) {
        SdlStateInstance targetInstance = self.getState((SdlState)target);
        if (targetInstance == null) {
            throw new RuntimeException("assert");
        }
        enterState(targetInstance, self);
    }

    private static void enterState(SdlStateInstance state, SdlCompositeStateInstance self) {
        self.getActualState().set(0, state);

        // get state nodes that cover the actual state
        Collection<SdlStateNode> stateNodesWithActualState = new Vector<SdlStateNode>();
        for (SdlAbstractState abstractState: self.getMetaClassifierSdlStateType().getStateAutomaton().getState()) {
            if (abstractState instanceof SdlStateNode) {
                SdlStateNode stateNode = (SdlStateNode)abstractState;
                if (stateNode.getState().contains(state.getMetaClassifierSdlState())) {
                    stateNodesWithActualState.add(stateNode);
                }
            }
        }
        // set up triggers for the new states inputs
        for (SdlStateNode stateNode: stateNodesWithActualState) {
            for (SdlTrigger trigger: stateNode.getTrigger()) {
                self.getInput().add(((SdlInput)trigger).metaCreateSdlInputInstance());
            }
        }
    }
}
